package gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * Class for loginwindow. Presents a window that prompts for a username,
 * password and server with port. User authorization
 * 
 * @author
 * 
 */
public class LoginWindow extends JFrame {
    
    private JButton loginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField ipField;
    private JPanel mainPanel;
    private JLabel errorLabel;
    
    /**
     * Constructor creating the login window and adding listeners.
     */
    public LoginWindow(final GenomizerView parent) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                parent.getFrame().dispose();
            }
        });
        setTitle("Genomizer Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(270, 180);
        setResizable(false);
        this.setLocationRelativeTo(parent.getFrame());
        
        placeComponents();
    }
    
    /**
     * Sets the layout and looks to the login window
     */
    private void placeComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        add(mainPanel);
        mainPanel.setLayout(null);
        
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(10, 10, 80, 25);
        mainPanel.add(usernameLabel);
        
        usernameField = new JTextField(20);
        usernameField.setBounds(100, 10, 160, 25);
        usernameField.setText("desktop");
        mainPanel.add(usernameField);
        
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 40, 80, 25);
        mainPanel.add(passwordLabel);
        
        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 40, 160, 25);
        passwordField.setText("umea@2014");
        mainPanel.add(passwordField);
        
        JLabel ipLabel = new JLabel("IP");
        ipLabel.setBounds(10, 70, 80, 25);
        mainPanel.add(ipLabel);
        
        ipField = new JTextField(20);
        ipField.setBounds(100, 70, 160, 25);
        ipField.setText("scratchy.cs.umu.se:7000");
        mainPanel.add(ipField);
        
        loginButton = new JButton("login");
        loginButton.setBounds(10, 110, 80, 25);
        mainPanel.add(loginButton);
        
    }
    
    /**
     * Adds listener to the loginbutton
     * 
     * @param listener
     *            The listener to login to the server
     */
    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }
    
    /**
     * Method for getting the username
     * 
     * @return the username entered by the user
     */
    public String getUsernameInput() {
        return usernameField.getText();
    }
    
    /**
     * Method for getting the password
     * 
     * @return the password entered buy the user
     */
    public String getPasswordInput() {
        return passwordField.getText();
    }
    
    /**
     * Method for getting IP-address and port
     * 
     * @return the IP-adress and port from the user
     */
    public String getIPInput() {
        return ipField.getText();
    }
    
    public void updateLoginFailed(String errorMessage) {
        paintErrorMessage(errorMessage);
    }
    
    public void paintErrorMessage(String message) {
        errorLabel = new JLabel("<html><b>" + message + "</b></html>");
        errorLabel.setIcon(UIManager.getIcon("OptionPane.warningIcon"));
        errorLabel.setBounds(120, 100, 150, 45);
        mainPanel.add(errorLabel);
        repaint();
        revalidate();
    }
    
    public void removeErrorMessage() {
        if (errorLabel != null) {
            mainPanel.remove(errorLabel);
            errorLabel = null;
            repaint();
            revalidate();
        }
    }
    
}
