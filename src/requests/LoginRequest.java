package requests;

import com.google.gson.annotations.Expose;

public class LoginRequest extends Request {
    @Expose
    public String username;
    @Expose
    public String password;

    public LoginRequest(String username, String password) {
	super("login", "/connection/login", "GET");
	this.username = username;
	this.password = password;
    }

}
