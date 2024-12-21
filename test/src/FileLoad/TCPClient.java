package FileLoad;

import java.io.*;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("c:\\1.jpg");

        //创建socket对象，构造方法中绑定服务器的ip地址和端口号
        Socket socket =new Socket("127.0.0.1",8888);

        //获取Socket中的方法getoutputstream获取网络字节输出outputstream对象
        OutputStream os =socket.getOutputStream();

        int len=0;
        byte[] bytes =new byte[1024];
        while((len=fis.read(bytes))!=-1){
            //使用网络字节输出流outputstream对象中的方法write，把读取到的文件上传到服务器
            os.write(bytes,0,len);
        }

        socket.shutdownOutput();

        //使用socket中的方法getinputstream，获取网络字节流inputstream对象
        InputStream is= socket.getInputStream();
        while((len=is.read(bytes))!=-1){
            System.out.println(new String(bytes,0,len));
        }

        fis.close();
        socket.close();
    }
}
