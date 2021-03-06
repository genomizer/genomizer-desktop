package model;

/**
 * Class for handling session related responsibilities such as log in/out of
 * User and storing the IP (to the server where requests are sent) of the
 * session. The class follows the Singleton pattern, as it makes sense to only
 * have one session per client.
 *
 * @author oi12mlw, oi12pjn
 */
import requests.LoginRequest;
import requests.LogoutRequest;
import requests.RequestFactory;
import responses.LoginResponse;
import responses.ResponseParser;
import util.Constants;
import util.LoginException;
import util.LoginPreferences;
import util.RequestException;
import communication.Connection;
import communication.ConnectionFactory;

public class SessionHandler {

    private ConnectionFactory connFactory = new ConnectionFactory();

    /** The SessionHandler instance */
    private static final SessionHandler instance = new SessionHandler();

    /** Private constructor (class is singleton, there can be only one instance) */
    private SessionHandler() {
    }


    /**
     * Returns the instance of the SessionHandler
     *
     * @return the instance of the SessionHandler
     */
    public static SessionHandler getInstance() {
        return instance;
    }

    /**
     * Sets the IP to the server where all requests are sent for the session.
     *
     * @param ip
     *            the IP to the server
     */
    public void setIP(String ip) {
        connFactory.setIP(ip);
    }

    /**
     * Returns the IP to the server where all requests are sent
     *
     * @return the IP
     */
    public String getIP() {
        return connFactory.getIP();
    }

    /**
     * Logs in the user with the given username and password
     *
     * @param username
     *            the username
     * @param password
     *            the password
     *
     * @throws RequestException if the log in failed
     */
    public void loginUser(String username, String password) throws LoginException {

        LoginPreferences prefs = new LoginPreferences();
        LoginRequest request = RequestFactory.makeLoginRequest(username, password);
        Connection conn = connFactory.makeConnection();
        try {

            conn.sendRequest(request, "", Constants.JSON);
            int responseCode = conn.getResponseCode();

            if (responseCode == 0) {
                throw new LoginException("Server not found");
            }

            LoginResponse loginResponse = ResponseParser.parseLoginResponse(conn.getResponseBody());
            if (loginResponse != null) {
                User.getInstance().setToken(loginResponse.token);
                User.getInstance().setName(username);
                User.getInstance().setRole(loginResponse.role);
                User.getInstance().setLoggedIn(true);
                prefs.setLastUsername(username);
                prefs.setLastServer(getIP());
            }

        } catch (RequestException e) {
            throw new LoginException("Incorrect username or password");
        }

    }

    // TODO Should not return boolean. Throw exception?
    /**
     * Logs out the user
     *
     * @return
     */
    public boolean logoutUser() {
        LogoutRequest request = RequestFactory.makeLogoutRequest();
        Connection conn = connFactory.makeConnection();
        try {
            String token = User.getInstance().getToken();
            conn.sendRequest(request, token, Constants.TEXT_PLAIN);

            // TODO old code. might not be needed anymore. use setLoggedIn(T/F) instead.
            User.getInstance().setToken("");

            User.getInstance().setLoggedIn(false);
            return true;
        } catch (RequestException e) {
            return false;
        }
    }


    public Connection makeConnection(){
        return connFactory.makeConnection();
    }
}
