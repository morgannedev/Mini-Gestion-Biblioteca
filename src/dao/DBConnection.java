package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL="jdbc:mariadb://192.168.68.53:3306/myapp";
    private static final String USER="backendMyApp";
    private static final String PASSWORD="58Jwy;;>22(9";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
