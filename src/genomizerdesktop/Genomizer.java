package genomizerdesktop;

import com.google.gson.Gson;
import requests.LoginRequest;
import requests.Request;

public class Genomizer {

    public static void main(String args[]) {

        Gson gson = new Gson();
        Request login = new LoginRequest("kalle", "123");
        String json = gson.toJson(login);

        GUI gui = new GUI();

        SearchTab st = new SearchTab();
        UploadTab ut = new UploadTab();
        WorkspaceTab wt = new WorkspaceTab();
        gui.setSearchTab(st);
        gui.setUploadTab(ut);
        gui.setWorkspaceTab(wt);
        Model model = new Model();
        model.attemptLogin("kalle", "123");
        //Connection con = new Connection("genomizer.apiary-mock.com", 80);
        //con.sendRequest(login);
    }
}
