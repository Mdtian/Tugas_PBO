// DBConnection.java
import java.sql.*;

public class DBConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dbrestoran";
    private static final String USER = "root";
    private static final String PASS = "";
    
    private static Connection connection = null;
    
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
            }
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}