package model;

import requests.LoginRequest;
import requests.LogoutRequest;
import requests.RequestFactory;
import responses.ErrorResponse;
import responses.LoginResponse;
import responses.ResponseParser;
import util.Constants;
import util.RequestException;

import communication.Connection;
import communication.ConnectionFactory;

public class SessionHandler {


    private ConnectionFactory connFactory = new ConnectionFactory();
    private static SessionHandler instance = new SessionHandler();

    private SessionHandler() { }

    public static SessionHandler getInstance() {
        return instance;
    }

    public void setIP(String ip) {
        connFactory.setIP(ip);
    }

    public String getIP() {
        return connFactory.getIP();
    }

    public String loginUser(String username, String password) {

        if (!username.isEmpty() && !password.isEmpty()) {
            LoginRequest request = RequestFactory.makeLoginRequest(username, password);
            Connection conn = connFactory.makeConnection();
            try {
                conn.sendRequest(request, "", Constants.JSON);

            } catch (RequestException e) {
                // new ErrorDialog("Couldn't add new experiment", "fel",
                // e.getResponseCode() + ":\n" +
                // e.getResponseBody()).showDialog();

            }
            if (conn.getResponseCode() == 200) {
                LoginResponse loginResponse = ResponseParser
                        .parseLoginResponse(conn.getResponseBody());
                if (loginResponse != null) {
                    User.getInstance().setToken(loginResponse.token);
                    return "true";
                }
            } else {
                ErrorResponse response = ResponseParser.parseErrorResponse(conn
                        .getResponseBody());
                if (response != null) {
                    return response.message;
                } else {
                    return "Server not found";
                }
            }
        }

        return "No username and/or password inserted";
    }

    public boolean logoutUser() {
        LogoutRequest request = RequestFactory.makeLogoutRequest();
        Connection conn = connFactory.makeConnection();
        try {
            String token = User.getInstance().getToken();
            conn.sendRequest(request, token, Constants.TEXT_PLAIN);
            User.getInstance().setToken("");
            return true;

        } catch (RequestException e) {
            return false;
        }
    }
}
