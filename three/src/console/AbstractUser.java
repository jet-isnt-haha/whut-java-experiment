package console;

import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Scanner;

import static console.DataProcessing.docs;
import static console.DataProcessing.insertDoc;

/**
 * TODO 抽象用户类，为各用户子类提供模板
 *
 * @author gongjing
 * @date 2016/10/13
 */
public abstract class  AbstractUser implements Serializable {
    private String name;
    private String password;
    private String role;
//    static final double EXCEPTION_PROBABILITY=0.9;
    String uploadpath="C:\\Users\\76018\\Desktop\\test1\\uploadPath\\";
    String downloadpath="C:\\Users\\76018\\Desktop\\test1\\downloadPath\\";

    public AbstractUser(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }


    @Override
    public String toString() {
        return "AbstractUser{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    /**
     * TODO 修改用户自身信息
     *
     * @param password 口令
     * @return boolean 修改是否成功
     * @throws SQLException
     */
    public boolean changeSelfInfo(String password) throws SQLException{
        if (DataProcessing.updateUser(name, password, role)){
            this.password=password;
            System.out.println("修改成功");
            return true;
        }else {
            return false;
        }
    }

    /**
     * TODO 下载档案文件
     *
     * @param id 文件名
     * @return boolean 下载是否成功
     * @throws IOException
     */
    public boolean downloadFile(String id) throws IOException, SQLException {
//        double ranValue=Math.random();
//        if (ranValue>EXCEPTION_PROBABILITY) {
//            throw new IOException( "Error in accessing file" );}
        //boolean result=false;
        byte [] buffer = new  byte [1024];
        Doc doc=DataProcessing.searchDoc(id);

        if  (doc==null) {
            return  false ;
        }

        File tempFile =new  File(uploadpath+doc.getFilename());
        String filename = tempFile.getName();

        BufferedInputStream infile = new  BufferedInputStream(new FileInputStream(tempFile));
        BufferedOutputStream targetfile = new  BufferedOutputStream(new FileOutputStream(downloadpath+filename));

        while  (true ) {
            int  byteRead=infile.read(buffer);
            if  (byteRead==-1) {
                break ;
            }
            targetfile.write(buffer,0,byteRead);
        }
        infile.close();
        targetfile.close();
        System.out.println("下载成功");
        return  true ;
    }
    /**
     * TODO 上传档案文件
     *
     * @param path 文件路径
     * @return  boolean
     * @throws IOException,SQLException
     * */
    public boolean uploadFile(String path) throws IOException, SQLException {
        byte[] buffer = new byte[1024];
        File tempFile = new File(path);
        String filename = tempFile.getName();

        try (BufferedInputStream infile = new BufferedInputStream(new FileInputStream(tempFile));
             BufferedOutputStream targetfile = new BufferedOutputStream(new FileOutputStream(uploadpath + filename))) {
            int byteRead;
            while ((byteRead = infile.read(buffer)) != -1) {
                targetfile.write(buffer, 0, byteRead);
            }
        }

        Scanner scanner = new Scanner(System.in); // 将 Scanner 设置为局部变量
        System.out.println("请输入档案编号>>");
        String id = scanner.next();
        System.out.println("请输入描述>>");
        String description = scanner.next();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        insertDoc(id, getName(), timestamp, description, filename);

        System.out.println("上传成功");
        return true;
    }

    /**
     * TODO 展示档案文件列表
     *
     * @param
     * @return void
     * @throws SQLException
     */
    public void showFileList() throws SQLException{
    Enumeration<Doc> e= DataProcessing.listDoc();
        Doc doc;
        while ( e.hasMoreElements() ){
            doc=e.nextElement();
            System.out.println("Id:"+doc.getId()+"\t Creator:" +doc.getCreator()+"\t Time:" +doc.getTimestamp()+"\t Filename:"+doc.getFilename());
            System.out.println("Description:"+doc.getDescription());
        }
    }



    /**
     * TODO 展示菜单，需子类加以覆盖
     *
     * @param
     * @return void
     * @throws
     */
    public abstract void  showMenu();
    ;

    /**
     * TODO 退出系统
     *
     * @param
     * @return void
     * @throws
     */
    public void exitSystem(){
        System.out.println("系统退出, 谢谢使用 ! ");
        System.exit(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
