package ie.atu.week5.samplelogin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDB {

    private final String url = "jdbc:mariadb://localhost:3306/login";
    private final String username = "user1";
    private final String password = "admin123";
    private final String adminPass = "password123";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
