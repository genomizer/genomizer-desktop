package genomizerdesktop;


import gui.GUI;
import gui.ProcessTab;
import gui.QuerySearchTab;
import gui.UploadTab;
import gui.WorkspaceTab;
import gui.sysadmin.SysadminTab;

import javax.swing.SwingUtilities;

import model.ErrorLogger;
import model.Model;
import controller.Controller;

/**
 * Main Genomizer desktop startup class
 *
 */
public class Genomizer {

    public static void main(String args[]) {

        // Create GUI
        final GUI gui = new GUI();

        try{
        // Create Tabs
        UploadTab ut = new UploadTab();
        WorkspaceTab wt = new WorkspaceTab();
        ProcessTab pt = new ProcessTab();
        // AnalyzeTab at = new AnalyzeTab(); // TODO: Analyze tab not used (OO)
        SysadminTab sat = new SysadminTab();
        QuerySearchTab qst = new QuerySearchTab();

        // Set tabs in GUI
        gui.setQuerySearchTab(qst);
        gui.setUploadTab(ut);
        gui.setProcessTab(pt);
        gui.setWorkspaceTab(wt);
        // gui.setAnalyzeTab(at); // TODO: Analyze tab not used (OO)
        gui.setSysAdminTab(sat);

        // Create model and controller
        Model model = new Model();
        Controller controller = new Controller(gui, model);

        // Start the GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gui.showLoginWindow();
                gui.pack();
            }
        });
        } catch(Exception e){
            ErrorLogger.log(e);
        }


    }
}
