package genomizerdesktop;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUI implements GenomizerView {

	private JFrame frame;
	private JPanel mainPanel, centerPanel, searchPanel, workspacePanel;
	private JPanel analyzePanel, processPanel, uploadPanel;
	private JLabel searchLabel, workspaceLabel, analyzeLabel;
	private JLabel processLabel, uploadLabel;
	private JTabbedPane tabbedPane;

    public GUI() {
	frame = new JFrame("Genomizer");
	frame.setSize(800, 800);

	BorderLayout bl = new BorderLayout();
	mainPanel = new JPanel(bl);
	frame.add(mainPanel);

	tabbedPane = new JTabbedPane();
	mainPanel.add(tabbedPane, BorderLayout.NORTH);

	searchLabel = new JLabel("SEARCH");
	workspaceLabel = new JLabel("WORKSPACE");
	analyzeLabel = new JLabel("ANALYZE");
	processLabel = new JLabel("PROCESS");
	uploadLabel = new JLabel("UPLOAD");

	searchPanel = new JPanel();
	workspacePanel = new JPanel();
	analyzePanel = new JPanel();
	processPanel = new JPanel();
	uploadPanel = new JPanel();

	searchPanel.add(searchLabel);
	workspacePanel.add(workspaceLabel);
	analyzePanel.add(analyzeLabel);
	processPanel.add(processLabel);
	uploadPanel.add(uploadLabel);

	tabbedPane.add("SEARCH", searchPanel);
	tabbedPane.add("WORKSPACE", workspacePanel);
	tabbedPane.add("ANALYZE", analyzePanel);
	tabbedPane.add("PROCESS", processPanel);
	tabbedPane.add("UPLOAD", uploadPanel);
	
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
		searchLabel.setIcon(icon);
	} catch(IllegalArgumentException e2) {
		System.err.println("Image Loading failed.");
	}
	frame.setSize(800, 800);

	centerPanel = new JPanel();
	mainPanel.add(centerPanel, BorderLayout.CENTER);

	frame.setVisible(true);
    }

    public JFrame getFrame() {
	return frame;
    }
}
