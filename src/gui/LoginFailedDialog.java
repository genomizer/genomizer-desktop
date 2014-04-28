package gui;

import genomizerdesktop.GenomizerView;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class LoginFailedDialog extends JDialog {

    public LoginFailedDialog(final GenomizerView parent, String username,
	    String pwd) {
	setTitle("Login failed");
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setSize(400, 200);
	setResizable(false);
	setLocationRelativeTo(parent.getFrame());
	JLabel label = new JLabel("Login Failed with username \"" + username
		+ "\" and password \"" + pwd + "\"");
	add(label);
	setVisible(true);
    }
}