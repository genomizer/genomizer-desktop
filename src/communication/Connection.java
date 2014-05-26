package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import gui.GUI;
import gui.GenomizerView;
import gui.LoginWindow;
import model.Model;
import requests.Request;

import javax.swing.*;

public class Connection {
    private final GenomizerView view;
    private String ip;
    private int responseCode;
    private String responseBody;
    private HttpURLConnection connection;
    public Connection(String ip, GenomizerView view) {
        this.ip = ip;
        this.view = view;
        responseBody = "";
        responseCode = 0;
    }

    public boolean sendRequest(Request request, String userID, String type) {
        if (ip.startsWith("http://")) {
            ip = ip.substring(7);
        }
        try {
            String targetUrl = "http://" + ip + request.url;
            System.out.println(targetUrl);
            System.out.println("the request.toJson(): " + request.toJson());
            URL url = new URL(targetUrl);
            connection = (HttpURLConnection) url
                    .openConnection();
            if (type.equals("application/json")) {
                connection.setDoOutput(true);
            }
            connection.setReadTimeout(2000);
            connection.setRequestMethod(request.type);
            connection.setRequestProperty("Content-Type", type);
            if (!userID.isEmpty()) {
                connection.setRequestProperty("Authorization", userID);
            }

            if (request.type.equals("DELETE")) {
                //connection.connect();
                responseCode = connection.getResponseCode();
                fetchResponse(connection.getInputStream());
                if (responseCode >= 300) {
                    return false;
                }
                return true;
            }

            if (type.equals("application/json")) {
                PrintWriter outputStream = new PrintWriter(
                        connection.getOutputStream(), true);
                outputStream.println(request.toJson());
                outputStream.flush();
            }
            responseCode = connection.getResponseCode();
            fetchResponse(connection.getInputStream());
            if(responseCode == 401 && !userID.isEmpty()) {
                view.updateLogout();
                System.out.println("The token has expired, or was removed from the server.");
                return false;
            }
            if (responseCode >= 300) {
                return false;
            }
            connection.disconnect();
        } catch (IOException e) {
            try {
                fetchResponse(connection.getErrorStream());
            } catch (IOException e1) {
                e1.printStackTrace();
                return false;
            }
            return false;
        }
        return true;
    }

    private void fetchResponse(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                inputStream));
        String buffer;
        StringBuilder output = new StringBuilder();
        char[] cbuff = new char[1024];
        int length;
        while ((length = in.read(cbuff)) != -1) {
            output.append(cbuff, 0, length);
        }
        responseBody = output.toString();
        System.out.println("Responsebody:" + responseBody);
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void checkType(String output) {

    }

}
