package gui.DocGUI;

import console.AbstractUser;
import console.Administrator;
import console.DataProcessing;
import console.Doc;
import gui.MainGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static gui.DocGUI.ClientService.*;
import static java.lang.System.exit;

public class DocFrame extends JFrame{
    //使用线程池管理后台任务
    private static final ExecutorService executor = Executors.newCachedThreadPool();
    private JFrame jFrame;
    private JPanel docPanel;
    private JButton uploadButton;
    private JButton downloadButton;
    private JTable docTable;
    private JButton checkButton;
    private AbstractUser user;

    public DocFrame(AbstractUser user){
        this.user =user;
        //设置表格模型
        docTable.setModel(new DefaultTableModel(
                new Object[][]{},//初始空数据
                new String[]{"档案号","创建者","时间","文件名","描述"}//列名
        ));
        //加载表格
        loadDocData();
        setContentPane(docPanel);
        setTitle("档案管理");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setLocationRelativeTo(null);
        setRight(user);

        downloadButton.addActionListener(e->downloadDoc());
        uploadButton.addActionListener(e->uploadDoc(user));
        checkButton.addActionListener(e->checkDoc(docTable));
    }
    private void setRight(AbstractUser user){
        if(user.getRole().equals("browser")||user.getRole().equals("administrator"))
        {
            uploadButton.setEnabled(false);
        }
    }
    private void checkDoc(JTable docTable){

        CheckDocFrame checkDocFrame=new CheckDocFrame(docTable);
        checkDocFrame.setVisible(true);
    }

    private void uploadDoc(AbstractUser user){
       UploadDocFrame uploadDocFrame =new UploadDocFrame(user);
       uploadDocFrame.setVisible(true);
        loadDocData();
    }
    public void saveFile(String fileName, String directory) {
        new Thread(() -> {
            // 定义标志变量
            boolean isSuccess = false;

            try {
                // 发送下载请求到服务器
                getOutput().writeObject("download");
                getOutput().flush();
                getOutput().writeObject(fileName);
                getOutput().flush();

                // 文件接收和保存逻辑
                try (FileOutputStream fout = new FileOutputStream(directory + "//" + fileName)) {
                    byte[] buffer = new byte[1024];
                    int byteRead;
                    // 读取数据并写入文件*****
                    while ((byteRead = getInput().read(buffer)) != -1) {
                        fout.write(buffer, 0, byteRead);
                    }
                    fout.flush();

                    // 等待结束标志
                    String endFlag = (String) getInput().readObject();
                    isSuccess = true; // 文件接收完成.
                    if (isSuccess) {
                        System.out.println("客户端---下载文件成功：" + fileName);
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                isSuccess = false;
                System.err.println("客户端---下载文件失败：" + fileName);
                System.err.println("客户端---文件下载过程出现异常：" + e.getMessage());
            }finally {

                try{
                    closeConnection();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void downloadDoc(){
        if(docTable.getSelectedRow()<0){
            MainGUI.showMessage(jFrame,"请选择要下载的文档","提示");
            return;
        }
        int choice =MainGUI.showConfirmMessage(jFrame,"确定要下载该档案吗?","提示");
        if(choice==JOptionPane.OK_OPTION){
            String id="";
            String filename="";
            //获取文件名
            Object object = docTable.getModel().getValueAt(docTable.getSelectedRow(),0);
            if(object!=null){
                id=object.toString();
                filename=docTable.getModel().getValueAt(docTable.getSelectedRow(),3).toString();
            }
            //打开保存文件对话框
            FileDialog saveFileDialog=new FileDialog(jFrame,"保存文件对话框",FileDialog.SAVE);
            saveFileDialog.setFile(filename);
            saveFileDialog.setVisible(true);
            if(saveFileDialog.getFile()!=null){
//                    DataProcessing.downloadFile(id,saveFileDialog.getDirectory());
                if(!ClientService.connectToServer()){
                    MainGUI.showMessage(null,"服务器未连接","提示");
                }else{
                    saveFile(filename,saveFileDialog.getDirectory());
                    JOptionPane.showMessageDialog(null, "下载成功");
                }

            }
        }
    }
    private void loadDocData(){
        // 使用 ArrayList 暂存数据
        ArrayList<String[]> rowDataList = new ArrayList<>();
        Enumeration<Doc> u;

        try {
            // 获取哈希表信息
            u = DataProcessing.listDoc();

            // 将哈希表信息导入到动态集合中
            while (u.hasMoreElements()) {
                Doc doc = u.nextElement();
                rowDataList.add(new String[]{
                        doc.getId(),
                        doc.getCreator(),
                        doc.getTimestamp().toString(),
                        doc.getFilename(),
                        doc.getDescription()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

// 将 ArrayList 转换为二维数组
        String[][] rowData = rowDataList.toArray(new String[0][0]);



// 将数据加载到表格模型
        DefaultTableModel tableModel = (DefaultTableModel) docTable.getModel();

//清空现有行
        tableModel.setRowCount(0);
        for (String[] row : rowData) {
            tableModel.addRow(row);
        }

    }

}
