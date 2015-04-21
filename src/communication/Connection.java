package communication;

import gui.GenomizerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import model.ErrorLogger;

import requests.Request;

/**
 * Class representing a connection to a server (fast egentligen inte), and the communication between
 * server-client
 *
 * @author
 *
 */
public class Connection {

    // TODO: varf�r view? g�r oberoende?
    private final GenomizerView view;

    /** The IP-adress to the Server */
    private String ip;

    // TODO: skapa konstanter f�r response/status-code?
    /** HTML status code */
    private int responseCode;

    /** The response message of a request */
    private String responseBody;

    private HttpURLConnection connection;

    // TODO: anv�nds inte. ta bort?
    private Request request;

    /**
     * Constructs a new Connection object to a server with a given IP address,
     * and a given GenomizerView
     *
     * @param ip
     *            the IP address
     * @param view
     *            the GenomizerView
     */
    public Connection(String ip, GenomizerView view) {
        this.ip = ip;
        this.view = view;
        responseBody = "";
        responseCode = 0;
    }

    // TODO: returnera ett Response-objekt eller response code ist�llet f�r
    // boolean, om det beh�vs.
    /**
     * Sends a REST-request to the connected server and processes the response.
     *
     * @param request
     *            the request to be sent
     * @param token
     *            a unique identifier of the user
     * @param type
     *            the type of request (JSON or PLAIN_TEXT)
     * @return true if successful
     */
    public boolean sendRequest(Request request, String token, String type) {
        // TODO: on�dig
        this.request = request;

        if (ip.startsWith("http://")) {
            ip = ip.substring(7);
        }
        try {
            // Connect
            String targetUrl = "http://" + ip + request.url;
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
                // TODO:wtf
                view.updateLogout();
                return false;
            }
            if (responseCode >= 300) {
                return false;
            }
            connection.disconnect();
        } catch (IOException e) {
            ErrorLogger.log(e);
            try {
                InputStream is = connection.getErrorStream();
                if (is != null) {
                    fetchResponse(connection.getErrorStream());
                }
            } catch (IOException e1) {
                ErrorLogger.log(e1);
                connection.disconnect();
                return false;
            }
            connection.disconnect();
            return false;
        }
        return true;
    }

    /**
     * Builds a response body from the connection input stream
     *
     * @param inputStream
     *            the connection input stream
     * @throws IOException
     *             If an I/O error occurs
     */
    private void fetchResponse(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                inputStream));
        String buffer;
        StringBuilder output = new StringBuilder();
        while ((buffer = in.readLine()) != null) {
            output.append(buffer);
        }
        responseBody = output.toString();
        // TODO: On�dig if-sats?
        if (responseCode >= 300) {
            // err response
            // System.err.println(request.getRequestName() + " response " +
            // responseCode + " " + responseBody);
        }
    }

    /**
     * Returns the response code of a request
     *
     * @return the response code
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * Returns the response body of a request
     *
     * @return the response body
     */
    public String getResponseBody() {
        return responseBody;
    }

    // TODO: unused? ta bort?
    public void checkType(String output) {

    }

}
