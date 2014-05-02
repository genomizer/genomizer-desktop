package model;

import java.util.ArrayList;
import java.util.HashMap;

public interface GenomizerModel {

    public boolean loginUser(String username, String password);

    public boolean logoutUser();

    public boolean uploadFile();

    public ArrayList<HashMap<String, String>> search(String pubmedString);

    public boolean rawToProfile(ArrayList<String> markedFiles);

    public boolean downloadFile();

    public void setIp(String ip);
}
