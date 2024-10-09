package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import quanlynganhang.GUI.model.message.MessageBox;


public class DatabaseConnect {
    public static final String DB_NAME = "banking_db";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "031203";
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        return getConnection(DB_NAME, USERNAME, PASSWORD);
    }
    
    public static Connection getConnection(String dbName, String username, String password) throws ClassNotFoundException, SQLException {
        try {
            String dbURL = "jdbc:mysql://localhost:3306/%s";
            Connection conn = DriverManager.getConnection(String.format(dbURL, dbName), username, password);
            
            return conn;
        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder();
            errorString.append("Lỗi kết nối đến cơ sở dữ liệu, vui lòng kiểm tra lại cấu hình!");
            errorString.append("\nTên cơ sở dữ liệu: " + DB_NAME);
            errorString.append("\nusername: " + USERNAME);
            errorString.append("\npassword: " + PASSWORD);
            MessageBox.showErrorMessage(null, errorString.toString());
            return null;
        }
    }
}
