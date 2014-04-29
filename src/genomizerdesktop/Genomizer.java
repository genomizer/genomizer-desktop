package genomizerdesktop;

import gui.AnalyzeTab;
import gui.GUI;
import gui.ProcessTab;
import gui.SearchTab;
import gui.SysadminTab;
import gui.UploadTab;
import gui.WorkspaceTab;
import gui.QuerySearchTab;
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
	gui.setSearchTab(st);
	gui.setUploadTab(ut);
	gui.setProcessTab(pt);
	gui.setWorkspaceTab(wt);
	gui.setAnalyzeTab(at);
	gui.setSysAdminTab(sat);
	gui.setQuerySearchTab(qst);
	Connection con = new Connection("127.0.0.1", 25652);
	Model model = new Model(con);
	Controller controller = new Controller(gui, model);
	gui.showLoginWindow();
    }
}
