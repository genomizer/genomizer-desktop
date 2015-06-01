package model;

/**
 * Class representing a user of the Genomizer desktop client.
 * @author oi12mlw
 *
 */
public class User {

    private static User instance = new User();

    /** The user token (used for server authentication) */
    private String token = "";

    /** The user name */
    private String name;

    private String role;

    private boolean loggedIn = false;

    private User() { }

    /**
     * Returns the user instance
     * @return the user instance
     */
    public static User getInstance() {
        return instance;
    }

    /**
     * Return the token for the user. The token will be an empty string, when the user is not logged in.
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the user token
     * @param token the user token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Returns the user's name
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name
     * @param name the user's name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

}
