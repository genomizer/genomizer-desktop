package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JOptionPane;

import requests.AddAnnotationRequest;
import requests.AddFileToExperiment;
import requests.DeleteAnnotationRequest;
import requests.DownloadFileRequest;
import requests.LoginRequest;
import requests.LogoutRequest;
import requests.RequestFactory;
import requests.SearchRequest;
import requests.rawToProfileRequest;
import responses.DownloadFileResponse;
import responses.LoginResponse;
import responses.ResponseParser;
import responses.SearchResponse;
import util.ExperimentData;

import com.google.gson.Gson;

import communication.Connection;
import communication.DownloadHandler;
import communication.HTTPURLUpload;

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
					// TODO Fixa s� att det syns b�r anv�ndaren att filen
					// gick
					// attt konverteras.
				} else {
					return false;
					// TODO Fixa felmeddelande i gui ifall det inte gick att
					// convertera till profile.
					// TODO K�ra n�n timer f�r response.
				}

			}

		}

		return false;
	}

	public boolean loginUser(String username, String password) {
		if (!username.isEmpty() && !password.isEmpty()) {
			System.out.println("login test");
			LoginRequest request = RequestFactory.makeLoginRequest(username,
					password);
			conn.sendRequest(request, userID, "application/json");
			if (conn.getResponseCode() == 200) {
				LoginResponse loginResponse = ResponseParser
						.parseLoginResponse(conn.getResponseBody());
				if (loginResponse != null) {
					userID = loginResponse.token;
					System.out.println(userID);
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
		HTTPURLUpload handler = new HTTPURLUpload(
				"/var/www/html/uploads/test321.txt",
				"/home/dv12/dv12csr/edu/test321");
		handler.sendFile("pvt", "pvt");
		/*
		 * UploadHandler handler = new UploadHandler(
		 * "/var/www/html/uploads/test321.txt",
		 * "/home/dv12/dv12csr/edu/test321", userID, "pvt:pvt"); Thread thread =
		 * new Thread(handler); thread.start();
		 */
		return true;
	}

	public boolean downloadFile() {
		DownloadFileRequest request = RequestFactory.makeDownloadFileRequest(
				"test.wig", ".wig");
		conn.sendRequest(request, userID, "text/plain");
		Gson gson = new Gson();
		DownloadFileResponse response = gson.fromJson(conn.getResponseBody(),
				DownloadFileResponse.class);
		DownloadHandler handler = new DownloadHandler("pvt", "pvt");
		String homeDir = System.getProperty("user.home");
		handler.download("http://sterner.cc", homeDir + "/testFile.txt", userID);
		System.out.println("Test");
		return true;
	}

	public ExperimentData[] search(String pubmedString) {
		SearchRequest request = RequestFactory.makeSearchRequest(pubmedString);
		conn.sendRequest(request, userID, "text/plain");
		if (200 == 200) { // if(conn.getResponseCode == 200) {
			ExperimentData[] searchResponses = ResponseParser
					.parseSearchResponse(SearchResponse.getJsonExampleTest());
			// parseSearchResponse(conn.getResponseBody);
			if (searchResponses != null && searchResponses.length > 0) {
				searchHistory.addSearchToHistory(searchResponses);
				return searchResponses;
			}
		}
		return null;
	}

	public void setIp(String ip) {
		conn.setIp(ip);
	}

	@Override
	public boolean addNewAnnotation(String name, String[] categories,
			boolean forced) throws IllegalArgumentException {

		if (name.isEmpty()) {
			throw new IllegalArgumentException("Must have a name for the annotation!");
		}

		if (categories == null || categories.length == 0) {
			categories = new String[] {"Yes", "No", "Unknown"};
		}

		AddAnnotationRequest request = RequestFactory.makeAddAnnotationRequest(
				name, categories, forced);
		conn.sendRequest(request, userID, "application/json");
		if (conn.getResponseCode() == 201) {
			System.err.println("addAnnotation sent succesfully!");
			return true;
		} else {
			System.err
					.println("addAnnotaion FAILURE, did not recive 201 response");
			return false;
		}
	}

	@Override
	public boolean deleteAnnotation(String[] strings) {

		DeleteAnnotationRequest request = RequestFactory
				.makeDeleteAnnotationRequest(strings);
		conn.sendRequest(request, userID, "application/json");
		if (conn.getResponseCode() == 200) {
			System.err.println("Annotation named " + strings
					+ " deleted succesfully");
			return true;
		} else {
			System.err.println("Could not delete annotation name " + strings
					+ "!");
		}
		return false;
	}
}
