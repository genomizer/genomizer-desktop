package gui;

import gui.sysadmin.SysadminTab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeListener;

import model.ErrorLogger;

import util.ActiveSearchPanel;
import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.FileData;
import util.GenomeReleaseData;
import util.Process;
import util.ProcessFeedbackData;

import communication.DownloadHandler;
import communication.HTTPURLUpload;
import controller.SysadminController;

public class GUI extends JFrame {

    private static final long serialVersionUID = 6659839768426124853L;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private UserPanel userPanel;
    private UploadTab uploadTab;
    private WorkspaceTab workspaceTab;
    private LoginWindow loginWindow;
    private ProcessTab processTab;
    private SysadminTab sysadminTab;
    private QuerySearchTab querySearchTab;
    private DownloadWindow downloadWindow;
    private RatioCalcPopup ratioCalcPopup;
    // private AnalyzeTab at;

    private JPanel statusPanel;
    private ConvertTab convertTab;

    /**
     * Initiates the main view of the program.
     */
    public GUI() {

        setLookAndFeel();

        /*
         * When the window is activated, set the focus to the search button.
         * This prevents the user from accidentally pressing the log out button
         * after logging in.
         */
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                querySearchTab.getSearchButton().requestFocusInWindow();
            }
        });

        this.setTitle("Genomizer");
        setSize(1024, 768);
        this.setMinimumSize(new Dimension(1024, 768));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout bl = new BorderLayout();
        mainPanel = new JPanel(bl);
        userPanel = new UserPanel();
        loginWindow = new LoginWindow(this);
        ratioCalcPopup = new RatioCalcPopup(this);

        add(mainPanel);

        mainPanel.add(userPanel, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        URL url = ClassLoader.getSystemResource("icons/logo.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        setIconImage(img);

        statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(mainPanel.getWidth(), 16));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));

        this.setLocationRelativeTo(null);
    }

    /**
     * Set a new statusmessage
     *
     * @param status
     *            New status
     * @author JH
     */
    public void setStatusPanel(String status) {
        statusPanel.removeAll();
        JLabel statusLabel = new JLabel(status);
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    /**
     * adds a ChangeListener to the tabbedPane not used...? TODO unuseds
     *
     * @see gui.GenomizerView#addChangedTabListener(javax.swing.event.ChangeListener)
     */
    public void addChangedTabListener(ChangeListener listener) {
        tabbedPane.addChangeListener(listener);
    }

    /**
     * returns the index of the currently select item in the tabbed pane
     *
     * @see gui.GenomizerView#getSelectedIndex()
     */
    public int getSelectedIndex() {
        return tabbedPane.getSelectedIndex();
    }

    public void addUploadToListener(ActionListener listener) {
        workspaceTab.addUploadToListener(listener);
    }

    public void addUploadToListenerSearchTab(ActionListener listener) {
        querySearchTab.addUploadToListener(listener);
    }

    public LoginWindow getLoginWindow() {
        return loginWindow;
    }

    public void addLoginListener(ActionListener listener) {
        loginWindow.addLoginListener(listener);
    }

    public void addUpdateSearchAnnotationsListener(ActionListener listener) {
        querySearchTab.addUpdateAnnotationsListener(listener);
    }

    public void addProcessFileListener(ActionListener listener) {
        workspaceTab.addProcessFileListener(listener);
    }

    public void addConvertFileListener(ActionListener listener) {
        convertTab.addConvertFileListener(listener);
    }

    public void addRawToProfileDataListener(ActionListener listener) {
        processTab.addRawToProfileDataListener(listener);
    }

    public void addSearchToWorkspaceListener(ActionListener listener) {
        querySearchTab.addAddToWorkspaceButtonListener(listener);
    }

    public void addProcessFeedbackListener(ActionListener listener) {
        processTab.addProcessFeedbackListener(listener);
    }

    public void addLogoutListener(ActionListener listener) {

        userPanel.addLogoutButtonListener(listener);
    }

    public void addSearchListener(ActionListener listener) {
        // TODO Auto-generated method stub
    }

    public void addQuerySearchListener(ActionListener listener) {
        querySearchTab.addSearchButtonListener(listener);
    }

    public void addDownloadFileListener(ActionListener listener) {
        workspaceTab.addDownloadFileListener(listener);
    }

    public void addAddToExistingExpButtonListener(ActionListener listener) {
        uploadTab.addAddToExistingExpButtonListener(listener);
    }

    public void addAddToExistingExpButtonListenerInSearch(
            ActionListener listener) {
        uploadTab.addAddToExistingExpButtonListener(listener);
    }

    public void addSelectFilesToUploadButtonListener(ActionListener listener) {
        uploadTab.getExistExpPanel().addSelectFilesToUploadButtonListener(
                listener);
    }

    public void addUploadToExperimentButtonListener(ActionListener listener) {
        uploadTab.getExistExpPanel().addUploadToExperimentButtonListener(
                listener);
    }

    public void addDeleteFromDatabaseListener(ActionListener listener) {
        workspaceTab.addDeleteSelectedListener(listener);
    }

    public void addSearchResultsDownloadListener(ActionListener listener) {
        querySearchTab.addDownloadButtonListener(listener);
    }

    public void addToWorkspace(ArrayList<ExperimentData> experiments) {
        workspaceTab.addExperimentsToTable(experiments);
    }

    /**
     * @return The data (files or experiments) that were selected in search.
     */
    public ArrayList<ExperimentData> getSelectedDataInSearch() {
        return querySearchTab.getSelectedData();
    }

    /**
     * @return The uploadTab.
     */
    public UploadTab getUploadTab() {
        return uploadTab;
    }

    /**
     * @return The querySearchTab's searchString.
     */
    public String getQuerySearchString() {
        return querySearchTab.getSearchString();
    }

    /**
     * @return The marked files from the process tab.
     */
    public ArrayList<FileData> getAllMarkedFiles() {
        return processTab.getAllMarkedFiles();

    }

    /**
     * @return The password input from the login window.
     */
    public String getPassword() {
        return loginWindow.getPasswordInput();
    }

    /**
     * @return The username input from the login window.
     */
    public String getUsername() {
        return loginWindow.getUsernameInput();
    }

    /**
     * @return The IP input from the login window.
     */
    public String getIp() {
        return loginWindow.getIPInput();
    }

    public int getSelectedRowAtAnnotationTable() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Returns the GUI
     *
     * @return The GUI
     */
    public JFrame getFrame() {
        return this;
    }

    /**
     * Sets the downloadWindow attribute of the GUI.
     *
     * @param downloadWindow
     *            The DownloadWindow to set the GUI's downloadWindow attribute
     *            to.
     */
    public void setDownloadWindow(DownloadWindow downloadWindow) {
        this.downloadWindow = downloadWindow;
    }

    /**
     * Is run when the user has logged in, makes the GUI visible, hides the
     * loginWindow
     *
     * @see controller.Controller#LoginListener()
     * @param username
     *            the username the user logged in with
     * @param pwd
     *            the password he user used, unused...? //TODO pwd unused
     * @param name
     *            the name of the user //TODO static
     */
    public void updateLoginAccepted(String username, String pwd, String name) {
        userPanel.setUserInfo(username, name, false);
        refreshGUI();
        this.setVisible(true);
        loginWindow.removeErrorMessage();
        loginWindow.setVisible(false);
        querySearchTab.clickUpdateAnnotations();
    }

    /**
     *
     * @param errorMessage
     */
    public void updateLoginNeglected(String errorMessage) {
        loginWindow.updateLoginFailed(errorMessage);

    }

    /**
     * Makes GUI invisible, shows the loginWindow
     */
    public void updateLogout() {

        this.setVisible(false);

        loginWindow.setVisible(true);
    }

    /**
     *
     * @param searchResults
     */
    public void updateQuerySearchResults(ArrayList<ExperimentData> searchResults) {
        querySearchTab.updateSearchResults(searchResults);
    }

    /**
     * Sets the GUI's processTab attribute.
     *
     * @param processTab
     *            The ProcessTab to set the GUI's attribute to.
     */
    public void setProcessTab(ProcessTab processTab) {
        this.processTab = processTab;
        tabbedPane.addTab("PROCESS", null, processTab, "Process");

    }

    /**
     * Sets the look and feel of the view.
     */
    private void setLookAndFeel() {

        try {

            UIManager
                    .setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            ErrorLogger.log(e);
            // TODO H�r har dom l�mnat tomt CF
            // If Nimbus is not available, you can set the GUI to another look
            // and feel.

        }
    }

    /**
     * Sets the uploadTab of the GUI. Also sets the name of the tab in the
     * tabbedPane.
     *
     * @param uploadTab
     *            The UploadTab to set the attribute to.
     */
    public void setUploadTab(UploadTab uploadTab) {
        this.uploadTab = uploadTab;
        tabbedPane.addTab("UPLOAD", null, uploadTab, "Upload");
    }

    // TODO: Setup Analyze tab (OO)
    // public void setAnalyzeTab(AnalyzeTab at) {
    // this.at = at;
    // tabbedPane.addTab("at", null, at, "at");
    //
    // }

    /**
     * Sets the workspaceTab of the GUI. Also sets the name of the tab in the
     * tabbedPane.
     *
     * @param workspaceTab
     *            The WorkspaceTab to set the attribute to.
     */
    public void setWorkspaceTab(WorkspaceTab workspaceTab) {
        this.workspaceTab = workspaceTab;
        tabbedPane.addTab("WORKSPACE", null, workspaceTab, "Workspace");
    }

    /**
     * Returns the WorkspaceTab, used by controller
     *
     * @return workspaceTab The WorkspaceTab
     */
    public WorkspaceTab getWorkSpaceTab() {
        return workspaceTab;
    }

    /**
     * Sets the sysadminTab of the GUI. Also sets the name of the tab in the
     * tabbedPane.
     *
     * @param sat
     *            The SysadminTab to set the attribute to.
     */
    public void setSysAdminTab(SysadminTab sat) {
        this.sysadminTab = sat;
        tabbedPane.addTab("ADMINISTRATION", null, sysadminTab,
                "System Administration");

    }

    /**
     * Returns the SysadminTab, used by controller
     *
     * @return sysadminTab The SysadminTab
     */
    public SysadminTab getSysAdminTab() {
        return sysadminTab;
    }

    /**
     * Sets the querySearchTab of the GUI. Also sets the name of the tab in the
     * tabbedPane.
     *
     * @param qst
     *            The QuerySearchTab to set the attribute to.
     */
    public void setQuerySearchTab(QuerySearchTab qst) {
        this.querySearchTab = qst;
        tabbedPane.addTab("SEARCH", null, querySearchTab, "Search");
    }

    /**
     * Returns the querySearchTab, used by controller
     *
     * @return the querySearchTab
     */
    public QuerySearchTab getQuerySearchTab() {
        return querySearchTab;
    }

    public void refreshSearch() {
        querySearchTab.refresh();
    }

    /**
     * Sets the annotationTypes of the querySearchTab.
     *
     * @param annotationTypes
     *            An array containing AnnotationDataTypes to set the
     *            querySearchTab's annotationTypes to.
     */
    public void setSearchAnnotationTypes(AnnotationDataType[] annotationTypes) {
        querySearchTab.setAnnotationTypes(annotationTypes);
    }

    // TODO: Setup Analyze tab (OO)
    /*
     * @Override public void setAnnotationTableData(AnnotationDataType[]
     * annotations) { sysadminTab.setAnnotationTableData(annotations); }
     */

    /**
     * TODO unfinished
     *
     * @param allFileData
     */
    public void setProcessFileList(ArrayList<FileData> allFileData) {

        ArrayList<FileData> fileArray = allFileData;

        tabbedPane.setSelectedIndex(2);
        processTab.setFileInfo(workspaceTab.getSelectedData());

    }

    /**
     *
     * @param message
     */
    public void printToConsole(String message) {
        processTab.printToConsole(message);
    }

    /**
     * @return The selected data in the workspace in the form of an arrayList
     *         containing the ExperimentData.
     */
    public ArrayList<ExperimentData> getSelectedDataInWorkspace() {

        return workspaceTab.getSelectedData();
    }

    public ArrayList<ExperimentData> getSelectedExperimentsInWorkspace() {
        return workspaceTab.getSelectedExperiments();
    }

    /**
     * Repaint and revalidate the view.
     */
    public void refreshGUI() {
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    /**
     * Makes the loginWindow visible.
     */
    public void showLoginWindow() {
        loginWindow.setVisible(true);
    }

    public void setSysadminController(SysadminController sysadminController) {
        sysadminTab.setController(sysadminController);

    }

    public void createNewExp(AnnotationDataType[] annotations) {
        uploadTab.addNewExpPanel(annotations);
    }

    public void addNewExpButtonListener(ActionListener listener) {
        uploadTab.addNewExpButtonListener(listener);
    }

    public void addSelectButtonListener(ActionListener listener) {
        uploadTab.getNewExpPanel().addSelectButtonListener(listener);
    }

    public void addUploadButtonListener(ActionListener listener) {
        uploadTab.getNewExpPanel().addUploadButtonListener(listener);
    }

    public void addUploadSelectedFilesListener(ActionListener listener) {
        uploadTab.getNewExpPanel().addUploadSelectedFilesListener(listener);
    }

    public ArrayList<File> getFilesToUpload() {
        return uploadTab.getNewExpPanel().getUploadFiles();
    }

    public ArrayList<File> getSelectedFilesToUpload() {
        return uploadTab.getNewExpPanel().getSelectedFilesToUpload();
    }

    public AnnotationDataValue[] getUploadAnnotations() {
        return uploadTab.getNewExpPanel().getUploadAnnotations();
    }

    public String getNewExpName() {
        return uploadTab.getNewExpPanel().getNewExpID();
    }

    public HashMap<String, String> getFilesToUploadTypes() {
        return uploadTab.getNewExpPanel().getTypes();
    }

    public void enableUploadButton(boolean b) {
        uploadTab.getNewExpPanel().enableUploadButton(b);
    }

    public void deleteUploadFileRow(File f) {
        uploadTab.getNewExpPanel().deleteFileRow(f);
    }

    public void selectFilesToNewExp(File[] files) {
        uploadTab.getNewExpPanel().createUploadFileRow(files);
    }

    public void selectFilesToExistingExp(File[] files) {
        uploadTab.getExistExpPanel().createUploadFileRow(files);
    }

    /**
     * @return The GUI's downloadWindow.
     */
    public DownloadWindow getDownloadWindow() {
        return downloadWindow;
    }

    public String[] getParameters() {
        return processTab.getRegularParameters();
    }

    public void setBowtieParameters() {
        processTab.setRegularParameters();
    }

    public JList getfileList() {
        return processTab.getFileList();
    }

    public String[] getRatioCalcParameters() {
        return ratioCalcPopup.getRatioCalcParameters();
    }

    public void addRatioCalcListener(ActionListener listener) {
        processTab.addRatioCalcListener(listener);
    }

    // TODO: They removed Cancel button from RatioCalcPopup, but left half of it
    // (OO)
    // public void addCancelListener(ActionListener listener) {
    // ratioCalcPopup.addCancelListener(listener);
    // }

    public void addOkListener(ActionListener listener) {
        ratioCalcPopup.addOkListener(listener);
    }

    public void setDefaultRatioPar() {
        ratioCalcPopup.setDefaultRatioPar();
    }

    public void setUnusedRatioPar() {
        ratioCalcPopup.setUnusedRatioPar();
    }

    /**
     * displays the ratio popup
     *
     * @see gui.GenomizerView#showRatioPopup()
     */
    public void showRatioPopup() {
        ratioCalcPopup.setVisible(true);
    }

    public void showProcessFeedback(ProcessFeedbackData[] processFeedbackData) {
        processTab.showProcessFeedback(processFeedbackData);
    }


    /**
     * returns the RatioCalcPopup
     *
     * @see gui.GenomizerView#getRatioCalcPopup()
     */
    public RatioCalcPopup getRatioCalcPopup() {
        return this.ratioCalcPopup;
    }

    public void setGenomeFileList(GenomeReleaseData[] genomeReleases) {
        processTab.setGenomeFileList(genomeReleases);
    }

    public void removeUploadExpName() {
        // TODO: Doesn't do anything (OO)
        // uploadTab.removeExpName();
    }

    public void removeSelectedFromWorkspace() {
        workspaceTab.removeSelectedData();
    }

    /**
     * TODO understand
     *
     * @see gui.GenomizerView#isCorrectToProcess()
     */
    public boolean isCorrectToProcess() {
        boolean sgrFormat = processTab.radioGroup
                .isSelected(processTab.outputSGR.getModel());
        return Process.isCorrectToProcess(processTab.smoothWindowSize,
                processTab.stepPosition, processTab.stepSize, sgrFormat,
                processTab.useSmoothing, processTab.stepSizeBox);
    }

    /**
     * TODO understand
     *
     * @see gui.GenomizerView#isRatioCorrectToProcess()
     */
    public boolean isRatioCorrectToProcess() {
        return !processTab.useRatio()
                || Process.isRatioCorrectToProcess(
                        ratioCalcPopup.ratioWindowSize,
                        ratioCalcPopup.inputReads, ratioCalcPopup.chromosome,
                        ratioCalcPopup.ratioStepPosition);
    }

    public void setProfileButton(boolean bool) {

        // TODO: Doesn't do anything (OO)
        // processTab.setProfileButton(bool);
    }

    public boolean useRatio() {
        return processTab.useRatio();
    }

    public ActiveSearchPanel getActiveSearchPanel() {
        return querySearchTab.getActivePanel();
    }

    public JButton getBackButton() {
        return querySearchTab.getBackButton();
    }

    public String getSelectedSpecies() {
        return uploadTab.getNewExpPanel().getSelectedSpecies();
    }

    public void addSpeciesSelectedListener(ActionListener listener) {
        uploadTab.getNewExpPanel().addSpeciesSelectedListener(listener);
    }

    public void setGenomeReleases(GenomeReleaseData[] grd) {
        uploadTab.setGenomeReleases(grd);
    }

    public String getGenomeVersion(File f) {
        return uploadTab.getGenomeVersion(f);
    }

    /**
     * Remove and re-add each tab in the GUI. For now **ONLY TABS** are reset:
     * If this changes some other methods will need updating (logoutlistener)
     */
    public void resetGUI() {

        // Remove tabs
        while (tabbedPane.getTabCount() > 0) {
            tabbedPane.removeTabAt(0);
        }

        // Recreate tabs
        UploadTab ut = new UploadTab();
        ProcessTab pt = new ProcessTab();
        WorkspaceTab wt = new WorkspaceTab();
        SysadminTab sat = new SysadminTab();
        QuerySearchTab qst = new QuerySearchTab();
        // TODO: Maybe analyse too (OO)

        // Set tabs
        setQuerySearchTab(qst);
        setUploadTab(ut);
        setProcessTab(pt);
        setWorkspaceTab(wt);
        setSysAdminTab(sat);
        // Maybe analyse too (OO)

        repaint();
        revalidate();
    }

    /**
     * returns the tabbedPane
     *
     * @see gui.GenomizerView#getTabbedPane()
     */
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void addDeleteSelectedListener(ActionListener listener) {
        processTab.addDeleteSelectedListener(listener);
    }

    public ArrayList<ExperimentData> getFileInfo() {
        return processTab.getFileInfo();
    }

    public void setFileInfo(ArrayList<ExperimentData> fileInfo) {
        processTab.setFileInfo(fileInfo);
    }

    public void setOngoingDownloads(
            CopyOnWriteArrayList<DownloadHandler> ongoingDownloads) {
        workspaceTab.setOngoingDownloads(ongoingDownloads);

    }

    public void changeTabInWorkspace(int tabIndex) {
        workspaceTab.changeTab(tabIndex);
    }

    public void clearSearchSelection() {
        querySearchTab.clearSearchSelection();
    }

    /**
     * Returns the processTab
     *
     * @return the processTab
     */
    public ProcessTab getProcessTab() {
        // TODO Auto-generated method stub
        return processTab;
    }

    public boolean getIsNewExp() {
        return uploadTab.getUploadToNewExpPanel().getIsNewExp();
    }

    public void setConvertTab(ConvertTab ct) {
        this.convertTab = ct;
        tabbedPane.addTab("CONVERT", null, convertTab, "Convert");
    }

    public ConvertTab getConvertTab() {
        return convertTab;
    }

    public void setConvertFileList(ArrayList<FileData> arrayList) {
        ArrayList<FileData> fileArray = arrayList;

        tabbedPane.setSelectedIndex(5);
        convertTab.setFileInfo(workspaceTab.getSelectedData());

    }

}
