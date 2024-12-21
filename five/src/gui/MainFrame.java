package gui;

import console.AbstractUser;
import console.SQLconnection;
import gui.DocGUI.DocFrame;
import gui.UserGUI.UserFrame;

import javax.swing.*;
import java.sql.SQLException;

public class MainFrame extends JFrame{

    private  JFrame jframe;
    private JPanel mainPanel;
    private JButton userManageButton;
    private JButton docManageButton;
    private JButton selfInfoButton;
    private JButton exitButton;

    private AbstractUser user;




        public MainFrame(AbstractUser user) {
            this.user = user;

            // 初始化主界面的组件（这些组件应该已经通过 IDEA 的 GUI 设计器设计完成）
            setContentPane(mainPanel); // 使用你的主面板
            setTitle("主界面");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setBounds(100, 100, 800, 600);
            setLocationRelativeTo(null); // 窗口居中显示

            //用户管理
            userManageButton.addActionListener(e->manageUser());

            //档案管理
            docManageButton.addActionListener(e->manageDoc(user));

            //个人中心
            selfInfoButton.addActionListener(e->manageSelfInfo(user));

            //退出登录
            exitButton.addActionListener(e->returnLogin());

            //权限管理
            setRight(user.getRole());
        }

        private void returnLogin(){
            //断开数据库连接
            try {
                SQLconnection.disconnect();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,e.getLocalizedMessage());
            }
            dispose();
            LoginFrame loginFrame = new LoginFrame(new JFrame());
        }

        private void setRight(String role){
            if(role.equalsIgnoreCase("administrator")){
                userManageButton.setEnabled(true);
                selfInfoButton.setEnabled(true);
                docManageButton.setEnabled(true);
            }else if(role.equalsIgnoreCase("browser")){
                userManageButton.setEnabled(false);
                selfInfoButton.setEnabled(true);
                docManageButton.setEnabled(true);
            }else if(role.equalsIgnoreCase("operator")){
                userManageButton.setEnabled(false);
                selfInfoButton.setEnabled(true);
                docManageButton.setEnabled(true);
            }

        }

        private void manageDoc(AbstractUser user){
            DocFrame docFrame=new DocFrame(this.user);
            docFrame.setVisible(true);
        }

        private void manageSelfInfo(AbstractUser user){
            SelfInfoFrame selfInfoFrame = new SelfInfoFrame(user);
            selfInfoFrame.setVisible(true);
        }

        private void manageUser(){
            UserFrame userFrame =new UserFrame();
            userFrame.setVisible(true);
        }
    }


