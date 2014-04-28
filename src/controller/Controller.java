package controller;

import genomizerdesktop.GenomizerView;
import genomizerdesktop.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import requests.LoginRequest;
import requests.LogoutRequest;
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
        view.addLoginListener(new LoginListener());
        // view.addLogoutListener
        // view.addSearchListener
    }

    class LoginListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();

        }

        @Override
        public void run() {
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

        }

        @Override
        public void run() {

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
