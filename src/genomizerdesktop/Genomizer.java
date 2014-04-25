package genomizerdesktop;

import requests.*;

import com.google.gson.Gson;
import communication.Connection;

public class Genomizer {

    public static void main(String args[]) {

	Gson gson = new Gson();
	Request login = new LoginRequest("kalle", "123");
	String json = gson.toJson(login);

	GUI gui = new GUI();
	SearchTab st = new SearchTab();
	UploadTab ut = new UploadTab();
	gui.setSearchTab(st);
	gui.setUploadTab(ut);
	Connection con = new Connection("127.0.0.1", 25652);
	con.sendRequest(login);
    }
}
