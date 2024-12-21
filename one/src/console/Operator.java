package console;

import java.util.Scanner;

public class Operator extends User
{
    Operator(String name, String password, String role) {
        super(name, password, role);
    }
    public boolean uploadFile(){
        System.out.println("上传文件... ...");
        return true;
    }
    Scanner scanner =new Scanner(System.in);
    public void showMenu() {
        System.out.println("*****档案录入人员*****");
        System.out.println("*****1.上传档案*****");
        System.out.println("*****2.下载档案*****");
        System.out.println("*****3.档案列表*****");
        System.out.println("*****4.修改个人密码*****");
        System.out.println("*****5.退出登录*****");
        while(true)
        {
            System.out.println("请输入>>");
            int a=scanner.nextInt();
            switch(a){
                case 1:
                {
                    uploadFile();
                    break;}
                case 2:{
                    String file=" ";
                    downloadFile(file);
                    break;}
                case 3:
                {
                    showFileList();
                    break;}
                case 4:{
                    System.out.println("请输入新密码>>");
                    String password=scanner.next();
                    setPassword(password);
                    break;}
                case 5:{
                    System.out.println("已退出");
                    return;}
                default:
                    break;
            }
        }
    }
}
