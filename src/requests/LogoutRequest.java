package requests;

/**
 * A class representing a "Logout from the server" request in an application for
 * genome researchers. This request logs the user out of the server.
 *
 *
 * @author
 *
 */
public class LogoutRequest extends Request {

    /**
     * A constructor creating the request.
     *
     */
    public LogoutRequest() {
        super("logout", "/login", "DELETE");
    }

}
