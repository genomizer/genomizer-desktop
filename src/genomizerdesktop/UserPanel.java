package genomizerdesktop;

import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserPanel extends JPanel {

	private String firstName, lastName, username;
	private boolean admin;

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
}
