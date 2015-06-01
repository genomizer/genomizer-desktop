package genomizerdesktop;

import gui.ConvertTab;
import gui.ErrorDialog;
import gui.GUI;
import gui.QuerySearchTab;
import gui.SettingsTab;
import gui.UploadTab;
import gui.WorkspaceTab;
import gui.processing.ProcessTab;
import gui.sysadmin.SysadminTab;

import javax.swing.SwingUtilities;

import model.ErrorLogger;
import model.Model;

import communication.SSLTool;

import model.ErrorLogger;
import model.Model;
import model.SessionHandler;
import model.User;
import controller.Controller;

/**
 * Main Genomizer desktop startup class
 *
 */
public class Genomizer {
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                Runtime.getRuntime().addShutdownHook(new Thread() {

                    @Override
                    public void run() {

                        if(User.getInstance().isLoggedIn()) {

                            SessionHandler.getInstance().logoutUser();

                        }

                    }

                });

                // TODO
                SSLTool.disableCertificateValidation();
                // Create GUI
                final GUI gui = new GUI();

                ErrorDialog.setParentComponent(gui);

                try {
                    // Create Tabs
                    UploadTab ut = new UploadTab();
                    WorkspaceTab wt = new WorkspaceTab();
                    ProcessTab pt = new ProcessTab();
                    // AnalyzeTab at = new AnalyzeTab(); // TODO: Analyze tab
                    // not used
                    // (OO)
                    SysadminTab sat = new SysadminTab();
                    QuerySearchTab qst = new QuerySearchTab();
                    ConvertTab ct = new ConvertTab();
                    SettingsTab st = new SettingsTab();

                    // Set tabs in GUI
                    gui.setQuerySearchTab(qst);
                    gui.setUploadTab(ut);
                    gui.setProcessTab(pt);
                    gui.setWorkspaceTab(wt);
                    // gui.setAnalyzeTab(at); // TODO: Analyze tab not used (OO)
                    gui.setSysAdminTab(sat);
                    gui.setConvertTab(ct);
                    gui.setSettingsTab(st);
                    // Create model and controller
                    Model model = new Model();
                    Controller controller = new Controller(gui, model);

                    // Start the GUI
                    // TODO: Maybe put EDT on other parts?
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            gui.showLoginWindow();
                            // Set tabs in GUI
                            gui.pack();

                        }
                    });
                } catch (Exception e) {
                    ErrorLogger.log(e);
                }

            }
        });

    }
}
