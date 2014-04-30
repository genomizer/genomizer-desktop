package model;

import java.util.ArrayList;
import java.util.HashMap;

import requests.AddFileToExperiment;
import requests.LoginRequest;
import requests.LogoutRequest;
import requests.RequestFactory;
import requests.SearchRequest;
import requests.rawToProfileRequest;
import responses.LoginResponse;
import responses.ResponseParser;
import responses.SearchResponse;

import communication.Connection;
import communication.UploadHandler;

public class Model implements GenomizerModel {

    private String userID = "";
    private Connection conn;
    private SearchHistory searchHistory;

    public Model(Connection conn) {
	searchHistory = new SearchHistory();
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

    public boolean rawToProfile(ArrayList<String> markedFiles) {

	if (!markedFiles.isEmpty()) {

	    for (int i = 0; i < markedFiles.size(); i++) {

		rawToProfileRequest rawToProfilerequest = RequestFactory
			.makeRawToProfileRequest(markedFiles.get(i));

		conn.sendRequest(rawToProfilerequest, userID, "text/plain");
		if (conn.getResponseCode() == 201) {
		    return true;
		    // TODO Fixa så att det syns bör användaren att filen gick
		    // attt konverteras.
		} else {
		    return false;
		    // TODO Fixa felmeddelande i gui ifall det inte gick att
		    // convertera till profile.
		    // TODO Köra nån timer för response.
		}

	    }

	}

	return false;
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

    public ArrayList<HashMap<String, String>> search(String pubmedString) {
	SearchRequest request = RequestFactory.makeSearchRequest(pubmedString);
	conn.sendRequest(request, userID, "text/plain");
	if (conn.getResponseCode() == 200) {
	    SearchResponse[] searchResponses = ResponseParser
		    .parseSearchResponse(conn.getResponseBody());
	    if (searchResponses != null && searchResponses.length > 0) {
		searchHistory.addSearchToHistory(searchResponses);
		ArrayList<HashMap<String, String>> annotationsList = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < searchResponses.length; i++) {
		    HashMap<String, String> annotationsMap = new HashMap<String, String>();
		    SearchResponse searchResponse = searchResponses[i];
		    for (int j = 0; j < searchResponse.annotations.length; j++) {
			String id = searchResponse.annotations[j].id;
			String name = searchResponse.annotations[j].name;
			String value = searchResponse.annotations[j].value;
			annotationsMap.put(name, value);
		    }
		    annotationsList.add(annotationsMap);

		}
		return annotationsList;
	    }
	}
	return null;
    }

}
