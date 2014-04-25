package genomizerdesktop;

import requests.LoginRequest;
import requests.RequestFactory;
import responses.LoginResponse;
import responses.ResponseParser;

import communication.Connection;

public class Model {

	private static final String ip = "genomizer.apiary-mock.com";
	private static final int port = 80;
	private String userID = "";
	private Connection connection;

	public Model() {
		connection = new Connection(ip, port);
	}

	public boolean attemptLogin(String username, String password) {
		LoginRequest request = RequestFactory.makeLoginRequest(username,
				password);
		connection.sendRequest(request, userID, "application/json");
		if(connection.getResponseCode() == 200) {
			LoginResponse loginResponse
				= ResponseParser.parseLoginResponse(connection.getResponseBody());
			if(loginResponse != null) {
				userID = loginResponse.token;
				return true;
			}
		} 
		return false;
	}
}
