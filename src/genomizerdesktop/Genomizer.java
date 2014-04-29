package genomizerdesktop;

import gui.AnalyzeTab;
import gui.GUI;
import gui.ProcessTab;
import gui.SearchTab;
import gui.UploadTab;
import gui.WorkspaceTab;
import model.GenomizerModel;

import communication.Connection;

import controller.Controller;

public class Genomizer {

    public static void main(String args[]) {

	GUI gui = new GUI();
	SearchTab st = new SearchTab();
	UploadTab ut = new UploadTab();
	ProcessTab pt = new ProcessTab();
	WorkspaceTab wt = new WorkspaceTab();
	AnalyzeTab at = new AnalyzeTab();
	gui.setSearchTab(st);
	gui.setUploadTab(ut);
	gui.setProcessTab(pt);
	gui.setWorkspaceTab(wt);
	gui.setAnalyzeTab(at);
	Connection con = new Connection("127.0.0.1", 25652);
	GenomizerModel model = new GenomizerModel(con);
	Controller controller = new Controller(gui, model);
	gui.showLoginWindow();
    }
}
