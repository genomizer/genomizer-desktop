package requests;

public class LogoutRequest extends Request {

    public LogoutRequest() {
        super("logout", "/login", "DELETE");
    }

}
