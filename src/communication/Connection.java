package communication;

import gui.GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

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


    private static final int TIME_OUT_MS = 2000;


    /** The IP-adress to the Server */
    private String ip;

    // TODO: skapa konstanter f�r response/status-code?
    /** HTML status code */
    private int responseCode;

    /** The response message of a request */
    private String responseBody;

    private HttpsURLConnection connection;

    /**
     * Constructs a new Connection object to a server with a given IP address,
     * and a given GUI
     *
     * @param ip
     *            the IP address
     * @param view
     *            the GUI
     */
    public Connection(String ip) {

        if(!ip.startsWith("https://")) {
            ip = "https://" + ip;
        }

        this.ip = ip;
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
     * @throws RequestException
     *             if an error occurs (i.e HTTP response code is > 201)
     */
    public void sendRequest(Request request, String token, String type)
            throws RequestException {
        try {
            connect(request, token, type);
            if (request.requestType.equals("DELETE")) {
                responseCode = connection.getResponseCode();
                fetchResponse(connection.getInputStream());
                if (responseCode >= 300) {
                    connection.disconnect();
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


            if (responseCode > 300) {
                ErrorLogger.log(responseBody);
                connection.disconnect();
                throw new RequestException(responseCode, responseBody);
            }

            connection.disconnect();
        } catch (IOException e) {

            ErrorLogger.log(e);
            try {
                InputStream inputStream = connection.getErrorStream();
                if (inputStream != null) {
                    fetchResponse(connection.getErrorStream());

                    //TODO Fixa så att man kan kasta meddelanden för alla koder >300
                    if(responseCode>=300){
                        connection.disconnect();
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

        URL url = new URL(ip + request.url);

        connection = (HttpsURLConnection) url.openConnection();


        if (type.equals("application/json")) {
            connection.setDoOutput(true);
        }
        connection.setReadTimeout(TIME_OUT_MS);
        connection.setRequestMethod(request.requestType);
        connection.setRequestProperty("Content-Type", type);

        if (!token.isEmpty()) {
            connection.setRequestProperty("Authorization", token);
        }

        connection.connect();

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

