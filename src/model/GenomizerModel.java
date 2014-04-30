package model;


public interface GenomizerModel {

    public boolean loginUser(String username, String password);

    public boolean logoutUser();

    public boolean uploadFile();

    public boolean search(String pubmedString);
}
