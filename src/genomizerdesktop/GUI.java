package genomizerdesktop;

import gui.LoginFailedDialog;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUI extends JFrame implements GenomizerView {

    private JPanel mainPanel;
    private JPanel processPanel;
    private JTabbedPane tabbedPane;
    private SearchTab searchTab;
    private LoginPanel loginPanel;
    private UserPanel userPanel;
    private UploadTab uploadTab;
    private AnalyzeTab analyzeTab;
    private WorkspaceTab workspaceTab;

    public GUI() {

	setLookAndFeel();
	this.setTitle("Genomizer");
	setSize(800, 800);

	BorderLayout bl = new BorderLayout();
	mainPanel = new JPanel(bl);
	loginPanel = new LoginPanel();
	userPanel = new UserPanel();
	add(mainPanel);

	JPanel newPanel = new JPanel();

	tabbedPane = new JTabbedPane();
	mainPanel.add(tabbedPane);
	mainPanel.add(tabbedPane);

	// mainPanel.add(new
	// UserPanel("kallekarlsson123",true),BorderLayout.NORTH);
	mainPanel.add(loginPanel, BorderLayout.NORTH);

	createPanels();
	addPanelsToTabbedPane();
	setLookAndFeel();
	// tabbedPane.add("LOGIN", userPanel);
	setSize(1200, 1200);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
    }

    private void setLookAndFeel() {
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (InstantiationException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	} catch (UnsupportedLookAndFeelException e) {
	    e.printStackTrace();
	}
    }

    public void addPanelsToTabbedPane() {
	// tabbedPane.add("SEARCH", searchPanel);
	// tabbedPane.add("WORKSPACE", workspacePanel);
	// tabbedPane.add("ANALYZE", analyzePanel);
	tabbedPane.add("PROCESS", processPanel);
	// tabbedPane.add("UPLOAD", uploadPanel);
    }

    public void setSearchTab(SearchTab searchTab) {
	this.searchTab = searchTab;
	tabbedPane.add("SEARCH", searchTab);
    }

    public void setUploadTab(UploadTab uploadTab) {
	this.uploadTab = uploadTab;
	tabbedPane.add("UPLOAD", uploadTab);
    }

    public void setWorkspaceTab(WorkspaceTab workspaceTab) {
	this.workspaceTab = workspaceTab;
	tabbedPane.add("WORKSPACE", workspaceTab);
    }

    public void setAnalyzeTab(AnalyzeTab analyzeTab) {
	this.analyzeTab = analyzeTab;
	tabbedPane.add("ANALYZE", analyzeTab);
    }

    private void createPanels() {
	// searchPanel = new JPanel();
	// workspacePanel = new JPanel();
	// analyzePanel = new JPanel();
	processPanel = new JPanel();
	// uploadPanel = new JPanel();
    }

    public void addLoginListener(ActionListener listener) {
	loginPanel.addLoginButtonListener(listener);
    }

    public JPanel getSearchPanel() {
	return searchTab;
    }

    public JFrame getFrame() {
	return this;
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
    public void addUploadFileListener(ActionListener listener) {
	uploadTab.addUploadBtnListener(listener);
    }

    @Override
    public void addDownloadFileListener(ActionListener listener) {
	// TODO Auto-generated method stub
    }

    @Override
    public String getPassword() {
	return loginPanel.getPasswordInput();
    }

    @Override
    public String getUsername() {
	return loginPanel.getUsernameInput();
    }

    @Override
    public void updateLoginAccepted(String username, String pwd) {
	System.out.println("login succesful with username " + username
		+ " & pwd " + pwd);
	userPanel.setUserInfo(username, "Kalle Karlsson", false);
	mainPanel.remove(loginPanel);
	mainPanel.add(userPanel, BorderLayout.NORTH);
	refreshGUI();
    }

    @Override
    public void updateLoginNeglected(String username, String pwd) {
	System.out.println("login failed with username " + username + " & pwd "
		+ pwd);
	new LoginFailedDialog(this, username, pwd);
    }

    public void refreshGUI() {
	mainPanel.repaint();
	mainPanel.revalidate();

    }

    @Override
    public void updateLogout() {
	System.out.println("logout succesul");
	mainPanel.remove(userPanel);
	mainPanel.add(loginPanel, BorderLayout.NORTH);
	refreshGUI();
    }
}
