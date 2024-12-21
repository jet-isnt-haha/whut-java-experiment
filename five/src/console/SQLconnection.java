package console;

import java.sql.SQLException;

public class SQLconnection {
    //加载数据库驱动类
    static String driverName="com.mysql.cj.jdbc.Driver";

    //声明数据库的URL
    static String url ="jdbc:mysql://localhost:3306/document?userSSL=false&serverTimezone=UTC";

    //数据库用户
    static String user ="root";
    static String password="123456cjt";

    //连接数据库
    public static void connect() throws SQLException, ClassNotFoundException {
        DataProcessing.connectToDatabase(driverName,url,user,password);
    }

    //断开数据库的连接
    public static void disconnect() throws SQLException {
        DataProcessing.disconnectFromDatabase();
    }
}
