package genomizerdesktop;

import communication.Connection;
import controller.Controller;
import gui.*;
import gui.sysadmin.SysadminTab;
import model.Model;

import javax.swing.*;

public class Genomizer {

    public static void main(String args[]) {

        final GUI gui = new GUI();
        SearchTab st = new SearchTab();
        UploadTab ut = new UploadTab();
        ProcessTab pt = new ProcessTab();
        WorkspaceTab wt = new WorkspaceTab();
        AnalyzeTab at = new AnalyzeTab();
        SysadminTab sat = new SysadminTab();
        QuerySearchTab qst = new QuerySearchTab();
        gui.setQuerySearchTab(qst);
        gui.setUploadTab(ut);
        gui.setProcessTab(pt);
        gui.setWorkspaceTab(wt);
        // gui.setAnalyzeTab(at);
        gui.setSysAdminTab(sat);
        Connection con = new Connection("http://scratchy.cs.umu.se:7000");
        Model model = new Model(con);
        Controller controller = new Controller(gui, model);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gui.showLoginWindow();
                gui.pack();
            }
        });
    }
}
