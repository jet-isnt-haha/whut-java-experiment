package gui;

import console.AbstractUser;
import console.DataProcessing;
import console.SQLconnection;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import static java.lang.System.exit;

public class LoginFrame extends JFrame{
    private JFrame jframe;
    private JPanel outpanel;
    private JPanel jpanel1;
    private JPanel jpanel2;
    private JPanel jpanel3;
    private JTextField nameField;
    private JTextField passwordField;
    private JButton buttonLogin;
    private JButton buttonExit;

    private String userName;
    private String password;

    public LoginFrame(JFrame jframe) {
        this.jframe = jframe;

        setContentPane(outpanel);//将主面板设置为窗口内容
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭操作
        setTitle("登录");
        setResizable(false);
        setBounds(0,0,500,300);
        setLocationRelativeTo(null);//窗口显示居中
        setVisible(true);

        //连接数据库
        //在构造函数中连接，不应在主函数连接，否则登出后无法连接
        try {
            SQLconnection.connect();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,e.getLocalizedMessage());
        }

        buttonLogin.addActionListener(e->onlogin());
        buttonExit.addActionListener(e->{
            exit(0);
        });
    }
    private void onlogin(){
        if(checkInput()){
            login();
        }
    }
private boolean checkInput(){
        userName = nameField.getText().trim();
        password =String.valueOf(passwordField.getText()).trim();
        if(userName.equals("")||userName==null){
            MainGUI.showMessage(jframe,"用户名不能为空！","提示信息");
            nameField.requestFocus();
            return false;
        }
    if(password.equals("")|| password ==null){
        MainGUI.showMessage(jframe,"密码不能为空！","提示信息");
        passwordField.requestFocus();
        return false;
    }
    return true;
}
private void login(){
        try{
            AbstractUser user = DataProcessing.searchUser(userName, password);
            if(user==null){
                MainGUI.showMessage(jframe,"用户或密码错误","提示");
            }else{
                //显示主窗口
                showMainFrame(user);

                //关闭登录窗口
                dispose();
            }
        } catch (SQLException e) {
            MainGUI.showMessage(jframe,e.getMessage(),"提示信息");
        }
}
    private void showMainFrame(AbstractUser user) {
        // 创建并显示 MainFrame
        MainFrame mainFrame = new MainFrame(user);
        mainFrame.setVisible(true);  // 显示主界面
    }
}