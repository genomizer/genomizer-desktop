package gui;

import gui.sysadmin.SysadminController;
import gui.sysadmin.SysadminTab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.FileData;
import util.GenomeReleaseData;
import util.ProcessFeedbackData;

import communication.HTTPURLUpload;

public class GUI extends JFrame implements GenomizerView {

    private static final long serialVersionUID = 6659839768426124853L;
    private JPanel mainPanel;
    private JPanel processPanel;
    private JTabbedPane tabbedPane;
    private SearchTab searchTab;
    // private LoginPanel loginPanel;
    private UserPanel userPanel;
    private UploadTab uploadTab;
    private AnalyzeTab analyzeTab;
    private WorkspaceTab workspaceTab;
    private LoginWindow loginWindow;
    private ProcessTab processTab;
    private SysadminTab sysadminTab;
    private QuerySearchTab querySearchTab;
    private DownloadWindow downloadWindow;
    private RatioCalcPopup ratioCalcPopup;

    /**
     * Initiates the main view of the program.
     */
    public GUI() {

        setLookAndFeel();
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

        tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);
        mainPanel.add(tabbedPane);
        // URL url = ClassLoader.getSystemResource("icons/genomizer.png");
        // Toolkit kit = Toolkit.getDefaultToolkit();
        // Image img = kit.createImage(url);
        // setIconImage(img);
        mainPanel.add(userPanel, BorderLayout.NORTH);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void addAnalyzeSelectedListener(ActionListener listener) {
        workspaceTab.addAnalyzeSelectedListener(listener);
    }

    @Override
    public void addLoginListener(ActionListener listener) {
        loginWindow.addLoginListener(listener);
    }

    /*
     * @Override public void addAddAnnotationListener(ActionListener
     * addAnnotationListener) {
     * sysadminTab.addAddAnnotationListener(addAnnotationListener); }
     */
    public void addUpdateSearchAnnotationsListener(ActionListener listener) {
        querySearchTab.addUpdateAnnotationsListener(listener);
    }

    @Override
    public void addProcessFileListener(ActionListener listener) {
        workspaceTab.addProcessFileListener(listener);
    }

    @Override
    public void addConvertFileListener(ActionListener listener) {
        processTab.addConvertFileListener(listener);
    }

    @Override
    public void addRawToProfileDataListener(ActionListener listener) {
        processTab.addRawToProfileDataListener(listener);
    }

    /*
     * @Override public void addAddPopupListener(ActionListener
     * addPopupListener) { sysadminTab.addAddPopupListener(addPopupListener); }
     */

    @Override
    public void addRawToRegionDataListener(ActionListener listener) {
        processTab.addRawToRegionDataListener(listener);
    }

    @Override
    public void addScheduleFileListener(ActionListener listener) {
        processTab.addScheduleFileListener(listener);
    }

    public void addSearchToWorkspaceListener(ActionListener listener) {
        querySearchTab.addAddToWorkspaceButtonListener(listener);
    }

    public void addProcessFeedbackListener(ActionListener listener) {
        processTab.addProcessFeedbackListener(listener);
    }

    @Override
    public void addLogoutListener(ActionListener listener) {

        userPanel.addLogoutButtonListener(listener);
    }

    @Override
    public void addSearchListener(ActionListener listener) {
        // TODO Auto-generated method stub
    }

    @Override
    public void addQuerySearchListener(ActionListener listener) {
        querySearchTab.addSearchButtonListener(listener);
    }

    @Override
    public void addDownloadFileListener(ActionListener listener) {
        workspaceTab.addDownloadFileListener(listener);
    }

    @Override
    public void addAddToExistingExpButtonListener(ActionListener listener) {
        uploadTab.addAddToExistingExpButtonListener(listener);
    }

    public void addSelectFilesToUploadButtonListener(ActionListener listener) {
        uploadTab.getUploadToExistingExpPanel()
                .addSelectFilesToUploadButtonListener(listener);
    }

    @Override
    public void addUploadToExperimentButtonListener(ActionListener listener) {
        uploadTab.getUploadToExistingExpPanel()
                .addUploadToExperimentButtonListener(listener);
    }

    public void addDeleteFromDatabaseListener(ActionListener listener) {
        workspaceTab.addDeleteSelectedListener(listener);
    }

    @Override
    public void addSearchResultsDownloadListener(ActionListener listener) {
        querySearchTab.addDownloadButtonListener(listener);
    }

    /**
     * Adds the provided ExperimentDatas to the workspaceTab.
     *
     * @param experiments
     *            The ArrayList of ExperimentData to be added.
     */
    public void addToWorkspace(ArrayList<ExperimentData> experiments) {
        workspaceTab.addExperimentsToTable(experiments);
    }

    /**
     * @return The data (files or experiments) that were selected in search.
     */
    @Override
    public ArrayList<ExperimentData> getSelectedDataInSearch() {
        return querySearchTab.getSelectedData();
    }

