package responses;

public class LoginResponse extends Response {

	public String token;

	public LoginResponse(String userID) {
		super("login");
		this.token = userID;
	}
}
