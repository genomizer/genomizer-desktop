// TODO SORTERA SKITEN
// TODO WHY IS CONTROLLER EVEN A CLASS TO USE

package controller;

import gui.DownloadWindow;
import gui.GenomizerView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.ErrorLogger;
import model.GenomizerModel;
import util.AnnotationDataType;
import util.ExperimentData;
import util.FileData;

/**
 * Controller class responsible for setting the correct actions to the listening
 * buttons and other component. This will drive the actions started via the GUI.
 */
public class Controller {

    private GenomizerView view;
    private GenomizerModel model;
    private final JFileChooser fileChooser = new JFileChooser();
    private boolean runonce;

    public Controller(GenomizerView view, GenomizerModel model) {
        this.view = view;
        this.model = model;
        updateView();
        runonce = true;
    }

    /**
     * Update **ALL** the actionlisteners in the whole wide gui.
     */
    private void updateView() {

        loginWindowUpdate();

        userPanelUpdate();

        ratioCalcUpdate();

        tabbedPaneUpdate();

        // unimplementedUpdate();
    }

    /**
     * Update all the actionlisteners in the tabs.
     */
    public void updateTabs() {

        QuerySearchTabController querySearchTabController = new QuerySearchTabController(
                view, model);
        view.getQuerySearchTab().setController(querySearchTabController);
        ProcessTabController processTabController = new ProcessTabController(
                view, model);
        view.getProcessTab().setController(processTabController);
        WorkspaceTabController workspaceTabController = new WorkspaceTabController(
                view, model, fileChooser);
        view.getWorkSpaceTab().setController(workspaceTabController);
        UploadTabController uploadTabController = new UploadTabController(view,
                model, fileChooser);
        view.getUploadTab().setController(uploadTabController);

        SysadminController sysadminTabController = new SysadminController(model);
        view.setSysadminController(sysadminTabController);
        sysadminTabController.updateAnnotationTable();
        sysadminTabController.updateGenomeReleaseTab();
    }

    /**
     * Update the tabbed-pane listeners
     */
    private void tabbedPaneUpdate() {
        view.addChangedTabListener(new ChangedTabListener());
    }

    /**
     * Update the loginWindow listeners
     */
    private void loginWindowUpdate() {
        view.getLoginWindow().addLoginListener(new LoginListener());
    }

    /**
     * Update the userPanel listeners
     */
    private void userPanelUpdate() {
        view.addLogoutListener(new LogoutListener());
    }

    /**
     * Update the ratioCalcWindow listeners
     */
    private void ratioCalcUpdate() {
        view.addOkListener(new OkListener());
        view.addRatioCalcListener(new RatioCalcListener());
    }

    /**
     * Listener for when tabs are changed. Will for some tabs perform automatic
     * updates.
     *
     * TODO: separate view from Thread
     */
    class ChangedTabListener implements ChangeListener, Runnable {

        @Override
        public void stateChanged(ChangeEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            AnnotationDataType[] a;
            if (view.getSelectedIndex() == 1) {
                if (((a = model.getAnnotations()) != null)
                        && view.getUploadTab().newExpStarted()) {
                    view.getUploadTab().getNewExpPanel().updateAnnotations(a);
                }
            } else if (view.getSelectedIndex() == 0) {
                if ((a = model.getAnnotations()) != null) {
                    view.setSearchAnnotationTypes(a);
                }
            }
        }
    }

    /**
     * Listener to convert files. Should convert files between different
     * formats. TODO: Not completed.
     */
    class ConvertFileListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            // TODO ConvertFile-listener doesn't do anything
        }
    }

    /**
     * The listener to create region data, TODO: Not completed at all
     *
     * @author c11ann
     */
    class RawToRegionDataListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {

            // TODO: Raw To Region Data Listener doesn't do anything.


        }
    }

    /**
     * Listen to the login button. Will send the entered name and password, and
     * if accepted update view. TODO: Move view bits from Thread
     */
    class LoginListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            model.setGenomizerView(view);
            model.setIp(view.getIp());
            String username = view.getUsername();
            String pwd = view.getPassword();
            String response = model.loginUser(username, pwd);

            // TODO: extract stupid .equals true to a domain object boolean
            // thingy
            if (response.equals("true")) {
                view.updateLoginAccepted(username, pwd, "Desktop User");

                if (runonce) {
                    updateTabs();
                    runonce = false;
                } else{
                    view.getSysAdminTab().getController().updateAnnotationTable();
                    view.getSysAdminTab().getController().updateGenomeReleaseTab();
                }

                ErrorLogger.log("Login", username + " logged in");
            } else {
                view.updateLoginNeglected(response);
                ErrorLogger.log(response);
            }
        }
    }

    /**
     * Listen to the logout button. Will call logout and reset methods of the
     * model, and also update and reset view. (Because of this also reset
     * relevant parts of the controller.) TODO: Separate view part of Thread.
     */
    class LogoutListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            int response = JOptionPane.showConfirmDialog(null,
                    "Are you sure you wish to log out?", "Log out",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                model.logoutUser();
                model.resetModel();
                view.updateLogout();
                view.resetGUI();
                // If only tabs are updated then only these methods will be
                // needed.
                updateTabs();
                ErrorLogger.log("Logout", "User logged out");
            }
        }
    }

    /**
     * Listener for when the download button in workspace is clicked. Opens a
     * DownloadWindow with the selected files.
     *
     * TODO: separate view parts from Thread. Move to correct tab controller?
     */
    class DownloadWindowListener implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            new Thread(this).start();
        }

        @Override
        public void run() {
            // Skicka med arraylist<FileData> för de filer som ska nerladdas
            ArrayList<ExperimentData> selectedData = view
                    .getSelectedDataInWorkspace();
            ArrayList<FileData> selectedFiles = new ArrayList<>();
            for (ExperimentData experiment : selectedData) {
                for (FileData file : experiment.files) {
                    if (!selectedFiles.contains(file)) {
                        selectedFiles.add(file);
                    }
                }
            }
            DownloadWindow downloadWindow = new DownloadWindow(selectedFiles,
                    model.getOngoingDownloads());
            view.setDownloadWindow(downloadWindow);
            downloadWindow.setVisible(true);
        }
    }

    /**
     * Show the ratioCalc popup. TODO: Remove Thread
     */
    class RatioCalcListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            view.showRatioPopup();
        }
    }

    /**
     * Listen to the OK button in the ratioCalc popup. Will hide the window.
     * TODO: Remove the Thread, should OK do something more?
     */
    class OkListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            view.getRatioCalcPopup().hideRatioWindow();
        }

    }
}