    /*
     * @Override public String getNewAnnotationName() { return
     * sysadminTab.getNewAnnotationName(); }
     */

    /**
     * @return The uploadTab.
     */
    public UploadTab getUploadTab() {
        return uploadTab;
    }

    /*
     * @Override public String[] getNewAnnotionCategories() { return
     * sysadminTab.getNewAnnotationCategories(); }
     *
     * @Override public boolean getNewAnnotationForcedValue() { return
     * sysadminTab.getNewAnnotationForcedValue(); }
     */

    /**
     * @return The querySearchTab's searchString.
     */
    @Override
    public String getQuerySearchString() {
        return querySearchTab.getSearchString();
    }

    /**
     * @return The marked file data from the process tab.
     */
    @Override
    public ArrayList<FileData> getAllMarkedFileData() {
        return processTab.getAllMarkedFileData();
    }

    /**
     * @return The marked files from the process tab.
     */
    @Override
    public ArrayList<FileData> getAllMarkedFiles() {
        return processTab.getAllMarkedFiles();

    }

    /**
     * @return The search tab.
     */
    public JPanel getSearchPanel() {
        return searchTab;
    }

    /**
     * @return The password input from the login window.
     */
    @Override
    public String getPassword() {
        return loginWindow.getPasswordInput();
    }

    /**
     * @return The username input from the login window.
     */
    @Override
    public String getUsername() {
        return loginWindow.getUsernameInput();
    }

    /**
     * @return The IP input from the login window.
     */
    @Override
    public String getIp() {
        return loginWindow.getIPInput();
    }

    /*
     * @Override public AnnotationDataType
     * getSelectedAnnoationAtAnnotationTable() { // TODO Auto-generated method
     * stub return sysadminTab.getSelectedAnnotationAtAnnotationTable(); }
     */
    @Override
    public int getSelectedRowAtAnnotationTable() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @return The JFrame, which is this object.
     */
    @Override
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
    @Override
    public void setDownloadWindow(DownloadWindow downloadWindow) {
        this.downloadWindow = downloadWindow;
    }

    /**
     *
     * @param username
     * @param pwd
     * @param name
     */
    @Override
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
    @Override
    public void updateLoginNeglected(String errorMessage) {
        loginWindow.updateLoginFailed(errorMessage);

    }

    /**
     *
     */
    @Override
    public void updateLogout() {
        this.setVisible(false);
        loginWindow.setVisible(true);
    }

