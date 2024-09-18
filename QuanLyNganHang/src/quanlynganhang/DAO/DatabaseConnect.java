package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnect {
    public static final String DB_NAME = "banking_db";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        return getConnection(DB_NAME, USERNAME, PASSWORD);
    }
    
    public static Connection getConnection(String dbName, String username, String password) throws ClassNotFoundException, SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/%s";
            Connection conn = DriverManager.getConnection(String.format(dbURL, dbName), username, password);
            
            return conn;
    }
}
