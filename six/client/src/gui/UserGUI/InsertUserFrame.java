package gui.UserGUI;

import console.DataProcessing;
import gui.MainGUI;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class InsertUserFrame extends JDialog {
    private JFrame jFrame;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea passwordText;
    private JLabel userNameLabel;
    private JLabel passwordLabel;
    private JLabel rolesLabel;
    private JTextArea userNameText;
    private JComboBox rolesComboBox;

    private String userName;
    private String password;
    private String role;

    public InsertUserFrame() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("新增用户");
        setBounds(100, 100, 100, 80);
        pack();
        setLocationRelativeTo(null);

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

    private void onOK() {
        // 在此处添加您的代码
        userName =userNameText.getText().trim();
        password =passwordText.getText().trim();
        role =(String)rolesComboBox.getSelectedItem();
        try{
            if(!DataProcessing.insertUser(userName,password,role)){
                MainGUI.showMessage(jFrame,"添加失败","提示");
                return;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        dispose();
    }

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }

    public static void main(String[] args) {
        InsertUserFrame dialog = new InsertUserFrame();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
