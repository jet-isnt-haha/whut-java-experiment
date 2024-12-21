package BSTCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServerThread {
    public static void main(String[] args) throws IOException {
        //创建一个服务器ServerSocket，和系统要指定的端口号
        ServerSocket server =new ServerSocket(8080);

        while (true){


            //使用accept方法获取到请求的客户端对象
            Socket socket =server.accept();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{

                        //使用Socket对象中的方法getInputStream，获取到网络字节输入流inputStream对象
                        InputStream is = socket.getInputStream();

                        //使用网络字节输入流对象InputStream对象中的方法read读取客户端的的请求信息
//        byte[] bytes =new byte[1024];
//        int len=0;
//        while((len=is.read(bytes))!=-1){
//            System.out.println(new String(bytes,0,len));
//        }

                        //把is网络字节输入流对象，转换为字节缓冲输入流
                        BufferedReader br = new BufferedReader(new InputStreamReader((is)));

                        String line =br.readLine();

                        String[] arr=line.split(" ");

                        String htmlpath =arr[1].substring(1);

                        //创建一个本地字节输入流，构造方法中绑定要读取的html路径
                        FileInputStream fis =new FileInputStream(htmlpath);

                        //使用Socket中的方法ge't'OutputStream获取网络字节输出流OutputStream对象
                        OutputStream os = socket.getOutputStream();

                        os.write("HTTP/1.1 200 OK\r\n".getBytes());
                        os.write("Content-Type:text/html\r\n".getBytes());

                        os.write("\r\n".getBytes());

                        //一读一写复制文件
                        int len =0;
                        byte[] bytes=new byte[1024];
                        while((len=is.read(bytes))!=-1){
                            os.write(bytes,0,len);
                        }

                        fis.close();
                        socket.close();
                    }catch(IOException e){
                        System.out.println(e);
                    }
                }
            }).start();

        }

//        server.close();
    }
}
