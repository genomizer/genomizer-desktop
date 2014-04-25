package genomizerdesktop;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI implements GenomizerView {

	private JFrame frame;
	private JPanel mainPanel, tabPanel, centerPanel;

    public GUI() {
	frame = new JFrame("Genomizer");
	frame.setSize(800, 800);

	BorderLayout bl = new BorderLayout();
	mainPanel = new JPanel(bl);
	frame.add(mainPanel);

	tabPanel = new JPanel(new GridBagLayout());
	mainPanel.add(tabPanel, BorderLayout.NORTH);
	tabPanel.add(new JButton("SEARCH"));
	tabPanel.add(new JButton("WORKSPACE"));
	tabPanel.add(new JButton("ANALYZE"));
	tabPanel.add(new JButton("PROCESS"));
	tabPanel.add(new JButton("UPLOAD"));
	frame.setSize(800, 800);


	System.out.println("9213849218392");

	//sätter gui'et i mitten av skärmen
	frame.setLocationRelativeTo(null);

	centerPanel = new JPanel();
	mainPanel.add(centerPanel, BorderLayout.CENTER);

	frame.setVisible(true);
    }

    public JFrame getFrame() {
	return frame;
    }
}
