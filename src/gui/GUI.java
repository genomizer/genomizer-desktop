package gui;

import gui.processing.ProcessTab;
import gui.sysadmin.SysadminTab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import util.ExperimentData;
import util.FileData;
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
    private RatioCalcPopup ratioCalcPopup;
    // private AnalyzeTab at;

    private JPanel statusPanel;
    private ConvertTab convertTab;
    private String status;
    private Color color;
    public int nrOfThreads = 0;
    public int statusSuccessOrFail = 0;

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
        setStatusPanel("Login successful");

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

//    public void setStatusPanelColorSuccess(){
//        Color color = new Color(153,255,153);
//        statusPanel.setBackground(color);
//    }
//
//    public void setStatusPanelColorFail(){
//        Color color = new Color(243,126,126);
//        statusPanel.setBackground(color);
//    }



    public class setStatusPanelColors implements Runnable{

        private int firstTime = 0;


      public setStatusPanelColors(String status){
            setCurrentStatus(status);
        }


      @Override
      public void run() {

          nrOfThreads ++;
          if(getCurrentStatus().equals("success")){
              setColor(155,255,155);

              if(nrOfThreads == 1){
                  firstTime  = 1;
              }

              statusSuccessOrFail = 1;
              for(int i = 0; i < 100;i++){
                  if(statusSuccessOrFail == 2 || (firstTime == 1 && nrOfThreads > 1)){
                      break;
                  }
                  if(nrOfThreads > 1){
                      firstTime  = 1;
                  }
                  try {
                      Thread.sleep(40);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  setColor(155,255-i,155);
                  statusPanel.setBackground(getColor());

              }


          } else if(getCurrentStatus().equals("fail")){
              setColor(255,155,155);
              if(nrOfThreads == 1){
                  firstTime = 1;
              }

              statusSuccessOrFail = 2;
              for(int i = 0; i < 100;i++){
                  if(statusSuccessOrFail  == 1 || (firstTime == 1 && nrOfThreads > 1)){
                      break;
                  }
                  if(nrOfThreads > 1){
                      firstTime  = 1;
                  }
                  try {
                      Thread.sleep(40);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  setColor(255-i,155,155);
                  statusPanel.setBackground(getColor());
              }
          }
          firstTime = 0;
          nrOfThreads--;

      }


      }

    public void setStatusPanelColor(String status){
        (new Thread(new setStatusPanelColors(status))).start();
    }


      private void setColor(int r, int g, int b){
          color = new Color(r,g,b);
      }

      private Color getColor(){
          return color;
      }

      private void setCurrentStatus(String statusString){
          status = statusString;
      }

      private String getCurrentStatus(){
          return status;
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

    public Class<? extends Component> getSelectedTabClass() {
        return tabbedPane.getSelectedComponent().getClass();
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
    public void setProcessFileList(ExperimentData experimentData) {

//        ArrayList<FileData> fileArray = allFileData;

        tabbedPane.setSelectedIndex(2);
        processTab.setSelectedExperiment(experimentData);
//        processTab.setFileInfo(workspaceTab.getSelectedData());

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
        return false;
//        boolean sgrFormat = processTab.radioGroup
//                .isSelected(processTab.outputSGR.getModel());
//        return Process.isCorrectToProcess(processTab.smoothWindowSize,
//                processTab.stepPosition, processTab.stepSize, sgrFormat,
//                processTab.useSmoothing, processTab.stepSizeBox);
    }

    /**
     * TODO understand
     *
     */
    public boolean isRatioCorrectToProcess() {
        return false;
//        return !processTab.useRatio()
//                || Process.isRatioCorrectToProcess(
//                        ratioCalcPopup.ratioWindowSize,
//                        ratioCalcPopup.inputReads, ratioCalcPopup.chromosome,
//                        ratioCalcPopup.ratioStepPosition);
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
        ConvertTab ct = new ConvertTab();
        // TODO: Maybe analyse too (OO)

        // Set tabs
        setQuerySearchTab(qst);
        setUploadTab(ut);
        setProcessTab(pt);
        setWorkspaceTab(wt);
        setSysAdminTab(sat);
        setConvertTab(ct);
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

        tabbedPane.setSelectedComponent(convertTab);
        convertTab.setFileInfo(workspaceTab.getSelectedData());

    }

}
