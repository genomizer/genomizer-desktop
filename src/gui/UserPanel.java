package gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserPanel extends JPanel {

    private String name, username;
    private boolean admin;
    JLabel usernameLabel, nameLabel;
    JButton logoutButton;

    public UserPanel() {
	setLayout(new GridBagLayout());
	logoutButton = new JButton("Log out");
    }

    public void setUserInfo(String username, String name, boolean admin) {
	this.removeAll();
	this.name = name;
	this.username = username;
	this.admin = admin;
	nameLabel = new JLabel("Name: " + name + " | ");
	usernameLabel = new JLabel("Username: " + username + " ");
	add(nameLabel);
	add(usernameLabel);
	add(logoutButton);
    }

    public void addLogoutButtonListener(ActionListener listener) {
	logoutButton.addActionListener(listener);
    }

}
