package gui.DocGUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientService {
    private static boolean connecToserver =false;

    private static Socket client;

    public static boolean isConnecToserver() {
        return connecToserver;
    }

    public static Socket getClient() {
        return client;
    }

    public static ObjectOutputStream getOutput() {
        return output;
    }

    public static ObjectInputStream getInput() {
        return input;
    }

    public static void setConnecToserver(boolean connecToserver) {
        ClientService.connecToserver = connecToserver;
    }

    public static void setInput(ObjectInputStream input) {
        ClientService.input = input;
    }

    public static void setOutput(ObjectOutputStream output) {
        ClientService.output = output;
    }

    public static void setClient(Socket client) {
        ClientService.client = client;
    }

    private static ObjectOutputStream output;

    private static ObjectInputStream input;
    public static String ID;
    public static boolean connectToServer(){
        if (connecToserver) {
            // 如果已经连接，则直接返回
            return true;
        }
        try {
            System.out.println("正在连接服务器");
            client=new Socket("127.0.0.1",8888);
            connecToserver=true;

            //获取输入输出流
            getObjectStream();




        } catch (IOException e) {
            System.out.println(e);
        }
        return connecToserver;
    }

    private static  void getObjectStream() throws IOException {
        //创建对象输出流
        output =new ObjectOutputStream(client.getOutputStream());
        output.flush();

        //创建对象输入流
        input=new ObjectInputStream(client.getInputStream());
        System.out.println("获得输入/输出流对象!");
    }
    public static void closeConnection() throws IOException {
        client.close();
        input.close();
        output.close();
        connecToserver=false;
    }
}
