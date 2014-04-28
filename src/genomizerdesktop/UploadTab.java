package genomizerdesktop;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class UploadTab extends JPanel {

	private static final long serialVersionUID = -2830290705724588252L;
	private JButton uploadButton;
	private JTextArea directoryTextField;
	private JPanel northPanel;

	public UploadTab() {
		this.setLayout(new BorderLayout());
		
		directoryTextField = new JTextArea("File directory");
		directoryTextField.setColumns(1);
		northPanel = new JPanel();
		this.add(northPanel, BorderLayout.NORTH);
		northPanel.add(directoryTextField, BorderLayout.NORTH);
		uploadButton = new JButton("UPLOAD");
		northPanel.add(uploadButton, BorderLayout.CENTER);
	}

    public void addUploadBtnListener(ActionListener listener) {
        uploadButton.addActionListener(listener);
    }
}
