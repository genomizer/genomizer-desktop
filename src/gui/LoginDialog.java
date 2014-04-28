package gui;

import genomizerdesktop.GenomizerView;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginDialog extends JDialog {

    private JButton loginButton;
    private JTextField usernameField;
    private JTextField passwordField;
    private JPanel mainPanel;
    private JPanel failPanel;

    public LoginDialog(GenomizerView parent) {

	setTitle("Login");
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setSize(800, 400);
	setResizable(false);
	failPanel = new JPanel();
	mainPanel = new JPanel();
	mainPanel.setLayout(new GridBagLayout());
	JLabel usernameLabel = new JLabel("username");
	JLabel passwordLabel = new JLabel("password");

	usernameField = new JTextField(20);
	passwordField = new JTextField(20);
	/* temporary info */
	usernameField.setText("Kalle");
	passwordField.setText("123");
	/* temporary info */
	loginButton = new JButton("Log in");

	mainPanel.add(usernameLabel);
	mainPanel.add(usernameField);
	mainPanel.add(passwordLabel);
	mainPanel.add(passwordField);
	mainPanel.add(loginButton);
	add(mainPanel);
	// add(failPanel);
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

    public void updateLoginFailed(String username, String password) {
	JLabel failedLabel = new JLabel("FAILED");
	failPanel.add(failedLabel);
	this.repaint();
    }

}