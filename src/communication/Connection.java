package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import requests.Request;

import javax.swing.*;

public class Connection {
    private String ip;
    private int responseCode;
    private String responseBody;
    
    public Connection(String ip) {
        this.ip=ip;
    }
    
    public boolean sendRequest(Request request, String userID, String type) {
        if (ip.startsWith("http://")) {
            ip = ip.substring(7);
        }
        try {
            String targetUrl = "http://" + ip + request.url;
            responseBody = "";
            responseCode = 0;
            System.out.println(targetUrl);
            System.out.println("the request.toJson(): " + request.toJson());
            URL url = new URL(targetUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            if (type.equals("application/json")) {
                connection.setDoOutput(true);
            }
            
            connection.setReadTimeout(1000);
            connection.setRequestMethod(request.type);
            connection.setRequestProperty("Content-Type", type);
            if (!userID.isEmpty()) {
                connection.setRequestProperty("Authorization", userID);
            }
            
            if (request.type.equals("DELETE")) {
                connection.connect();
                responseCode = connection.getResponseCode();
                if (responseCode >= 300) {
                    JOptionPane.showMessageDialog(null,
                            "Connection error: " + responseCode);
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
            if (responseCode >= 300) {
                JOptionPane.showMessageDialog(null,"Connection error: " + responseCode);
                return false;
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String buffer;
            StringBuilder output = new StringBuilder();
            while ((buffer = in.readLine()) != null) {
                output.append(buffer);
            }
            responseBody = output.toString();
            System.out.println(responseBody);
            connection.disconnect();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Connection error: " + e.getMessage());
            return false;
        }
        return true;
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
