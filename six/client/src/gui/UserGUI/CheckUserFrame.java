package gui.UserGUI;

import console.AbstractUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CheckUserFrame extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField userNameTextField;
    private JLabel userNameLabel;

    private AbstractUser user;
    private String name;
    public CheckUserFrame(JTable userTable) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("查询用户");
        setBounds(100, 100, 100, 80);
        pack();
        setLocationRelativeTo(null);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(userTable);
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

    private void onOK(JTable userTable) {
        // 在此处添加您的代码
        boolean flag=false;
        name=userNameTextField.getText().trim();
        for(int i=0;i<userTable.getRowCount();i++){
            if(name.equals(userTable.getValueAt(i,0))){
                //将找到的信息高亮显示
                userTable.getSelectionModel().setSelectionInterval(i,i);
                Rectangle rectangle=userTable.getCellRect(i,0,true);
                userTable.scrollRectToVisible(rectangle);
                flag=true;
            }

        }
        if(!flag){
            JOptionPane.showMessageDialog(null,"查询失败");
            return;
        }
        dispose();
    }

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }

//    public static void main(String[] args) {
//        CheckUserFrame dialog = new CheckUserFrame();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
