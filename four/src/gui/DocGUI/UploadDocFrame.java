package gui.DocGUI;

import console.AbstractUser;
import console.DataProcessing;
import console.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

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
        try {
            DataProcessing.uploadFile(filepath,user,id,description);
            JOptionPane.showMessageDialog(null, "上传成功！");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "上传失败！");
            return;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "上传失败！");
            return;
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
