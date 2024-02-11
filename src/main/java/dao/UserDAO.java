package dao;

import db.MyConnection;
import model.User;

import java.sql.*;

public class UserDAO {

    public static boolean isExists(String email) {

        try (Connection connection = MyConnection.getConnection())  {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from users");

            while (rs.next()) {
                String e = rs.getString("email");
                if (e.equals(email)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int saveUser(User user) {

        try ( Connection connection = MyConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users values(default, ?, ?) ");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }
}
