package gui.DocGUI;

import gui.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CheckDocFrame extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField TextField;
    private JLabel docIdLabel;

    private String id;
    public CheckDocFrame(JTable docTable) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("查询文档");
        setBounds(100, 100, 100, 80);
        pack();
        setLocationRelativeTo(null);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(docTable);
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

    private void onOK(JTable docTable) {
        // 在此处添加您的代码
        boolean flag=false;
        id=TextField.getText().trim();
        for(int i=0;i<docTable.getRowCount();i++){
            if(id.equals(docTable.getValueAt(i,0))){
                docTable.getSelectionModel().setSelectionInterval(i,i);
                Rectangle rectangle =docTable.getCellRect(i,0,true);
                docTable.scrollRectToVisible(rectangle);
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
//        CheckDocFrame dialog = new CheckDocFrame();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
