package communication;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

import requests.*;

public class Connection {
	private String ip;
	private int port;
	private LinkedList<String> incomingMessages = new LinkedList<String>();

	public Connection(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public boolean sendRequest(Request request) {
		try {
            String targetUrl = "http://" + ip + ":" + port + request.url;
            System.out.println(targetUrl);
            URL url = new URL(targetUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(request.type);
            connection.setRequestProperty("Content-Type", "application/json");
            PrintWriter outputStream = new PrintWriter(connection.getOutputStream(), true);
            outputStream.println(request.toJson());
            outputStream.flush();
            int responseCode = connection.getResponseCode();
            if(responseCode != 200) {
                System.out.println("Connection error: " + responseCode);
                return false;
            }
            BufferedReader response = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            incomingMessages.add(response.readLine());
            System.out.println(incomingMessages.getFirst());
            connection.disconnect();

		} catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
		return true;
	}

}
