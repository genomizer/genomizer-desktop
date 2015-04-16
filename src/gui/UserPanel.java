package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The tab at the top of the main gui window.
 * Displays the username and a "logout" button.
 *
 * @author (of comment) c12oor
 *
 */
public class UserPanel extends JPanel {

    private static final long serialVersionUID = -5837140311329422316L;

    // TODO: Unused information re. user. Should be extracted to user class. OO
    private String name, username;
    private boolean admin;

    private JLabel usernameLabel;
    private JButton logoutButton;

    /**
     * Create a new {@link UserPanel} with an empty username label and a (actless) button. (OO)
     */
    public UserPanel() {
        setLayout(new BorderLayout());
        setLabels();
        setLogoutButton();
        logoutButton.setFocusable(false);
    }

    private void setLogoutButton() {
        logoutButton = new JButton("Logout");
        add(logoutButton, BorderLayout.EAST);
    }

    private void setLabels() {
        usernameLabel = new JLabel();
        add(usernameLabel, BorderLayout.CENTER);
    }

    /**
     * Set the user info to be displayed at the top of the screen. (OO)
     * @param username String to display
     * @param name (Not currently used)
     * @param admin (Not currently used)
     */
    public void setUserInfo(String username, String name, boolean admin) {
        this.name = name;
        this.username = username;
        this.admin = admin;
        usernameLabel.setText("<html><b> " + username + "</b></html>");
        this.repaint();
        this.revalidate();
    }

    /**
     * What will be done when {@link UserPanel} logout button is pressed. (OO)
     * @param listener
     */
    public void addLogoutButtonListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }
}
