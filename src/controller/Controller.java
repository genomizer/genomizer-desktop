package controller;

import genomizerdesktop.GenomizerView;
import genomizerdesktop.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import requests.AddFileToExperiment;
import requests.RequestFactory;

import communication.Connection;
import communication.UploadHandler;

public class Controller {

    private GenomizerView view;
    private Model model;
    private Connection conn;
    private int a = 0;

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
	    if(a == 0) {
	    	view.updateLoginNeglected("Test fail, login again");
	    	a = 1;
	    } else {
	    	view.updateLoginAccepted(username, pwd, "Yuri Gagarin");
	    	a = 0;
	    }
	    // if (!username.isEmpty() && !pwd.isEmpty()) {
	    // LoginRequest request = RequestFactory.makeLoginRequest(
	    // username, pwd);
	    // conn.sendRequest(request, model.getUserID(), "application/json");
	    // if (conn.getResponseCode() == 200) {
	    // LoginResponse loginResponse = ResponseParser
	    // .parseLoginResponse(conn.getResponseBody());
	    // if (loginResponse != null) {
	    // model.setUserID(loginResponse.token);
	    // view.updateLoginAccepted(username, pwd);
	    // }
	    // }
	    // }
	    // view.updateLoginNeglected(username, pwd);
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
	    view.updateLogout();
	    // LogoutRequest request = RequestFactory.makeLogoutRequest();
	    // conn.sendRequest(request, model.getUserID(), "text/plain");
	    // if (conn.getResponseCode() == 200) {
	    // model.setUserID("");
	    // view.updateLogout();
	    // } else {
	    // // update view with logout failed
	    // }
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
	    if (url != null) {
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
