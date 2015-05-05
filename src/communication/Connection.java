package communication;

import gui.GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import model.ErrorLogger;

import requests.Request;
import util.RequestException;

/**
 * Class representing a connection to a server (fast egentligen inte), and the
 * communication between server-client
 *
 * @author
 *
 */
public class Connection {

    // TODO: varfï¿½r view? gï¿½r oberoende?
    private final GUI view;

    /** The IP-adress to the Server */
    private String ip;

    // TODO: skapa konstanter fï¿½r response/status-code?
    /** HTML status code */
    private int responseCode;

    /** The response message of a request */
    private String responseBody;

    private HttpURLConnection connection;

    /**
     * Constructs a new Connection object to a server with a given IP address,
     * and a given GUI
     *
     * @param ip
     *            the IP address
     * @param view
     *            the GUI
     */
    public Connection(String ip, GUI view) {
        this.ip = ip;
        this.view = (GUI) view;
        responseBody = "";
        responseCode = 0;
    }

    // TODO: returnera ett Response-objekt eller response code istï¿½llet fï¿½r
    // boolean, om det behï¿½vs.
    /**
     * Sends a REST-request to the connected server and processes the response.
     *
     * @param request
     *            the request to be sent
     * @param token
     *            a unique identifier of the user
     * @param type
     *            the type of request (JSON or PLAIN_TEXT)
     * @throws RequestException
     *             if an error occurs (i.e HTTP response code is > 201)
     */
    public void sendRequest(Request request, String token, String type)
            throws RequestException {
        try {
            connect(request, token, type);

            if (request.type.equals("DELETE")) {
                responseCode = connection.getResponseCode();
                fetchResponse(connection.getInputStream());
                if (responseCode >= 300) {
                    throw new RequestException(responseCode, responseBody);
                }
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
                throw new RequestException(responseCode, responseBody);
            }
            if (responseCode >= 300) {
                ErrorLogger.log(responseBody);
                throw new RequestException(responseCode, responseBody);
            }
            connection.disconnect();
        } catch (IOException e) {

            ErrorLogger.log(e);
            try {
                InputStream is = connection.getErrorStream();
                if (is != null) {
                    fetchResponse(connection.getErrorStream());

                    //TODO Fixa så att man kan kasta meddelanden för alla koder >300
                    if(responseCode==300){
                        throw new RequestException(responseCode, responseBody);
                    }
                    if(responseCode==503){
                        throw new RequestException(responseCode, responseBody);
                    }

                }
            } catch (IOException e1) {
                ErrorLogger.log(e1);
                connection.disconnect();
                throw new RequestException(responseCode, responseBody);
            }
            connection.disconnect();
        }
    }

    private void connect(Request request, String token, String type)
            throws MalformedURLException, IOException, ProtocolException {

        String targetUrl;

        if (ip.startsWith("http://")) {
            targetUrl = ip + request.url;
        } else {
            targetUrl = "http://" + ip + request.url;
        }

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
        // TODO: Onï¿½dig if-sats?
        if (responseCode >= 300) {
            ErrorLogger.log(responseBody);
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
