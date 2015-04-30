package model;

public class User {

    private static User instance = new User();
    private String token;
    private String name;

    private User() { }


    public static User getInstance() {
        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
