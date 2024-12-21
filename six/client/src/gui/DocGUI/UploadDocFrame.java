package gui.DocGUI;

import console.AbstractUser;
import console.DataProcessing;
import console.StringUtil;
import gui.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Timestamp;

import static console.DataProcessing.insertDoc;
import static gui.DocGUI.ClientService.*;

public class UploadDocFrame extends JDialog {
    private  JFrame jFrame;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField idTextField;
    private JTextField desTextField2;
    private JTextField filenameTextField3;
    private JButton openButton;
    private JLabel docIdLabel;
    private JLabel descriptionLabel;
    private JLabel docNameLabel;

    private String id;
    private String description;
    private String filepath;
    private String fliename;
    private  AbstractUser user;
    public UploadDocFrame(AbstractUser user) {
        this.user=user;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("修改用户");
        setBounds(100, 100, 100, 80);
        pack();
        setLocationRelativeTo(null);

        openButton.addActionListener(e->open());
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // 点击 X 时调用 onCancel()
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // 遇到 ESCAPE 时调用 onCancel()
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    private void open(){
        // 弹出文件选择框
        FileDialog OpenFileDialog = new FileDialog(this, "选择上传文件");
        OpenFileDialog.setVisible(true);

        // 获取文件路径
        String filepath = OpenFileDialog.getDirectory() + OpenFileDialog.getFile();
        filenameTextField3.setText(filepath);

}
    private void initConnection() throws IOException {
        // 重建连接
        setOutput(new ObjectOutputStream(getClient().getOutputStream()));
        setInput(new ObjectInputStream(getClient().getInputStream()));
    }
    void uploadFile(String filepath, AbstractUser user, String id, String description) throws IOException {
        new Thread(() -> {
            try {
                // 向服务端发送上传指令
                getOutput().writeObject("upload");
                getOutput().flush();
                System.out.println("客户端---发送上传指令");
                // 发送文件路径
                getOutput().writeObject(filepath);
                getOutput().flush();
                System.out.println("客户端---发送文件路径: " + filepath);

                //上传文件内容
                File tempFile = new File(filepath);
                try(BufferedInputStream fileInput =new BufferedInputStream(new FileInputStream(tempFile))){
                    byte[] buffer =new byte[1024];
                    int byteRead;
                    //循环读取是为了处理大文件或未知大小的文件
                    while((byteRead=fileInput.read(buffer))!=-1){
                        getOutput().write(buffer,0,byteRead);
                        getOutput().flush();
                    }
                    getOutput().writeObject("END_OF_FILE");
                    getOutput().flush();
                    System.out.println("客户端---文件内容发送完成");
                }
                //插入文件信息到数据库并更新文档列表
                // 4. 接收服务端的响应
                String successFlag = (String) getInput().readObject();
                if ("UPLOAD_SUCCESS".equals(successFlag)) {
                    String filename = tempFile.getName();
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    insertDoc(id, user.getName(), timestamp, description, filename);
                    System.out.println("客户端---文件 " + filename + " 上传成功");
                }else{
                    System.out.println("文件上传失败");
                }

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    closeConnection();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }/*通过 BufferedInputStream 和 try-with-resources
     自动关闭文件流，避免资源泄漏。*/

    private void onOK()  {
        // 在此处添加您的代码
        filepath=filenameTextField3.getText().trim();
        id=idTextField.getText().trim();
        description=desTextField2.getText().trim();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (StringUtil.isEmpty(filepath)) {
            JOptionPane.showMessageDialog(null, "未选择文件！");
            return;
        }
        if (StringUtil.isEmpty(id)) {
            JOptionPane.showMessageDialog(null, "未输入档案号！");
            return;
        }
        if (StringUtil.isEmpty(description)) {
            JOptionPane.showMessageDialog(null, "未输入文件描述！");
            return;
        }
        //DataProcessing.uploadFile(filepath,user,id,description);
        if(!ClientService.connectToServer()){
            MainGUI.showMessage(null,"服务器未连接","提示");
        }else{
            try {
                uploadFile(filepath,user,id,description);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            JOptionPane.showMessageDialog(null, "上传成功！");
        }

        dispose();
    }

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }

//    public static void main(String[] args) {
//        UploadDocFrame dialog = new UploadDocFrame();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
