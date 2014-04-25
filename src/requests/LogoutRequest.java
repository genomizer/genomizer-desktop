package requests;


public class LogoutRequest extends Request {

    public LogoutRequest(int userID) {
	super("logout", "/connection/logout/" + userID, "DELETE");
    }

}
