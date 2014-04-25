package genomizerdesktop;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagLayout;

import javax.swing.*;

public class SearchTab {

    // For test purpose
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel tabPanel;


    private JPanel centerPanel;

    public SearchTab(JPanel panel) {
	// For test purpose
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
	frame.setVisible(true);

	centerPanel = panel;



    }

    public static void main(String args[]) {
	new SearchTab(new JPanel());
    }
}
