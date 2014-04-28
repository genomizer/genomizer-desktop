package requests;

public class LogoutRequest extends Request {

    public LogoutRequest() {
	super("logout", "/connection/logout/", "DELETE");
    }

}
