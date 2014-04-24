package Requests;

public class LoginRequest extends Request {
    public String username;
    public String password;

    public LoginRequest(String username, String password) {
	super("login");
	this.username = username;
	this.password = password;
    }

}
