package genomizerdesktop;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUI implements GenomizerView {

	private JFrame frame;
	private JPanel mainPanel, searchPanel, workspacePanel;
	private JPanel analyzePanel, processPanel, uploadPanel;
	private JTabbedPane tabbedPane;

    public GUI() {
	frame = new JFrame("Genomizer");
	frame.setSize(800, 800);

	BorderLayout bl = new BorderLayout();
	mainPanel = new JPanel(bl);
	frame.add(mainPanel);

	tabbedPane = new JTabbedPane();
	mainPanel.add(tabbedPane);

	searchPanel = new JPanel();
	workspacePanel = new JPanel();
	analyzePanel = new JPanel();
	processPanel = new JPanel();
	uploadPanel = new JPanel();

	addPanelsToTabbedPane();

	try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException  e) {
		e.printStackTrace();
	} catch (InstantiationException e) {
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		e.printStackTrace();
	} catch (UnsupportedLookAndFeelException e) {
		e.printStackTrace();
	}

	try {
		ImageIcon icon = new ImageIcon("/resources/SearchTabImage");
//		tabbedPane.setI
	} catch(IllegalArgumentException e2) {
		System.err.println("Image Loading failed.");
	}
	frame.setSize(800, 800);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
    }

    public void addPanelsToTabbedPane() {
    tabbedPane.add("SEARCH", searchPanel);
    tabbedPane.add("WORKSPACE", workspacePanel);
    tabbedPane.add("ANALYZE", analyzePanel);
    tabbedPane.add("PROCESS", processPanel);
    tabbedPane.add("UPLOAD", uploadPanel);
    }

    public void setSearchPanel(JPanel searchPanel) {
    	this.searchPanel = searchPanel;
    }

    public JPanel getSearchPanel() {
    	return searchPanel;
    }

    public JFrame getFrame() {
	return frame;
    }
}
