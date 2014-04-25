package communication;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.LinkedList;

import requests.*;

public class Connection implements Runnable {
	private String ip;
	private int port;
	private Socket socket;
	private LinkedList<String> incomingMessages = new LinkedList<String>();
	private boolean running = true;

	public Connection(String ip, int port) {
		this.ip = ip;
		this.port = port;
		setupNetwork();
	}

	public void setupNetwork() {
		try {
			socket = new Socket(ip, port);
		} catch (UnknownHostException e) {
			System.out.println("Unknown host: " + ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
            System.out.println(response.readLine());
            connection.disconnect();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public void stopListening() {
		running = false;
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			while (running) {
				String message = in.readLine();
				if(message == null) {
					running = false;
					break;
				}
				System.out.println(message);
				incomingMessages.add(message);
				System.out.println(incomingMessages);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			running = false;
			e.printStackTrace();
		}
	}
}
