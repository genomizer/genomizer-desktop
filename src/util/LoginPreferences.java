package util;

import gui.LoginWindow;

import java.util.prefs.Preferences;

public class LoginPreferences {

    private Preferences prefs;

    String usernameKey = "USERNAME";
    String username = "user";

    private String serverKey = "SERVER";
    private String server = "";



    public String getLastUsername() {
        prefs = Preferences.userRoot().node(this.getClass().getName());
        return prefs.get(usernameKey, username);
    }

    public void setLastUsername(String username) {
        prefs = Preferences.userRoot().node(this.getClass().getName());
        prefs.put(usernameKey, username);
    }

    public String getLastServer() {
        prefs = Preferences.userRoot().node(this.getClass().getName());
        return prefs.get(serverKey, server);
    }

    public void setLastServer(String server) {
        prefs = Preferences.userRoot().node(this.getClass().getName());
        prefs.put(serverKey, server);
    }
}
