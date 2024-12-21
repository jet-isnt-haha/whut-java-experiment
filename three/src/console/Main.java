package console;

import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Scanner;

import static console.DataProcessing.*;

public class Main {
    public static void main(String[] args) {
//try{
//    File file2 =new File("AbstractUser.out");
//    File file1 =new File("Doc.out");
////    ObjectOutputStream out1 =new ObjectOutputStream(new FileOutputStream(file1));
////    ObjectOutputStream out2 =new ObjectOutputStream(new FileOutputStream(file2));
////    Timestamp timestamp =new Timestamp(System.currentTimeMillis());
////    AbstractUser user= new AbstractUser("kate", "123", "administrator") {
////        @Override
////        public void showMenu() {
////
////        }
////    };
////    Doc doc =new  Doc("0001","jack",timestamp,"Doc Source Java","Doc.java");
////    out1.writeObject(doc);
////    out2.writeObject(user);
////    out1.close();
////    out2.close();
//
//if(!file1.exists()){
//    Timestamp timestamp =new Timestamp(System.currentTimeMillis());
//        Doc doc =new  Doc("0001","jack",timestamp,"Doc Source Java","Doc.java");
//}
//if(!file2.exists()){
//        AbstractUser user= new AbstractUser("kate", "123", "administrator") {
//        @Override
//        public void showMenu() {
//
//        }
//    };
//    }
//    ObjectInputStream in1 =new ObjectInputStream(new FileInputStream(file1));
//    docs =(Hashtable<String, Doc>)in1.readObject();
//
//    ObjectInputStream in2 =new ObjectInputStream(new FileInputStream(file2));
//    users  =(Hashtable<String, AbstractUser >)in2.readObject();
//    in2.close();
//    System.out.println(docs);
//    System.out.println(users);
//}catch (IOException e){
//    System.out.println(e);
//}catch (ClassNotFoundException e){
//    System.out.println(e);
//}
        Scanner scanner =new Scanner(System.in);


        while(true){
            init();
            System.out.println("*************欢迎来到档案管理系统***************\n");
            System.out.println("1.登录         0.退出\n");
            System.out.println("********************************************");
            int a=scanner.nextInt();
            String name;
            String password;
            try {
        switch (a) {
            case 0:
                try {
                    File file2 = new File("AbstractUser.out");
                    File file1 = new File("Doc.out");
                    ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream(file1));
                    ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(file2));
                    out1.writeObject(docs);
                    out2.writeObject(users);
                    out1.close();
                    out2.close();
                } catch (IOException e) {
                    System.out.println("保存数据时出现错误：" + e.getMessage());
                }
                System.out.println("关闭系统，欢迎下次使用");
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