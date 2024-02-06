package service;

import dao.UserDAO;
import model.User;

public class UserService {

    public static Integer saveUser(User user) {
        try {
            if (UserDAO.isExists(user.getEmail())) {
                return 0;
            } else {
                return UserDAO.saveUser(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
