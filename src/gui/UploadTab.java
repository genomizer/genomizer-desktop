package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class UploadTab extends JPanel {

	private static final long serialVersionUID = -2830290705724588252L;
	private JButton uploadButton, browseButton;
	private JTextArea directoryTextField;
	private JPanel northPanel;

	public UploadTab() {
		this.setLayout(new BorderLayout());

		directoryTextField = new JTextArea();
		directoryTextField.setColumns(30);
		northPanel = new JPanel();
		this.add(northPanel, BorderLayout.NORTH);
		northPanel.add(new JLabel("File directory:"));
		northPanel.add(directoryTextField, BorderLayout.NORTH);
		browseButton = new JButton("BROWSE");
		uploadButton = new JButton("UPLOAD");
		northPanel.add(browseButton, BorderLayout.CENTER);
		northPanel.add(uploadButton, BorderLayout.CENTER);
	}

	public void addBrowseBtnListener(ActionListener listener) {

	}

    public void addUploadBtnListener(ActionListener listener) {
        uploadButton.addActionListener(listener);
    }
}
