package communication;

import requests.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

public class Connection {
    private String ip;
    private int port;

    public Connection(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String sendRequest(Request request) {
        String message = null;
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
            if (responseCode >= 300) {
                System.out.println("Connection error: " + responseCode);
                return null;
            }
            BufferedReader response = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String buffer;
            StringBuilder output = new StringBuilder();
            while((buffer = response.readLine()) != null) {
                output.append(buffer);
            }
            System.out.println(request.url);
            message = output.toString();
            connection.disconnect();
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
        return message;
    }

    public void checkType(String output) {

    }

}
