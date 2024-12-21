package gui.UserGUI;

import console.DataProcessing;
import gui.MainGUI;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class DeleteUserFrame extends JDialog {
    private JFrame jFrame;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea deleteText;
    private JLabel deleteLabel;
    private String userName;

    public DeleteUserFrame() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("删除用户");
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

    private void onOK()  {
        // 在此处添加您的代码
        userName =deleteText.getText().trim();
        try {
            if(!DataProcessing.deleteUser(userName)){
                MainGUI.showMessage(jFrame,"删除失败","提示");
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        dispose();
    }

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }

    public static void main(String[] args) {
        DeleteUserFrame dialog = new DeleteUserFrame();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
