package controller;

import gui.GenomizerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.GenomizerModel;

public class Controller {

    private GenomizerView view;
    private GenomizerModel model;

    public Controller(GenomizerView view, GenomizerModel model) {
	this.view = view;
	this.model = model;
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
	    view.updateLoginAccepted(username, pwd, "Yuri Gagarin");
	    // if (model.loginUser(username, pwd)) {
	    // view.updateLoginAccepted(username, pwd, "Yuri Gagarin");
	    // } else {
	    // view.updateLoginNeglected("Login not accepted");
	    // }
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
	    // if (model.logoutUser()) {
	    // view.updateLogout();
	    // } else {
	    // view.updateLogout();
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

	    if (model.uploadFile()) {
		// update view?
	    }

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
