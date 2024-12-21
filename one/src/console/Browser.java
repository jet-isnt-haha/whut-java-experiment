package console;

import java.util.Scanner;

public class Browser extends User
{
    Browser(String name, String password, String role) {
        super(name, password, role);
    }
    Scanner scanner =new Scanner(System.in);
    public void showMenu(){
        System.out.println("*****档案浏览人员*****");
        System.out.println("*****1.下载档案*****");
        System.out.println("*****2.档案列表*****");
        System.out.println("*****3.修改个人密码*****");
        System.out.println("*****4.退出登录*****");
        while(true)
        {
            System.out.println("请输入>>");
            int a=scanner.nextInt();
            switch(a){
                case 1:
                {
                    String fileName=" ";
                    downloadFile(fileName);
                    break;}
                case 2:
                {
                    showFileList();
                    break;}
                case 3:
                {
                    System.out.println("请输入新密码>>");
                    String password=scanner.next();
                    setPassword(password);
                    break;}
                case 4:{
                    System.out.println("已退出");
                    return;}
                default:
                    break;
            }
        }
}

}