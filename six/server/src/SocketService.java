import console.DataProcessing;

import java.io.*;
import java.net.Socket;

import static console.DataProcessing.insertDoc;

public class SocketService {

    //与客户端通信的Socket
    private Socket client;

    //对象输出流
    private ObjectOutputStream output;

    //对象输入流
    private ObjectInputStream input;
    private int connectID;

    public SocketService(Socket socket,int id) throws IOException {
        client =socket;
        connectID=id;
        //获得输入输出流
        getStream();
    }

    private void getStream() throws IOException {
        //创建对象的输出流
        output =new ObjectOutputStream(client.getOutputStream());
        //刷新输出缓冲区以发送头信息
        output.flush();

        //创建对象输入流
        input =new ObjectInputStream(client.getInputStream());
        System.out.println("获得的输入/输出流对象");
    }

    public Socket getClient() {
        return client;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public int getConnectID() {
        return connectID;
    }

    public void processConnection(){

            try {
                boolean connectToClient=true;
                while(connectToClient){
                String message =(String) input.readObject();
                if(message.equals("upload")){
                    uploadFile();
                }
                if(message.equals("download")){
                    downloadFile();
                }

            }
                closeConnection();
        } catch (IOException | ClassNotFoundException e) {
                System.out.println(e);
            }
    }
    public void closeConnection() throws IOException {
        client.close();
        input.close();
        output.close();
    }
//    private void initConnection() throws IOException {
//        // 重建连接
//        client = new Socket("127.0.0.1", 8888);
//        output = new ObjectOutputStream(client.getOutputStream());
//        input = new ObjectInputStream(client.getInputStream());
//        System.out.println("重新建立连接成功");
//    }
    private void uploadFile() throws IOException, ClassNotFoundException {
        // 创建连接
        String path = (String) input.readObject();
        System.out.println("服务端---接收到文件路径: " + path);

        new Thread(() -> {
            try {
                byte[] buffer = new byte[1024];
                File tempFile = new File(path);
                String filename = tempFile.getName();
                //使用try-with-resources用于简化资源管理并确保资源在使用后自动关闭，以避免一些报错
                try (/*BufferedInputStream infile = new BufferedInputStream(new FileInputStream(tempFile));*/
                        BufferedOutputStream targetFile = new BufferedOutputStream(new FileOutputStream(DataProcessing.uploadpath + "//" + filename))) {
                    int byteRead;
                    while ((byteRead = input.read(buffer)) != -1) {
                        targetFile.write(buffer, 0, byteRead);
                    }
                    targetFile.flush();  // 确保所有内容写入文件
                }
                String end_Flag=(String) input.readObject();
                System.out.println("服务端---上传文件成功");
                output.writeObject("UPLOAD_SUCCESS");
                output.flush();
                System.out.println("服务端---发送上传成功标志");
            }  catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    closeConnection();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void downloadFile() throws IOException, ClassNotFoundException {
        // 获取客户端发送的信息
        String fileName = (String) input.readObject();
        new Thread(() -> {
            try (FileInputStream in = new FileInputStream(DataProcessing.uploadpath + "\\" + fileName)) {
                byte[] buffer = new byte[1024];
                int byteRead;
                while ((byteRead = in.read(buffer)) != -1) {
                    // 将数据发给客户端
                    output.write(buffer, 0, byteRead);
                }
                output.flush();
                // 发送结束标志
                getOutput().writeObject("END_OF_FILE");
                getOutput().flush();
                System.out.println("服务端---下载文件成功");
            } catch (IOException e) {
                throw new RuntimeException("服务端处理文件下载失败", e);
            }finally {
                try {
                    closeConnection();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

}
