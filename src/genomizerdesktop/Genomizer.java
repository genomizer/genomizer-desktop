package genomizerdesktop;

import gui.GUI;
import gui.ProcessTab;
import gui.QuerySearchTab;
import gui.UploadTab;
import gui.WorkspaceTab;
import gui.sysadmin.SysadminTab;

import javax.swing.SwingUtilities;

import model.Model;
import controller.Controller;
// HEJSANSVEJSAN
public class Genomizer {

    public static void main(String args[]) {

        final GUI gui = new GUI();
        UploadTab ut = new UploadTab();
        WorkspaceTab wt = new WorkspaceTab();
        ProcessTab pt = new ProcessTab();
        // AnalyzeTab at = new AnalyzeTab();
        SysadminTab sat = new SysadminTab();
        QuerySearchTab qst = new QuerySearchTab();
        gui.setQuerySearchTab(qst);
        gui.setUploadTab(ut);
        gui.setProcessTab(pt);
        gui.setWorkspaceTab(wt);
        // gui.setAnalyzeTab(at);

        gui.setSysAdminTab(sat);
        Model model = new Model();
        Controller controller = new Controller(gui, model);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gui.showLoginWindow();
                gui.pack();
            }
        });


    }
}
