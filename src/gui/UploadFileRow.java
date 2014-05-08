package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class UploadFileRow extends JPanel {
    private JPanel filePanel;
    private JLabel fileLabel;
    private JButton closeButton;
    private JComboBox typeBox;
    private JProgressBar uploadBar;

    public UploadFileRow(String fileName) {
	setLayout(new BorderLayout());

	filePanel = new JPanel();
	add(filePanel, BorderLayout.SOUTH);
	GridBagLayout gbl_panel = new GridBagLayout();
	gbl_panel.columnWidths = new int[] { 0, 0, 0, 0 };
	gbl_panel.rowHeights = new int[] { 0, 0 };
	gbl_panel.columnWeights = new double[] { 1.0, 0.0, 0.0,
		Double.MIN_VALUE };
	gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
	filePanel.setLayout(gbl_panel);
	
	fileLabel = new JLabel(fileName);
    	GridBagConstraints gbc_lblFilename = new GridBagConstraints();
    	gbc_lblFilename.anchor = GridBagConstraints.WEST;
    	gbc_lblFilename.insets = new Insets(0, 0, 5, 5);
    	gbc_lblFilename.gridx = 0;
    	gbc_lblFilename.gridy = 0;
    	filePanel.add(fileLabel, gbc_lblFilename);

	uploadBar = new JProgressBar();
	GridBagConstraints gbc_progressBar = new GridBagConstraints();
	gbc_progressBar.insets = new Insets(0, 0, 0, 5);
	gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
	gbc_progressBar.gridx = 0;
	gbc_progressBar.gridy = 1;
	filePanel.add(uploadBar, gbc_progressBar);

	String[] fileTypes = { "Profile", "Raw", "Region" };
	typeBox = new JComboBox(fileTypes);
	typeBox.setPreferredSize(new Dimension(120, 31));
	GridBagConstraints gbc_comboBox = new GridBagConstraints();
	gbc_comboBox.insets = new Insets(0, 0, 0, 5);
	gbc_comboBox.anchor = GridBagConstraints.WEST;
	gbc_comboBox.gridx = 1;
	gbc_comboBox.gridy = 1;
	filePanel.add(typeBox, gbc_comboBox);

	closeButton = new JButton("X");
	GridBagConstraints gbc_btnX = new GridBagConstraints();
	gbc_btnX.gridx = 2;
	gbc_btnX.gridy = 1;
	filePanel.add(closeButton, gbc_btnX);
    }
}