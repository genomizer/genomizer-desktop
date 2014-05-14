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
        setLabels();
        setLogoutButton();
        
    }
    
    private void setLogoutButton() {
        logoutButton = new JButton("Log out");
        logoutButton.setFocusable(false);
        add(logoutButton);
    }
    
    private void setLabels() {
        nameLabel = new JLabel();
        usernameLabel = new JLabel();
        add(nameLabel);
        add(usernameLabel);
    }
    
    public void setUserInfo(String username, String name, boolean admin) {
        this.name = name;
        this.username = username;
        this.admin = admin;
        nameLabel.setText(("Name: " + name + " | "));
        usernameLabel.setText(("Username: " + username + " "));
        this.repaint();
        this.revalidate();
    }
    
    public void addLogoutButtonListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }
    
}
