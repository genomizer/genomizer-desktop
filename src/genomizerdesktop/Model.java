package genomizerdesktop;

import requests.LoginRequest;
import requests.RequestFactory;
import responses.LoginResponse;
import responses.ResponseParser;

import communication.Connection;

public class Model {

    private static final String ip = "127.0.0.1";
    private static final int port = 25652;
    private String userID;
    private Connection connection;

    public Model() {
	connection = new Connection(ip, port);
    }

    public void attemptLogin(String username, String password) {
	LoginRequest request = RequestFactory.makeLoginRequest(username,
		password);
	String response = connection.sendRequest(request);
	System.out.println(response);
	LoginResponse login = ResponseParser.parseLoginResponse(response);
	userID = login.userID;
    }
}