    /**
     *
     * @param searchResults
     */
    @Override
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
            // UIManager.put("nimbusBase", Color.WHITE);
            // UIManager.put("nimbusBlueGrey", Color.WHITE);
            // UIManager.put("control", new Color(223, 235, 242));
            UIManager.put("nimbusOrange", new Color(81, 142, 183));
            UIManager.put("info", Color.white);
            // UIManager.put("nimbusLightBackground", new Color(197,210,220));
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look
            // and feel.
        }
    }

    /**
     * Sets the searchTab of the GUI.
     *
     * @param searchTab
     *            The SearchTab to set the attribute to.
     */
    public void setSearchTab(SearchTab searchTab) {
        this.searchTab = searchTab;
        // tabbedPane.add("SEARCH", searchTab);
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
     * Sets the analyzeTab of the GUI. Also sets the name of the tab in the
     * tabbedPane.
     *
     * @param analyzeTab
     *            The AnalyzeTab to set the attribute to.
     */
    public void setAnalyzeTab(AnalyzeTab analyzeTab) {
        this.analyzeTab = analyzeTab;
        tabbedPane.addTab("ANALYZE", null, analyzeTab, "Analyze");
        // tabbedPane.setEnabledAt(4, false);
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
        tabbedPane.addTab("SYSTEM ADMINISTRATION", null, sysadminTab,
                "System Administration");

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
     * Sets the annotationTypes of the querySearchTab.
     *
     * @param annotationTypes
     *            An array containing AnnotationDataTypes to set the
     *            querySearchTab's annotationTypes to.
     */
    public void setSearchAnnotationTypes(AnnotationDataType[] annotationTypes) {
        querySearchTab.setAnnotationTypes(annotationTypes);
    }

    /*
     * @Override public void setAnnotationTableData(AnnotationDataType[]
     * annotations) { sysadminTab.setAnnotationTableData(annotations); }
     */

    /**
     *
     * @param allFileData
     */
    @Override
    public void setProccessFileList(ArrayList<FileData> allFileData) {

        ArrayList<FileData> fileArray = allFileData;

        // TODO
        // TESTING
        for (int i = 0; i < fileArray.size(); i++) {

            System.out.println(fileArray.get(i).filename);

        }
        tabbedPane.setSelectedIndex(2);
        processTab.setFileInfo(allFileData, getSelectedDataInWorkspace());
        // processTab

    }

    /*
     * @Override public void closePopup() { sysadminTab.closePopup(); }
     *
     * @Override public void annotationPopup() { sysadminTab.popup(); }
     */

    /**
     *
     * @param message
     * @param color
     */
    @Override
    public void printToConvertText(String message, String color) {
        processTab.printToProfileText(message, color);
    }

    /**
     * @return The selected data in the workspace in the form of an arrayList
     *         containing the ExperimentData.
     */
    @Override
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

    /*
     * @Override public void addDeleteAnnotationListener(ActionListener
     * listener) { sysadminTab.addDeleteAnnotationListener(listener); }
     */
    @Override
    public void setSysadminController(SysadminController sysadminController) {
        sysadminTab.setController(sysadminController);

    }

    @Override
    public void addNewExpButtonListener(ActionListener listener) {
        uploadTab.addNewExpButtonListener(listener);
    }

    @Override
    public void addSelectButtonListener(ActionListener listener) {
        uploadTab.addSelectButtonListener(listener);
    }

    @Override
    public void addUploadButtonListener(ActionListener listener) {
        uploadTab.addUploadButtonListener(listener);
    }

    /**
     * Creates a new experiment to upload to using the provided annotations.
     *
     * @param annotations
     *            The annotations of the new experiment.
     */
    @Override
    public void createNewExp(AnnotationDataType[] annotations) {
        uploadTab.createNewExp(annotations);
    }

    @Override
    public void selectFilesToNewExp(File[] files) {
        uploadTab.createUploadFileRow(files);
    }

    @Override
    public void selectFilesToExistingExp(File[] files) {
        uploadTab.getUploadToExistingExpPanel().createUploadFileRow(files);
    }

    /**
     * @return The GUI's downloadWindow.
     */
    public DownloadWindow getDownloadWindow() {
        return downloadWindow;
    }

    @Override
    public String[] getParameters() {
        return processTab.getBowtieParameters();
    }

    @Override
    public ArrayList<File> getFilesToUpload() {
        return uploadTab.getUploadFiles();
    }

    @Override
    public AnnotationDataValue[] getUploadAnnotations() {
        return uploadTab.getUploadAnnotations();
    }

    @Override
    public void setBowtieParameters() {
        processTab.setBowtieParameters();
    }

    @Override
    public JList getfileList() {
        return processTab.getFileList();
    }

    @Override
    public String getNewExpName() {
        return uploadTab.getNewExpID();
    }

    @Override
    public HashMap<String, String> getFilesToUploadTypes() {
        return uploadTab.getTypes();
    }

    /**
     * Calls the uploadPanel's enableUploadButton method to try to either make
     * the upload button enabled or disabled. If all of the required annotation
     * fields are NOT filled, this method won't set it to true.
     *
     * @param b
     *            Whether it should try to make the button enabled (true) or
     *            disabled (false).
     */
    public void enableUploadButton(boolean b) {
        uploadTab.enableUploadButton(b);
    }

    public String[] getRatioCalcParameters() {
        return ratioCalcPopup.getRatioCalcParameters();
    }

    @Override
    public String[] getOtherParameters() {
        return processTab.getOtherParameters();
    }

    /**
     * Deletes a file row.
     *
     * @param f
     *            Used to identify which fileRow to be deleted.
     */
    @Override
    public void deleteUploadFileRow(File f) {
        uploadTab.deleteFileRow(f);
    }

    @Override
    public void addRatioCalcListener(ActionListener listener) {
        processTab.addRatioCalcListener(listener);
    }

    public void addCancelListener(ActionListener listener) {
        ratioCalcPopup.addCancelListener(listener);
    }

    public void addOkListener(ActionListener listener) {
        ratioCalcPopup.addOkListener(listener);
    }

    public void addUploadSelectedFilesListener(
            ActionListener listener) {
        uploadTab.addUploadSelectedFiles(listener);
    }

    @Override
    public void setDefaultRatioPar() {
        ratioCalcPopup.setDefaultRatioPar();
    }

    @Override
    public void setUnusedRatioPar() {
        ratioCalcPopup.setUnusedRatioPar();
    }

    @Override
    public void showRatioPopup() {
        ratioCalcPopup.setVisible(true);
    }

    public void showProcessFeedback(ProcessFeedbackData[] processFeedbackData) {
        processTab.showProcessFeedback(processFeedbackData);
    }

    public void setOngoingUploads(
            CopyOnWriteArrayList<HTTPURLUpload> ongoingUploads) {
        uploadTab.setOngoingUploads(ongoingUploads);
    }

    public RatioCalcPopup getRatioCalcPopup() {
        return this.ratioCalcPopup;
    }

    public void setGenomeFileList(GenomeReleaseData[] genomeReleases) {
        processTab.setGenomeFileList(genomeReleases);
    }

    public ArrayList<File> getSelectedFilesToUpload() {
        return uploadTab.getSelectedFilesToUpload();
    }

    public void removeUploadExpName() {
        uploadTab.removeExpName();
    }

    public void removeSelectedFromWorkspace() {
        workspaceTab.removeSelectedData();
    }

    public void disableSelectedRow(File f) {
        uploadTab.disableRow(f);
    }
}
