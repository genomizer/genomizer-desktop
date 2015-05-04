package gui;

import gui.sysadmin.SysadminTab;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeListener;

import model.ErrorLogger;

import util.FileData;
import util.Process;
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
     */
    public void addChangedTabListener(ChangeListener listener) {
        tabbedPane.addChangeListener(listener);
    }
    
    /**
     * returns the index of the currently select item in the tabbed pane
     * 
     */
    public int getSelectedIndex() {
        return tabbedPane.getSelectedIndex();
    }
    
    public LoginWindow getLoginWindow() {
        return loginWindow;
    }
    
    public void addLogoutListener(ActionListener listener) {
        userPanel.addLogoutButtonListener(listener);
    }
    
    public void addSearchListener(ActionListener listener) {
        // TODO Auto-generated method stub
    }
    
    /**
     * @return The uploadTab.
     */
    public UploadTab getUploadTab() {
        return uploadTab;
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
     * Makes GUI invisible, shows the loginWindow
     */
    public void updateLogout() {
        
        this.setVisible(false);
        
        loginWindow.setVisible(true);
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
    
    /**
     * @return The GUI's downloadWindow.
     */
    public DownloadWindow getDownloadWindow() {
        return downloadWindow;
    }
    
    // TODO: They removed Cancel button from RatioCalcPopup, but left half of it
    // (OO)
    // public void addCancelListener(ActionListener listener) {
    // ratioCalcPopup.addCancelListener(listener);
    // }
    
    /**
     * displays the ratio popup
     * 
     */
    public void showRatioPopup() {
        ratioCalcPopup.setVisible(true);
    }
    
    /**
     * returns the RatioCalcPopup
     * 
     */
    public RatioCalcPopup getRatioCalcPopup() {
        return this.ratioCalcPopup;
    }
    
    public void removeUploadExpName() {
        // TODO: Doesn't do anything (OO)
        // uploadTab.removeExpName();
    }
    
    /**
     * TODO understand
     * 
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
    
    public JButton getBackButton() {
        return querySearchTab.getBackButton();
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
     */
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
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
