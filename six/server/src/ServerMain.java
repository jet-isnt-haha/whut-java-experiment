import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerMain {
    private static int countConnect=1;
    static void startServer() throws IOException {
        ServerSocket server = new ServerSocket(8888);

        while(true){
            //等待客户端连接
            Socket socket = server.accept();

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                    int id = countConnect++;

                    System.out.println("与客户端建立连接");
                    SocketService socketService =new SocketService(socket,id);
                    socketService.processConnection();

                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }).start();
        }
    }
    public  static  void  main( String args[] ) throws IOException {
        startServer();
    }
}
