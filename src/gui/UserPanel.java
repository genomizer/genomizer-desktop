package gui;

import javax.swing.*;

import util.IconFactory;

import java.awt.*;
import java.awt.event.ActionListener;

public class UserPanel extends JPanel {

    private String name, username;
    private boolean admin;
    JLabel usernameLabel;
    JButton logoutButton;

    public UserPanel() {
        setLayout(new BorderLayout());
        setLabels();
        setLogoutButton();

    }

    private void setLogoutButton() {
        logoutButton = CustomButtonFactory.makeCustomButton(
                IconFactory.getLogoutIcon(35, 35),
                IconFactory.getLogoutHoverIcon(37, 37), 37, 37, "Logout user");
        add(logoutButton, BorderLayout.EAST);
    }

    private void setLabels() {
        usernameLabel = new JLabel();
        add(usernameLabel, BorderLayout.CENTER);
    }

    public void setUserInfo(String username, String name, boolean admin) {
        this.name = name;
        this.username = username;
        this.admin = admin;
        usernameLabel.setText("<html><b>" + username + " (" + name + ")</b></html>");
        this.repaint();
        this.revalidate();
    }

    public void addLogoutButtonListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }

}
