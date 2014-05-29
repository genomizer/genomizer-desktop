package communication;

import gui.GenomizerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import requests.Request;

public class Connection {
    private final GenomizerView view;
    private String ip;
    private int responseCode;
    private String responseBody;
    private HttpURLConnection connection;
    private Request request;

    public Connection(String ip, GenomizerView view) {
        this.ip = ip;
        this.view = view;
        responseBody = "";
        responseCode = 0;
    }

    public boolean sendRequest(Request request, String token, String type) {
        this.request = request;

        if (ip.startsWith("http://")) {
            ip = ip.substring(7);
        }
        try {
            String targetUrl = "http://" + ip + request.url;
//            System.out.println(targetUrl);
//            System.out.println("the request.toJson(): " + request.toJson());
            URL url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            if (type.equals("application/json")) {
                connection.setDoOutput(true);
            }
            connection.setReadTimeout(2000);
            connection.setRequestMethod(request.type);
            connection.setRequestProperty("Content-Type", type);
            if (!token.isEmpty()) {
                connection.setRequestProperty("Authorization", token);
            }

            if (request.type.equals("DELETE")) {
                // connection.connect();
                responseCode = connection.getResponseCode();
                fetchResponse(connection.getInputStream());
                return (!(responseCode >= 300));
            }

            if (type.equals("application/json")) {
                PrintWriter outputStream = new PrintWriter(
                        connection.getOutputStream(), true);
                outputStream.println(request.toJson());
                outputStream.flush();
            }
            responseCode = connection.getResponseCode();
            fetchResponse(connection.getInputStream());
            if (responseCode == 401 && !token.isEmpty()) {
                view.updateLogout();
                return false;
            }
            if (responseCode >= 300) {
                return false;
            }
            connection.disconnect();
        } catch (IOException e) {
            try {
                InputStream is = connection.getErrorStream();
                if (is != null) {
                    fetchResponse(connection.getErrorStream());
                }
            } catch (IOException e1) {
                // e1.printStackTrace();
                connection.disconnect();
                return false;
            }
            connection.disconnect();
            return false;
        }
        return true;
    }

    private void fetchResponse(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                inputStream));
        String buffer;
        StringBuilder output = new StringBuilder();
        while ((buffer = in.readLine()) != null) {
            output.append(buffer);
        }
        responseBody = output.toString();
        if (responseCode >= 300) {
            // err response
//        System.err.println(request.getRequestName() + " response " + responseCode + " " + responseBody);
        }
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
