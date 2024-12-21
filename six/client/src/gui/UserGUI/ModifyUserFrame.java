package gui.UserGUI;

import console.DataProcessing;
import gui.MainGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;

public class ModifyUserFrame extends JDialog {
    private JFrame jFrame;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea passwordText;
    private JComboBox rolesComboBox;
    private JLabel userNameLabel;
    private JLabel passwordLabel;
    private JLabel rolesLabel;
    private JTextArea userNameText;

    private String userName;
    private String password;
    private String role;
    public ModifyUserFrame(JTable userTable) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("修改用户");
        setBounds(100, 100, 100, 80);
        pack();
        setLocationRelativeTo(null);
        displayUser(userTable);
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
private void displayUser(JTable userTable){
        //将数据加载到表格模型

    DefaultTableModel tableModel =(DefaultTableModel)userTable.getModel();
    userNameText.setEnabled(false);
    userNameText.setText((String)tableModel.getValueAt(userTable.getSelectedRow(),0));
    passwordText.setText((String)tableModel.getValueAt(userTable.getSelectedRow(),1));
    rolesComboBox.setSelectedItem(((String) tableModel.getValueAt(userTable.getSelectedRow(),2)));
}
    private void onOK() {
        // 在此处添加您的代码
    userName=userNameText.getText().trim();
    password=passwordText.getText().trim();
    role =(String)rolesComboBox.getSelectedItem();
        try {
            if(!DataProcessing.updateUser(userName,password,role)){
                MainGUI.showMessage(jFrame,"修改失败","提示");
            }
                dispose();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }

    public static void main(String[] args) {
//        ModifyUserFrame dialog = new ModifyUserFrame();
//        dialog.pack();
//        dialog.setVisible(true);
        System.exit(0);
    }
}
