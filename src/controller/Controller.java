package controller;

import genomizerdesktop.GenomizerView;
import genomizerdesktop.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import requests.*;
import communication.UploadHandler;
import responses.LoginResponse;
import responses.ResponseParser;

import communication.Connection;

public class Controller {

    private GenomizerView view;
    private Model model;
    private Connection conn;

    public Controller(GenomizerView view, Model model, Connection conn) {
        this.view = view;
        this.model = model;
        this.conn = conn;
        view.addLoginListener(new LoginListener());
        view.addLogoutListener(new LogoutListener());
        view.addSearchListener(new SearchListener());
        view.addUploadFileListener(new UploadListener());
    }

    class LoginListener implements ActionListener, Runnable {
	@Override
	public void actionPerformed(ActionEvent e) {
	    new Thread(this).start();
	}

	@Override
	public void run() {
	    String username = view.getUsername();
	    String pwd = view.getPassword();
	    if (!username.isEmpty() && !pwd.isEmpty()) {
		LoginRequest request = RequestFactory.makeLoginRequest(
			username, pwd);
		conn.sendRequest(request, model.getUserID(), "application/json");
		if (conn.getResponseCode() == 200) {
		    LoginResponse loginResponse = ResponseParser
			    .parseLoginResponse(conn.getResponseBody());
		    if (loginResponse != null) {
			model.setUserID(loginResponse.token);
			view.updateLoginAccepted(username, pwd);
		    }
		}
	    }
	    view.updateLoginNeglected(username, pwd);
	}
    }

    class SearchListener implements ActionListener, Runnable {
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void run() {

	}
    }

    class LogoutListener implements ActionListener, Runnable {
	public void actionPerformed(ActionEvent e) {
	    new Thread(this).start();
	}

	@Override
	public void run() {
	    LogoutRequest request = RequestFactory.makeLogoutRequest();
	    conn.sendRequest(request, model.getUserID(), "text/plain");
	    if (conn.getResponseCode() == 200) {
		model.setUserID("");
		// update view with logout
	    } else {
		// update view with logout failed
	    }
	}
    }

    class UploadListener implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new Thread(this).start();
        }

        @Override
        public void run() {

            AddFileToExperiment request = RequestFactory.makeAddFile("test",
                    "test", "1.3GB", "raw");
            conn.sendRequest(request, model.getUserID(), "application/json");
            String url = conn.getResponseBody();
            if(url != null) {
                System.out.println(url);
            }
            UploadHandler handler = new UploadHandler(
                    "http://127.0.0.1:25652/test",
                    "/home/dv12/dv12csr/edu/test321", model.getUserID());
            Thread thread = new Thread(handler);
            thread.start();

        }
    }

    class DownloadListener implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }

        @Override
        public void run() {

        }
    }
}
