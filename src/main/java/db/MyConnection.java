package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MyConnection {

    public static Connection connection;

    public static Connection getConnection() {

        ResourceBundle rb = ResourceBundle.getBundle("config");
        try {
            connection = DriverManager.getConnection(rb.getString("db.url"), rb.getString("db.user"), rb.getString("db.pass"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}