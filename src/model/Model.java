package model;

public interface Model {

    public boolean loginUser(String username, String password);

    public boolean logoutUser();

    public boolean uploadFile();
}
