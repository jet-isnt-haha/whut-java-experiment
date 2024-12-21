package console;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Scanner;

import static console.DataProcessing.*;

public class Administrator extends AbstractUser
{
    Administrator(String name, String password, String role) {
        super(name, password, role);
    }
    Scanner scanner =new Scanner(System.in);
    @Override
    public void showMenu() {
        System.out.println("*****系统管理员*****");
        System.out.println("*****1.新增用户*****");
        System.out.println("*****2.删除用户*****");
        System.out.println("*****3.修改用户*****");
        System.out.println("*****4.用户列表*****");
        System.out.println("*****5.下载档案*****");
        System.out.println("*****6.档案列表*****");
        System.out.println("*****7.修改个人密码*****");
        System.out.println("*****8.退出登录*****");
        while(true) {
            try {
                System.out.println("请输入选项>>");
                int a = scanner.nextInt();
                switch (a) {
                    case 1: {
                        String role, name, password;
                        System.out.println("请输入用户类型>>");
                        role = scanner.next();
                        System.out.println("请输入名称>>");
                        name = scanner.next();
                        System.out.println("请输入密码");
                        password = scanner.next();
                        if (DataProcessing.insertUser(name, password, role)){
                            System.out.println("新增成功");
                        } else{
                            System.out.println("新增失败");}
                        break;
                    }
                    case 2: {
                        String name;
                        System.out.println("请输入要删除的对象的姓名");
                        name = scanner.next();
                        if (DataProcessing.deleteUser(name)){
                            System.out.println("删除成功");
                        } else{
                            System.out.println("删除失败");
                        }
                        break;
                    }
                    case 3: {
                        String username, name, password, role;
                        System.out.println("请输入要修改的对象的姓名");
                        username = scanner.next();
                        if (searchUser(username) != null) {
                            System.out.println("请输入修改后的姓名");
                            name = scanner.next();
                            System.out.println("请输入修改后的密码");
                            password = scanner.next();
                            System.out.println("请输入修改后的用户类型");
                            role = scanner.next();
                            if (updateUser(name, password, role)) {
                                System.out.println("修改成功");
                            } else {
                                System.out.println("修改失败");
                            }

                        }
                        break;
                    }
                    case 4: {
                        Enumeration<AbstractUser> allUsers = listUser();
                        while (allUsers.hasMoreElements()) {
                            AbstractUser user = allUsers.nextElement();
                            // 对 user 对象进行操作
                            System.out.println(user.getName() + " " + user.getPassword() + " " + user.getRole());
                        }
                        break;
                    }
                    case 5: {
                        String fileName = " ";
//                        downloadFile(fileName);
                        break;
                    }
                    case 6: {
                        showFileList();
                        break;
                    }
                    case 7: {
                        System.out.println("请输入新密码>>");
                        String password = scanner.next();
                        setPassword(password);
                        break;
                    }
                    case 8: {
                        System.out.println("已退出");
                        return;
                    }
                    default:
                        break;
                }
            } catch (SQLException e2) {
//                e2.printStackTrace();
                System.out.println(e2.getMessage());
            }
        }
    }
}
