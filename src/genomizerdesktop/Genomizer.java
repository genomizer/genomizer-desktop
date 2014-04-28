package genomizerdesktop;

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
	Model model = new Model();
	Controller controller = new Controller(gui, model, con);
	gui.showLoginWindow();
    }
}
