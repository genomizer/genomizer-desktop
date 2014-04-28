package genomizerdesktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUI implements GenomizerView {

    private JFrame frame;
    private JPanel mainPanel, workspacePanel;
    private JPanel analyzePanel, processPanel, uploadPanel;
    private JTabbedPane tabbedPane;
    private SearchTab searchTab;
    private UploadTab uploadTab;
    private WorkspaceTab workspaceTab;

    public GUI() {
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
	frame = new JFrame("Genomizer");
	frame.setSize(800, 800);

	BorderLayout bl = new BorderLayout();
	mainPanel = new JPanel(bl);
	frame.add(mainPanel);

	JPanel newPanel = new JPanel();

	tabbedPane = new JTabbedPane();
	mainPanel.add(tabbedPane);
	mainPanel.add(tabbedPane);
	mainPanel.add(new UserPanel("kallekarlsson123",true),BorderLayout.NORTH);

	createPanels();
	addPanelsToTabbedPane();
	setLookAndFeel();

	frame.setSize(1200, 1200);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
    }

    private void createPanels() {
	// searchPanel = new JPanel();
	// workspacePanel = new JPanel();
	analyzePanel = new JPanel();
	processPanel = new JPanel();
	// uploadPanel = new JPanel();
    }

    private void setLookAndFeel() {

    }

    public void addPanelsToTabbedPane() {
	// tabbedPane.add("SEARCH", searchPanel);
	// tabbedPane.add("WORKSPACE", workspacePanel);
	tabbedPane.add("ANALYZE", analyzePanel);
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

    public void addLoginListener(ActionListener listener) {
//	uploadTab.addUploadBtnListener(listener);
    }

    public JPanel getSearchPanel() {
	return searchTab;
    }

    public JFrame getFrame() {
	return frame;
    }

    @Override
    public void addLogoutListener(ActionListener listener) {
	// TODO Auto-generated method stub
    }

    @Override
    public void addSearchListener(ActionListener listener) {
	// TODO Auto-generated method stub
    }

    @Override
    public void addUploadFileListener(ActionListener listener) {
	// TODO Auto-generated method stub
    }

    @Override
    public void addDownloadFileListener(ActionListener listener) {
	// TODO Auto-generated method stub
    }

    @Override
    public String getPassword() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String getUsername() {
	// TODO Auto-generated method stub
	return null;
    }
}
