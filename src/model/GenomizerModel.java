package model;

import java.util.ArrayList;

public interface GenomizerModel {

    public boolean loginUser(String username, String password);

    public boolean logoutUser();

    public boolean uploadFile();

    public String search(String pubmedString);

    public boolean rawToProfile(ArrayList<String> markedFiles);
}
