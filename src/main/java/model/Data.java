package model;

public class Data {

    private int id;

    private String fileName;

    private String path;

    private String email;

    public Data() {

    }

    public Data(String fileName, String path, String email) {
        this.fileName = fileName;
        this.path = path;
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    public String getEmail() {
        return email;
    }

}
