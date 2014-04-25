package genomizerdesktop;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UploadTab extends JPanel {

	private static final long serialVersionUID = -2830290705724588252L;
	private JButton uploadButton;
	private JTextField directoryTextField;
	private GridBagLayout gridBag;

	public UploadTab() {
		this.setLayout(new BorderLayout());
		
		
		directoryTextField = new JTextField();
		this.add(directoryTextField, BorderLayout.NORTH);
		uploadButton = new JButton("UPLOAD");
		this.add(uploadButton, BorderLayout.CENTER);
	}
}
