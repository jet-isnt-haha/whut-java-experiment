package gui;

import console.AbstractUser;
import console.DataProcessing;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class SelfInfoFrame extends JDialog {
    private JFrame jFrame;
    private JPanel contentPane;
    private JButton buttonModify;
    private JButton buttonCancel;
    private JTextArea userNameTextArea;
    private JTextArea oldPasswordTextArea;
    private JTextArea newPasswordTextArea;
    private JTextArea confirmNewPasswordTextArea;
    private JTextArea roleTextArea;
    private JLabel userNameLabel;
    private JLabel oldPasswordLabel;
    private JLabel newPasswordLabel;
    private JLabel confirmNewPasswordLabel;

    private AbstractUser user;
    private String oldPassword;
    private String password;
    private String confirm;

    public SelfInfoFrame(AbstractUser user) {
        this.user=user;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonModify);
        setTitle("个人中心");
        setBounds(100, 100, 400, 300);
        setLocationRelativeTo(null);
        pack();
        displaySelfInfo(user);

        buttonModify.addActionListener(new ActionListener() {
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

    private void displaySelfInfo(AbstractUser user){
        userNameTextArea.setText(user.getName());
        userNameTextArea.setEnabled(false);

//        oldPasswordTextArea.setText(user.getPassword());
//        oldPasswordTextArea.setEnabled(false);

        roleTextArea.setText(user.getRole());
        roleTextArea.setEnabled(false);

    }

    private void onOK() {
        // 在此处添加您的代码
        password=newPasswordTextArea.getText().trim();
        confirm=confirmNewPasswordTextArea.getText().trim();
        oldPassword=oldPasswordTextArea.getText().trim();
        if(oldPassword.equals(user.getPassword())) {
            if (password.equals(confirm)) {
                try {
                    DataProcessing.updateUser(user.getName(), password, user.getRole());
                    MainGUI.showMessage(jFrame, "修改成功", "提示");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                MainGUI.showMessage(jFrame, "密码输入不一致", "提示");
                return;
            }
            dispose();
        }else{
            MainGUI.showMessage(jFrame, "原密码输入错误", "提示");
        }
    }

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }

//    public static void main(String[] args) {
//        SelfInfoFrame dialog = new SelfInfoFrame();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
