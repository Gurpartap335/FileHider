package views;

import dao.DataDAO;
import model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {

    private String email;

    public UserView(String email) {
        this.email = email;
    }

    public void home() {
        do {
            System.out.println("Welcome " + this.email);
            System.out.println("Press 1 to show hidden files");
            System.out.println("Press 2 to hide a new file");
            System.out.println("Press 3 to unhide a file");
            System.out.println("Press 0 to exit");
            Scanner s = new Scanner(System.in);
            int choice = Integer.parseInt(s.nextLine());
            switch (choice) {
                case 1 -> {
                    try {
                        List<Data> files = DataDAO.getAllFiles(email);
                        System.out.println("ID - File Name");
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    System.out.println("Enter the file path");
                    String path = s.nextLine();
                    File f = new File(path);
                    Data file = new Data(0, f.getName(), path, email);
                    try {
                        DataDAO.hideFile(file);
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                }
                case 3 -> {
                    try {
                        List<Data> files = DataDAO.getAllFiles(email);
                        System.out.println("ID - File Name");
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }

                        System.out.println("Enter the id of file to unhide");
                        int id = Integer.parseInt(s.nextLine());
                        boolean isValidId = false;
                        for (Data file : files) {
                            if (file.getId() == id) {
                                isValidId = true;
                            }
                        }

                        if (isValidId) {
                            DataDAO.unHide(id);
                        } else {
                            System.out.println("Wrong Id");
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
                case 0 -> {
                    System.exit(0);
                }
            }
        } while (true);
    }
}
