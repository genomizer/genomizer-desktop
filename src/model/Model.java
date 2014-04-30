package model;

import requests.AddFileToExperiment;
import requests.LoginRequest;
import requests.LogoutRequest;
import requests.RequestFactory;
import requests.SearchRequest;
import responses.LoginResponse;
import responses.ResponseParser;
import responses.SearchResponse;

import communication.Connection;
import communication.UploadHandler;

public class Model implements GenomizerModel {

    private String userID = "";
    private Connection conn;

    public Model(Connection conn) {
	this.setConn(conn);
    }

    public String getUserID() {
	return userID;
    }

    public void setUserID(String userID) {
	this.userID = userID;
    }

    public Connection getConn() {
	return conn;
    }

    public void setConn(Connection conn) {
	this.conn = conn;
    }

    public boolean loginUser(String username, String password) {
	if (!username.isEmpty() && !password.isEmpty()) {
	    LoginRequest request = RequestFactory.makeLoginRequest(username,
		    password);
	    conn.sendRequest(request, userID, "application/json");
	    if (conn.getResponseCode() == 200) {
		LoginResponse loginResponse = ResponseParser
			.parseLoginResponse(conn.getResponseBody());
		if (loginResponse != null) {
		    userID = loginResponse.token;
		    return true;
		}
	    }
	}
	return false;
    }

    public boolean logoutUser() {
	LogoutRequest request = RequestFactory.makeLogoutRequest();
	conn.sendRequest(request, userID, "text/plain");
	if (conn.getResponseCode() == 200) {
	    userID = "";
	    return true;
	} else {
	    return false;
	}
    }

    public boolean uploadFile() {
	AddFileToExperiment request = RequestFactory.makeAddFile("test",
		"test", "1.3GB", "raw");
	conn.sendRequest(request, userID, "application/json");
	String url = conn.getResponseBody();
	if (url != null) {
	    System.out.println(url);
	}
	UploadHandler handler = new UploadHandler(
		"http://127.0.0.1:25652/test",
		"/home/dv12/dv12csr/edu/test321", userID);
	Thread thread = new Thread(handler);
	thread.start();
	return true;
    }

    public String search(String pubmedString) {
	SearchRequest request = RequestFactory.makeSearchRequest(pubmedString);
	conn.sendRequest(request, userID, "text/plain");
	if (conn.getResponseCode() == 200) {
	    SearchResponse[] searchResponses = ResponseParser
		    .parseSearchResponse(conn.getResponseBody());
	    if (searchResponses != null && searchResponses.length > 0) {
		return searchResponses[0].name;
	    }
	}
	return null;
    }

}
