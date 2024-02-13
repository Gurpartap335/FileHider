package views;

import dao.DataDAO;
import model.Data;

import java.io.File;
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
            System.out.println("Press 3 to un-hide a file");
            System.out.println("Press 0 to exit");
            Scanner s = new Scanner(System.in);

            int choice = Integer.parseInt(s.nextLine());
            switch (choice) {
                case 1 -> {
                    List<Data> files = DataDAO.getAllFiles(email);
                    System.out.println("ID  -  File Name");

                    if (files != null) {
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                    }
                }
                case 2 -> {
                    System.out.println("Enter the file path");
                    String path = s.nextLine();
                    File f = new File(path);
                    Data file = new Data(f.getName(), path, email);
                    DataDAO.hideFile(file);
                }
                case 3 -> {
                    List<Data> files = DataDAO.getAllFiles(email);
                    System.out.println("ID  -  File Name");
                    if (files != null) {
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                    }

                    System.out.println("Enter the id of file to unhidden");
                    int id = Integer.parseInt(s.nextLine());
                    boolean isValidId = false;

                    if (files != null) {
                        for (Data file : files) {
                            if (file.getId() == id) {
                                isValidId = true;
                                break;
                            }
                        }
                    }

                    if (isValidId) {
                        DataDAO.unHide(id);
                    } else {
                        System.out.println("Wrong Id");
                    }
                }
                case 0 -> {
                    System.exit(0);
                }
            }
        } while (true);
    }
}
