package views;

import dao.UserDAO;
import model.User;
import service.OTPService;
import service.SendOTPService;
import service.UserService;

import java.util.Scanner;

public class Welcome {

    public void welcomeScreen() {
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to the application");
        System.out.println("Press 1 to login");
        System.out.println("Press 2 to signup");
        System.out.println("Press 0 to exit");

        int choice = Integer.parseInt(s.nextLine());

        switch (choice) {
            case 1 -> login();
            case 2 -> signUp();
            case 0 -> System.exit(0);
            default -> System.out.println("Please enter valid number");
        }


    }

    private void login() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter you email");
        String email = s.nextLine();
        if (UserDAO.isExists(email)) {
            String genOTP = OTPService.getOTP();
            SendOTPService.sendOTP(email, genOTP);
            System.out.println("Enter the OTP");
            String otp = s.nextLine();

            if (otp.equals(genOTP)) {
                UserView view = new UserView(email);
                view.home();
            } else {
                System.out.println("Wrong OTP");
            }
        } else {
            System.out.println("User does not exist. " + "Sign Up");
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
                case 1 -> {
                    System.out.println("User registered");
                    UserView view = new UserView(email);
                    view.home();
                }

            }
        } else {
            System.out.println("Wrong OTP");
        }
    }
}
