package genomizerdesktop;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {

    JLabel usernameLabel, passwordLabel;
    JTextField usernameField, passwordField;
    JButton loginButton;

    // public LoginPanel() {
    //
    //
    // setLayout(new GridBagLayout());
    //
    // // add(new JLabel("User: " + firstName + " " + lastName + "     "));
    // // add(new JLabel("Username: " + username));
    //
    // // if (admin) {
    // // add(new JLabel("     Administrator"));
    // //
    // // }
    // }

    public void addLoginButtonListener(ActionListener listener) {
	loginButton.addActionListener(listener);
    }

    public LoginPanel() {
	usernameLabel = new JLabel("Username: ");
	passwordLabel = new JLabel("  Password: ");
	usernameField = new JTextField(20);
	passwordField = new JPasswordField(20);
	/* temporary info */
	usernameField.setText("Kalle");
	passwordField.setText("123");
	/* temporary info */
	loginButton = new JButton("Log in");

	add(usernameLabel);
	add(usernameField);
	add(passwordLabel);
	add(passwordField);
	add(loginButton);
    }

    public String getUsernameInput() {
	return usernameField.getText();
    }

    public String getPasswordInput() {
	return passwordField.getText();
    }

}
