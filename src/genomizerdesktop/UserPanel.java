package genomizerdesktop;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserPanel extends JPanel {

	private String firstName, lastName, username;
	private boolean admin;
	JLabel usernameLabel, passwordLabel;
	JTextField usernameField, passwordField;
	JButton loginButton;

	public UserPanel(String username, boolean admin) {
		firstName = "Kalle";
		lastName = "Karlsson";
		this.username = username;
		this.admin = admin;
		
		setLayout(new GridBagLayout());
		
		add(new JLabel("User: " + firstName + " " + lastName + "     "));
		add(new JLabel("Username: " + username));
		
		if (admin) {
			add(new JLabel("     Administrator"));
		}
	}
	public void addLoginButtonListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }
	public UserPanel() {
		usernameLabel = new JLabel("Username: ");
		passwordLabel = new JLabel("  Password: ");
		usernameField = new JTextField(20);
		passwordField = new JTextField(20);
		loginButton = new JButton("Log in");
		
		add(usernameLabel);
		add(usernameField);
		add(passwordLabel);
		add(passwordField);
		add(loginButton);
	}
}
