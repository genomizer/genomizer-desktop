package communication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
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
			PrintWriter out = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);
			System.out.println(request.toJson());
			out.println(request.toJson());
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
