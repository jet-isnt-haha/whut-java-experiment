package console;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner =new Scanner(System.in);
        while(true){
            System.out.println("*************欢迎来到档案管理系统***************\n");
            System.out.println("1.登录         0.退出\n");
            System.out.println("********************************************");
            int a=scanner.nextInt();
            String name;
            String password;
            try {
        switch (a) {

                case 0:
                    System.out.println("关闭系统欢迎下次使用\n");
                    System.exit(0);
                    return;
                case 1:
                    System.out.println("请输入用户名>>");
                    name = scanner.next();
                    System.out.println("请输入密码>>");
                    password = scanner.next();
                    AbstractUser user = DataProcessing.searchUser(name, password);
                    if (user != null) {
                        user.showMenu();
                    }
                    break;
                default:
                    System.out.println("输入有误请重新操作");
                    break;

        }
            }catch (SQLException e)
            {
                System.out.println(e);
            }
        }
        }
}