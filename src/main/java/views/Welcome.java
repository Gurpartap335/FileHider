package views;

import dao.UserDAO;
import model.User;
import service.OTPService;
import service.SendOTPService;
import service.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class Welcome {

    public void welcomeScreen() {
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to the application");
        System.out.println("Press 1 to login");
        System.out.println("Press 2 to signup");
        System.out.println("Press 0 to exit");
        int choice = 0;
        choice = Integer.parseInt(s.nextLine());


        switch (choice) {
            case 1 -> login();
            case 2 -> signUp();
            case 0 -> System.exit(0);
            default -> System.out.println("Please enter valid number only.");
        }


    }

    private void login() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter you email");
        String email = s.nextLine();
        try {
            if (UserDAO.isExists(email)) {
                String genOTP = OTPService.getOTP();
                SendOTPService.sendOTP(email, genOTP);
                System.out.println("Enter the OTP");
                String otp = s.nextLine();
                if (otp.equals(genOTP)) {
                    new UserView(email).home();
                } else {
                    System.out.println("Wrong OTP");
                }
            } else {
                System.out.println("User does not exist." +
                        "Sign Up");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void signUp() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter name");
        String name = s.nextLine();

        System.out.println("Enter email");
        String email = s.nextLine();
        String genOTP = OTPService.getOTP();
        SendOTPService.sendOTP(email, genOTP);
        System.out.println("Enter the OTP");
        String otp = s.nextLine();
        if (otp.equals(genOTP)) {
            User user = new User(name, email);
            int response = UserService.saveUser(user);

            switch (response) {
                case 0 -> System.out.println("User already exists");
                case 1 -> System.out.println("User registered");
            }
        } else {
            System.out.println("Wrong email");
        }
    }
}
// BufferReader vs scanner
