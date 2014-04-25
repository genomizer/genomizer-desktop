package responses;

public class LoginResponse extends Response {

    public String userID;

    public LoginResponse(String userID) {
	super("login");
	this.userID = userID;
    }
}
