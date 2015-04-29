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

public class GUI extends JFrame implements GenomizerView {

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
        mainPanel.add(statusPanel,BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(mainPanel.getWidth(),16));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));

        this.setLocationRelativeTo(null);
    }

    public void setStatusPanel(String status) {
        statusPanel.removeAll();
        JLabel statusLabel = new JLabel(status);
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    public void addChangedTabListener(ChangeListener listener) {
        tabbedPane.addChangeListener(listener);
    }

    public int getSelectedIndex() {
        return tabbedPane.getSelectedIndex();
    }

    @Override
    public void addUploadToListener(ActionListener listener) {
        workspaceTab.addUploadToListener(listener);
    }


    @Override
    public void addUploadToListenerSearchTab(ActionListener listener) {
        querySearchTab.addUploadToListener(listener);
    }


    @Override
    public LoginWindow getLoginWindow() {
        return loginWindow;
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
    public void addRawToProfileDataListener(ActionListener listener) {
        processTab.addRawToProfileDataListener(listener);
    }

    /*
     * @Override public void addAddPopupListener(ActionListener
     * addPopupListener) { sysadminTab.addAddPopupListener(addPopupListener); }
     */

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

    @Override
    public void addAddToExistingExpButtonListenerInSearch(ActionListener listener) {
        uploadTab.addAddToExistingExpButtonListener(listener);
    }



    public void addSelectFilesToUploadButtonListener(ActionListener listener) {
        uploadTab.getExistExpPanel().addSelectFilesToUploadButtonListener(
                listener);
    }

    @Override
    public void addUploadToExperimentButtonListener(ActionListener listener) {
        uploadTab.getExistExpPanel().addUploadToExperimentButtonListener(
                listener);
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
     * @return The marked files from the process tab.
     */
    @Override
    public ArrayList<FileData> getAllMarkedFiles() {
        return processTab.getAllMarkedFiles();

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

             UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//             UIManager.put("nimbusBase", new Color(30, 30, 30));
//             UIManager.put("control", new Color(81, 81, 81));
//             UIManager.put("text", new Color(255,255,255));
//             UIManager.put("nimbusSelectionBackground", new Color(255, 255, 255));
//             UIManager.put("nimbusLightBackground", new Color(70,70,70));


        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            ErrorLogger.log(e);
            // TODO H�r har dom l�mnat tomt CF
            // If Nimbus is not available, you can set the GUI to another look
            // and feel.

        }
    }

//    /**
//     * Sets the look and feel of the view.
//     */
//    private void setLookAndFeel() {
//
//        try {
//
//            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//
//             UIManager.put("nimbusBase", new Color(66, 68, 86));
//             UIManager.put("nimbusBlueGrey", new Color(30, 30, 30));
//
//             UIManager.put("FormattedTextField[Enabled].borderPainter", Color.WHITE);
//            // background
//             UIManager.put("control", new Color(76, 78, 86));
//
//             UIManager.put("text", new Color(255,255,255));
//             UIManager.put("nimbusBorder", new Color(30,30,30));
//
//               UIManager.put("nimbusSelectionBackground", new Color(255, 255, 255));
//
//             UIManager.put("Button.background", new Color(55,55,55));
//             UIManager.put("nimbusLightBackground", new Color(56, 58, 66));
//
//
//
//
//        } catch (ClassNotFoundException | InstantiationException
//                | IllegalAccessException | UnsupportedLookAndFeelException e) {
//            // TODO H�r har dom l�mnat tomt CF
//            // If Nimbus is not available, you can set the GUI to another look
//            // and feel.
//
//        }
//    }

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
     * @return workspaceTab
     *          The WorkspaceTab
     */
    public WorkspaceTab getWorkSpaceTab(){
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
     * @return sysadminTab
     *          The SysadminTab
     */
    public SysadminTab getSysAdminTab(){
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
     * @return querySearchTab
     *          the querySearchTab
     */
    public QuerySearchTab getQuerySearchTab(){
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
     *
     * @param allFileData
     */
    @Override
    public void setProcessFileList(ArrayList<FileData> allFileData) {

        ArrayList<FileData> fileArray = allFileData;

        // TODO Vad ska h�nda h�r? CF
        // TESTING
        for (int i = 0; i < fileArray.size(); i++) {

            // System.out.println(fileArray.get(i).filename);

        }
        tabbedPane.setSelectedIndex(2);
        processTab.setFileInfo(getSelectedDataInWorkspace());

    }

    /**
     *
     * @param message
     */
    @Override
    public void printToConsole(String message) {
        processTab.printToConsole(message);
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

    @Override
    public void setSysadminController(SysadminController sysadminController) {
        sysadminTab.setController(sysadminController);

    }


    @Override
    public void createNewExp(AnnotationDataType[] annotations) {
        uploadTab.addNewExpPanel(annotations);
    }

    @Override
    public void addNewExpButtonListener(ActionListener listener) {
        uploadTab.addNewExpButtonListener(listener);
    }

    @Override
    public void addSelectButtonListener(ActionListener listener) {
        uploadTab.getNewExpPanel().addSelectButtonListener(listener);
    }

    @Override
    public void addUploadButtonListener(ActionListener listener) {
        uploadTab.getNewExpPanel().addUploadButtonListener(listener);
    }

    public void addUploadSelectedFilesListener(ActionListener listener) {
        uploadTab.getNewExpPanel().addUploadSelectedFilesListener(listener);
    }

    @Override
    public ArrayList<File> getFilesToUpload() {
        return uploadTab.getNewExpPanel().getUploadFiles();
    }

    public ArrayList<File> getSelectedFilesToUpload() {
        return uploadTab.getNewExpPanel().getSelectedFilesToUpload();
    }

    @Override
    public AnnotationDataValue[] getUploadAnnotations() {
        return uploadTab.getNewExpPanel().getUploadAnnotations();
    }

    @Override
    public String getNewExpName() {
        return uploadTab.getNewExpPanel().getNewExpID();
    }

    @Override
    public HashMap<String, String> getFilesToUploadTypes() {
        return uploadTab.getNewExpPanel().getTypes();
    }

   @Override
    public void enableUploadButton(boolean b) {
        uploadTab.getNewExpPanel().enableUploadButton(b);
    }


    @Override
    public void deleteUploadFileRow(File f) {
        uploadTab.getNewExpPanel().deleteFileRow(f);
    }

    @Override
    public void selectFilesToNewExp(File[] files) {
        uploadTab.getNewExpPanel().createUploadFileRow(files);
    }

    @Override
    public void selectFilesToExistingExp(File[] files) {
        uploadTab.getExistExpPanel().createUploadFileRow(files);
    }

    /**
     * @return The GUI's downloadWindow.
     */
    public DownloadWindow getDownloadWindow() {
        return downloadWindow;
    }

    @Override
    public String[] getParameters() {
        return processTab.getRegularParameters();
    }

    @Override
    public void setBowtieParameters() {
        processTab.setRegularParameters();
    }

    @Override
    public JList getfileList() {
        return processTab.getFileList();
    }

    public String[] getRatioCalcParameters() {
        return ratioCalcPopup.getRatioCalcParameters();
    }

    @Override
    public void addRatioCalcListener(ActionListener listener) {
        processTab.addRatioCalcListener(listener);
    }

    // TODO: They removed Cancle button from RatioCalcPopup, but left half of it (OO)
    // public void addCancelListener(ActionListener listener) {
    // ratioCalcPopup.addCancelListener(listener);
    // }


    public void addOkListener(ActionListener listener) {
        ratioCalcPopup.addOkListener(listener);
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

    public void removeUploadExpName() {
        // TODO: Doesn't do anything (OO)
        // uploadTab.removeExpName();
    }

    public void removeSelectedFromWorkspace() {
        workspaceTab.removeSelectedData();
    }

    public void disableSelectedRow(File f) {
        // TODO: Doesn't do anything (OO)
        // uploadTab.disableRow(f);
    }

    public boolean isCorrectToProcess() {
        boolean sgrFormat = processTab.radioGroup
                .isSelected(processTab.outputSGR.getModel());
        return Process.isCorrectToProcess(processTab.smoothWindowSize,
                processTab.stepPosition, processTab.stepSize, sgrFormat,
                processTab.useSmoothing, processTab.stepSizeBox);
    }

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

    @Override
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


    @Override
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

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void addDeleteSelectedListener(ActionListener listener) {
        processTab.addDeleteSelectedListener(listener);
    }

    public ArrayList<ExperimentData> getFileInfo() {
        return processTab.getFileInfo();
    }

    @Override
    public void setFileInfo(ArrayList<ExperimentData> fileInfo) {
        processTab.setFileInfo(fileInfo);
    }

    @Override
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

    @Override
    public ProcessTab getProcessTab() {
        // TODO Auto-generated method stub
        return processTab;
    }

}
