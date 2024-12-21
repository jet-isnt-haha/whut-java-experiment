package FileLoad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        // 创建一个服务器ServerSocket对象，和系统要指定的端口号
        ServerSocket server =new ServerSocket(8888);

        while (true){
            //使用ServerSocket对象中的方法accept，获取到请求的客户端Socket对象
            Socket socket =server.accept();


            //使用多线程技术，提高程序的效率
            //有一个客户端上传文件，就开启一个线程，完成文件的上传
             new Thread(new Runnable() {
                 @Override
                 public void run() {
                     try{



                         //使用Socket对象中的方法getInputStream，获取到网络字节流InputStream对象
                         InputStream is =socket.getInputStream();

                         File file =new File("d:\\upload");
                         if(!file.exists()){
                             file.mkdir();
                         }

                         String fileName="jet"+System.currentTimeMillis()+new Random().nextInt(999999)+".jpg";


                         FileOutputStream fos =new FileOutputStream(file+"\\"+fileName);

                         //使用网络字节输入流InputStream对象中的方法read，读取客户端上传的文件
                         int len =0;
                         byte[] bytes =new byte[1024];
                         while((len=is.read(bytes))!=-1){
                             fos.write(bytes,0,len);
                         }

                         //使用Socket对象中的方法getOutputStream，获取到网络字节输出流OutputStream对象
                         //使用网络字节输出流OutputStream对象中的方法write，给客户端回写“上传成功”
                         socket.getOutputStream().write("上传成功".getBytes());

                         fos.close();
                         socket.close();
                     }catch (IOException e){
                         System.out.println(e);
                     }
                 }
             }).start();

        }

//        server.close();
    }
}
