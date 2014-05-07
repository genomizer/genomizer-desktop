package genomizerdesktop;

import gui.AnalyzeTab;
import gui.GUI;
import gui.ProcessTab;
import gui.QuerySearchTab;
import gui.SearchTab;
import gui.UploadTab;
import gui.WorkspaceTab;
import gui.sysadmin.SysadminTab;
import model.Model;

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
		SysadminTab sat = new SysadminTab();
		QuerySearchTab qst = new QuerySearchTab();
		// gui.setSearchTab(st);
		gui.setQuerySearchTab(qst);
		gui.setUploadTab(ut);
		gui.setProcessTab(pt);
		gui.setWorkspaceTab(wt);
		gui.setAnalyzeTab(at);
		gui.setSysAdminTab(sat);
		Connection con = new Connection("plankarta.cs.umu.se:8080");
		Model model = new Model(con);
		Controller controller = new Controller(gui, model);
		gui.showLoginWindow();
		gui.pack();
	}
}
