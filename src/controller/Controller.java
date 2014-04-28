package controller;

import genomizerdesktop.GenomizerView;
import genomizerdesktop.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import requests.LoginRequest;
import requests.RequestFactory;
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
	// view.addLoginListener
	// view.addLogoutListener
	// view.addSearchListener
    }

    class LoginListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    // get username and pwd from view
	    LoginRequest request = RequestFactory.makeLoginRequest("Kalle",
		    "123");
	    conn.sendRequest(request, model.getUserID(), "application/json");
	    if (conn.getResponseCode() == 200) {
		LoginResponse loginResponse = ResponseParser
			.parseLoginResponse(conn.getResponseBody());
		if (loginResponse != null) {
		    model.setUserID(loginResponse.token);
		    // update view
		}
	    }
	    // update view
	}
    }

    class SearchListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	}
    }

    class LogoutListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	}
    }

}
