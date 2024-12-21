package console;

import java.io.*;
import java.sql.*;
import java.util.Enumeration;
import java.util.Hashtable;


public class DataProcessing {

    private static boolean connectToDB=false;
    private static Statement statement;
    private static Connection connection;
    private static ResultSet resultSet;

    public static  final String uploadpath="C:\\Users\\76018\\Desktop\\test1\\uploadPath\\";
    public  static  final String downpath="";

    public static void connectToDatabase(String driverName,String url,String user,String password)
            throws ClassNotFoundException, SQLException {

        //加载驱动
        Class.forName(driverName);

        //建立数据库连接
        connection= DriverManager.getConnection(url, user, password);
        connectToDB=true;
    }

    public static void disconnectFromDatabase() throws SQLException {
        if(connectToDB){
            //释放资源
            resultSet.close();
            statement.close();
            connection.close();
            connectToDB=false;
        }
    }


    public static Doc searchDoc(String id) throws SQLException {
        Doc doc=null;
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
        //创建语句对象
        statement =connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

        //执行sql语句
        String  sql="select * from doc_info where id='" +id + "'";

        //处理对象
        resultSet =statement.executeQuery(sql);
        //获取数据库信息
        if(resultSet.next()){
            String FileID=resultSet.getString("id");
            String creator=resultSet.getString("creator");
            Timestamp timestamp=Timestamp.valueOf(resultSet.getString("timestamp"));
            String description=resultSet.getString("description");
            String filename=resultSet.getString("filename");

            doc=new Doc(FileID,creator,timestamp,description,filename);
        }

        return doc;
    //        if (docs.containsKey(id)) {
    //            Doc temp =docs.get(id);
    //            return temp;
    //        }
    //        return null;
    }

    public static Enumeration<Doc> listDoc() throws SQLException{
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
        //建立哈希表
        Hashtable<String,Doc>docs=new Hashtable<String,Doc>();

        //创建语句对象
        statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

        //执行sql语句
        String sql="select *from doc_info";

        //处理对象
        resultSet=statement.executeQuery(sql);
        //获取数据库信息
        while(resultSet.next()){
            //遍历存放
            String FileID=resultSet.getString("id");
            String creator=resultSet.getString("creator");
            Timestamp timestamp=Timestamp.valueOf(resultSet.getString("timestamp"));
            String description=resultSet.getString("description");
            String filename=resultSet.getString("filename");

            docs.put(FileID,new  Doc(FileID,creator,timestamp,description,filename));
        }
        //返回枚举容器
        Enumeration<Doc> e  = docs.elements();
        return e;
    }


    public static boolean insertDoc(String id, String creator, Timestamp timestamp, String description, String filename) throws SQLException{
        Doc doc;

        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
        //创建语句对象
        statement =connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //执行sql语句
        String sql="INSERT INTO doc_info VALUES('" + id + "','" + creator + "','" + timestamp + "','" + description
                + "','" + filename + "')";
        //更新列表
        statement.executeUpdate(sql);

        return true;

    }


    public static AbstractUser searchUser(String name) throws SQLException{
        AbstractUser user =null;
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }

        //创建语句对象
        statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //执行sql语句
        String sql="select *from user_info where username='" +name+ "'";

        //处理对象
        resultSet=statement.executeQuery(sql);
        // 获取数据库信息
        if (resultSet.next()) {

            String UserName = resultSet.getString("username");
            String Password = resultSet.getString("password");
            String Role = resultSet.getString("role");

            user = new AbstractUser(UserName, Password, Role);


        }

        return  user;
    }


    public static AbstractUser searchUser(String name, String password) throws SQLException {
        AbstractUser user =null;
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }

        //创建语句对象
        statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //执行sql语句
        String sql="select *from user_info where username='" +name+ "'";

        //处理对象
        resultSet=statement.executeQuery(sql);
        // 获取数据库信息
        if (resultSet.next()) {

            String UserName = resultSet.getString("username");
            String Password = resultSet.getString("password");
            String Role = resultSet.getString("role");

            user = new AbstractUser(UserName, Password, Role);
            if (Password.equals(password)) {
                return user;
            } else {
                return null;
            }

        }
        return  user;
    }


    public static Enumeration<AbstractUser> listUser() throws SQLException{
        if (!connectToDB) {
            throw new SQLException("Not Connected to Database");
        }
        //建立哈希表
        Hashtable<String,AbstractUser> users=new Hashtable<String,AbstractUser>();

        // 创建语句对象
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // 执行SQL语句
        String sql = "select * from user_info "; // 获取user_info所有值

        // 处理对象
        resultSet = statement.executeQuery(sql);
        // 获取数据库信息
        while (resultSet.next()) {

            // 遍历存放
            String UserName = resultSet.getString("username");
            String Password = resultSet.getString("password");
            String Role = resultSet.getString("role");

            users.put(UserName, new AbstractUser(UserName, Password, Role));
        }
        Enumeration<AbstractUser> e = users.elements();
        return e;
    }


    public static boolean updateUser(String name, String password, String role) throws SQLException{

        if (!connectToDB)
            throw new SQLException("未连接到数据库！");

        // 创建语句对象
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // 执行SQL语句
        String sql = "UPDATE user_info SET password='" + password + "',role='" + role + "'WHERE username='" + name
                + "'"; // 更改user_info相关数据
        // 更新列表
        statement.executeUpdate(sql);

        return true;
    }


    public static boolean insertUser(String name, String password, String role) throws SQLException{
        if (!connectToDB)
            throw new SQLException("未连接到数据库！");

        // 创建语句对象
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // 执行SQL语句
        String sql = "INSERT INTO user_info VALUES('" + name + "','" + password + "','" + role + "')";
        // 更新列表
        statement.executeUpdate(sql);

        return true;
    }


    public static boolean deleteUser(String name) throws SQLException{

        if (!connectToDB)
            throw new SQLException("未连接到数据库！");

        // 创建语句对象
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // 执行SQL语句
        String sql = "DELETE FROM user_info WHERE username='" + name + "'";
        // 更新列表
        statement.executeUpdate(sql);

        return true;
    }


    public static boolean  uploadFile(String path,AbstractUser user,String id,String description) throws IOException,SQLException{

        byte [] buffer =new byte[1024];
        File tempFile =new File(path);
        String filename =tempFile.getName();

        BufferedInputStream infile =new BufferedInputStream(new FileInputStream(tempFile));
        BufferedOutputStream targetfile =new BufferedOutputStream(new FileOutputStream(uploadpath+filename));

        while(true){
            int byteRead =infile.read(buffer);
            if(byteRead==-1){
                break;
            }
            targetfile.write(buffer,0,byteRead);
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        insertDoc(id,user.getName(),timestamp,description,filename);
        infile.close();
        targetfile.close();
        System.out.println("上传成功");
        return true;

    }
    public static boolean downloadFile(String id,String downloadpath) throws IOException, SQLException {

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

    public static void main(String[] args) {

    }

}
