package genomizerdesktop;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UserPanel extends JPanel {

	private static final long serialVersionUID = -5659594424270202956L;
	private String firstName, lastName, username, password;
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
		passwordField = new JPasswordField(20);
		loginButton = new JButton("Log in");

		add(usernameLabel);
		add(usernameField);
		add(passwordLabel);
		add(passwordField);
		add(loginButton);
	}

	public String getUsername() {
		try {
			return username;
		} catch(NullPointerException e) {
			System.err.println("No username.");
			return "";
		}
	}

	public String getPassword() {
		try {
			return password;
		} catch(NullPointerException e) {
			System.err.println("No password.");
			return "";
		}
	}
}
