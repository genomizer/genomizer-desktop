package model;

import java.util.HashMap;

public interface GenomizerModel {

    public boolean loginUser(String username, String password);

    public boolean logoutUser();

    public boolean uploadFile();

    public boolean search(HashMap annotations);
}
