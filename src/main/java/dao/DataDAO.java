package dao;

import db.MyConnection;
import model.Data;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataDAO {
    public static List<Data> getAllFiles(String email) {

        try (Connection connection = MyConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from data where email = ?")) {
            List<Data> files = new ArrayList<>();
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Data data = new Data();
                data.setId(rs.getInt(1));
                data.setFileName(rs.getString(2));
                data.setPath(rs.getString(3));
                files.add(data);
            }
            return files;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void hideFile(Data file) {

        try (Connection connection = MyConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement("insert into data(name, path, email, bin_data) values(?, ?, ?, ?);")) {
            ps.setString(1, file.getFileName());
            ps.setString(2, file.getPath());
            ps.setString(3, file.getEmail());

            File f = new File(file.getPath());

            if (isImage(f)) {
                try (InputStream in = new FileInputStream(f)) {
                    ps.setBinaryStream(4, in, f.length());
                    ps.executeUpdate();
                }
            } else {
                FileReader fileReader = new FileReader(f);
                ps.setCharacterStream(4, fileReader, f.length());
                ps.executeUpdate();
                fileReader.close();
            }

            if (f.delete()) {
                System.out.println(file.getFileName() + " is hidden now");
            } else {
                System.out.println(file.getFileName() + " failed to hide");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    public static void unHide(int id) {

        try (Connection connection = MyConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("Select path, bin_data, name from data where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String path = rs.getString("path");
            String fileName = rs.getString("name");
            File file = new File(path);
            if (isImage(file)) {
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    Blob blob = rs.getBlob("bin_data");
                    byte[] b = blob.getBytes(1, (int) blob.length());
                    fos.write(b);
                }

            } else {
                Clob clob = rs.getClob("bin_data");

                Reader r = clob.getCharacterStream();
                FileWriter fw = new FileWriter(path);
                int i;
                while ((i = r.read()) != -1) {
                    fw.write((char) i);
                }
                fw.close();
            }

            ps = connection.prepareStatement("delete from data where id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            System.out.println(fileName + " is now Unhidden");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isImage(File file) {
        String mimeType = new MimetypesFileTypeMap().getContentType(file);
        String type = mimeType.split("/")[0];
        if (type.equals("image")) {
            return true;
        } else {
            return false;
        }
    }

}
