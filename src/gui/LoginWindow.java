package gui;

import genomizerdesktop.GenomizerView;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class LoginWindow extends JDialog {

	private JButton loginButton;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPanel mainPanel;
	private JLabel errorLabel;
	
	
	public LoginWindow(final GenomizerView parent) {
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent e) {
		      parent.getFrame().dispose();
		    }
		});
		setTitle("Genomizer Login");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300, 150);
		setResizable(false);
		this.setLocationRelativeTo(parent.getFrame());
		placeComponents();
	}

	private void placeComponents() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		add(mainPanel);
		mainPanel.setLayout(null);

		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(10, 10, 80, 25);
		mainPanel.add(usernameLabel);

		usernameField = new JTextField(20);
		usernameField.setBounds(100, 10, 160, 25);
		usernameField.setText("DNAh4xx0R_98");
		mainPanel.add(usernameField);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 40, 80, 25);
		mainPanel.add(passwordLabel);

		passwordField = new JPasswordField(20);
		passwordField.setBounds(100, 40, 160, 25);
		passwordField.setText("superhemligt");
		mainPanel.add(passwordField);

		loginButton = new JButton("login");
		loginButton.setBounds(10, 80, 80, 25);
		mainPanel.add(loginButton);

	}

	public void addLoginListener(ActionListener listener) {
		loginButton.addActionListener(listener);
	}

	public String getUsernameInput() {
		return usernameField.getText();
	}

	public String getPasswordInput() {
		return passwordField.getText();
	}

	public void updateLoginFailed(String errorMessage) {
		paintErrorMessage(errorMessage);
	}
	
	public void paintErrorMessage(String message) {
		errorLabel = new JLabel(message);
		errorLabel.setIcon(UIManager.getIcon("OptionPane.warningIcon"));
		errorLabel.setBounds(120, 80, 160, 25);
		mainPanel.add(errorLabel);
		repaint();
	}
	
	public void removeErrorMessage() {
		if(errorLabel != null) {
			mainPanel.remove(errorLabel);
			errorLabel = null;
			repaint();
		}
	}

}