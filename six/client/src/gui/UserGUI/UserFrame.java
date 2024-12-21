package gui.UserGUI;

import console.AbstractUser;
import console.DataProcessing;
import gui.MainGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;


public class UserFrame extends JFrame{
    private JFrame jFrame;
    private JPanel userPanel;
    private JButton deleteButton;
    private JButton insertButton;
    private JButton modifyButton;
    private JPanel buttonPanel;
    private JButton returnButton;
    private JTable userTable;
    private JButton checkButton;
    private AbstractUser user;
    public UserFrame()  {
        //设置表格模型
        userTable.setModel(new DefaultTableModel(
                new Object[][]{}, // 初始空数据
                new String[]{"用户名", "姓名", "类型"} // 列名
        ));
        //加载表格
        loadUserData();
        setContentPane(userPanel);
        setTitle("用户管理");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setLocationRelativeTo(null);

        checkButton.addActionListener(e->checkUser());
        deleteButton.addActionListener(e->deleteUser());
        insertButton.addActionListener(e->insertUser());
        modifyButton.addActionListener(e->modifyUser());
        returnButton.addActionListener(e->returnMain());
    }
private void returnMain(){
        dispose();
}
private  void checkUser(){
        CheckUserFrame checkUserFrame =new CheckUserFrame(userTable);
        checkUserFrame.setVisible(true);
}
private void modifyUser(){
        if(userTable.getSelectedRow()<0){
            MainGUI.showMessage(jFrame,"请选择修改的用户","提示");
            return;
        }
        ModifyUserFrame modifyUserFrame =new ModifyUserFrame(userTable);
        modifyUserFrame.setVisible(true);
        loadUserData();
}
private void insertUser(){
        InsertUserFrame insertUserFrame =new InsertUserFrame();
        insertUserFrame.setVisible(true);
        loadUserData();
}

private void deleteUser(){
        DeleteUserFrame deleteUserFrame=new DeleteUserFrame();
        deleteUserFrame.setVisible(true);
        loadUserData();
}
private void loadUserData() {

// 使用 ArrayList 暂存数据
    ArrayList<String[]> rowDataList = new ArrayList<>();
    Enumeration<AbstractUser> u;

    try {
        // 获取哈希表信息
        u = DataProcessing.listUser();

        // 将哈希表信息导入到动态集合中
        while (u.hasMoreElements()) {
            AbstractUser user = u.nextElement();
            rowDataList.add(new String[]{
                    user.getName(),
                    user.getPassword(),
                    user.getRole()
            });
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
    }

// 将 ArrayList 转换为二维数组
    String[][] rowData = rowDataList.toArray(new String[0][0]);



// 将数据加载到表格模型
    DefaultTableModel tableModel = (DefaultTableModel) userTable.getModel();

//清空现有行
    tableModel.setRowCount(0);
    for (String[] row : rowData) {
        tableModel.addRow(row);
    }

}
}
