package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.Controller;

import model.ErrorLogger;

import util.Constants;
import util.IconFactory;

/**
 * Class for loginwindow. Presents a window that prompts for a username,
 * password and server with port. User authorization
 */
public class LoginWindow extends JFrame {
    
    private static final long serialVersionUID = -150623476066679412L;
    private JButton loginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField ipField;
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel buttonPanel;
    private JPanel logoPanel;
    private JLabel errorLabel;
    
    /**
     * Constructor creating the login window and adding listeners.
     */
    public LoginWindow(final GenomizerView parent) {
        
        URL url = ClassLoader.getSystemResource("icons/logo.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        setIconImage(img);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                parent.getFrame().dispose();
            }
            
            @Override
            public void windowActivated(WindowEvent e) {
                usernameField.requestFocusInWindow();
            }
        });
        
        setTitle("Genomizer Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(270, 280);
        setResizable(false);
        this.setLocationRelativeTo(parent.getFrame());
        
        placeComponents();
    }
    
    /**
     * Sets the layout and looks to the login window
     */
    private void placeComponents() {
        mainPanel = new JPanel(new BorderLayout());
        bottomPanel = new JPanel();
        logoPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new FlowLayout());
        add(mainPanel);
        mainPanel.add(bottomPanel, BorderLayout.CENTER);
        mainPanel.add(logoPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        ImageIcon icon = IconFactory.getLogoIcon(70, 70);
        JLabel picLabel = new JLabel(icon);
        logoPanel.add(picLabel, BorderLayout.CENTER);
        logoPanel.add(Box.createVerticalStrut(10), BorderLayout.NORTH);
        bottomPanel.setLayout(null);
        
        JLabel usernameLabel = new JLabel("Name");
        usernameLabel.setBounds(10, 40, 80, 25);
        bottomPanel.add(usernameLabel);
        
        usernameField = new JTextField(20);
        usernameField.setBounds(90, 40, 170, 25);
        usernameField.setText(Constants.userName);
        bottomPanel.add(usernameField);
        
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 70, 80, 25);
        bottomPanel.add(passwordLabel);
        
        passwordField = new JPasswordField(20);
        passwordField.setBounds(90, 70, 170, 25);
        passwordField.setText(Constants.password);
        bottomPanel.add(passwordField);
        
        JLabel ipLabel = new JLabel("IP");
        ipLabel.setBounds(10, 100, 80, 25);
        bottomPanel.add(ipLabel);
        
        // TODO: Hard coded IP stuff should at least be updated. OO
        ipField = new JTextField(20);
        ipField.setBounds(90, 100, 170, 25);
        ipField.setText(Constants.serverAddress);
        bottomPanel.add(ipField);
        
        loginButton = new JButton("Login");
        buttonPanel.add(loginButton);
        
        // Add listeners to the JTextFields for if enter/return is pressed.
        usernameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loginButton.doClick();
            }
        });
        
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loginButton.doClick();
            }
        });
        
        ipField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loginButton.doClick();
            }
        });
    }
    
    /**
     * Adds listener to the loginbutton
     * 
     * @see controller.Controller#LoginListener()
     * @param listener
     *            The listener to login to the server
     */
    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }
    
    /**
     * Method for getting the username
     * 
     * @return the username entered by the user OR The username input from the
     *         login window.
     */
    public String getUsernameInput() {
        return usernameField.getText();
    }
    
    /**
     * Method for getting the password
     * 
     * @return the password entered by the user OR The password input from the
     *         login window.
     */
    public String getPasswordInput() {
        return new String(passwordField.getPassword());
    }
    
    /**
     * Method for getting IP-address and port
     * 
     * @return the IP-adress and port from the user OR The IP input from the
     *         login window.
     */
    public String getIPInput() {
        return ipField.getText();
    }
    
    /**
     * Displays an error message
     * 
     * @param errorMessage
     *            the error message
     */
    public void updateLoginFailed(String errorMessage) {
        paintErrorMessage(errorMessage);
    }
    
    public void paintErrorMessage(String message) {
        removeErrorMessage();
        message = message.replace(".", "");
        errorLabel = new JLabel("<html><b>" + message + "</b></html>");
        errorLabel.setBounds(73, 0, 150, 45);
        bottomPanel.add(errorLabel);
        ErrorLogger.log(message);
        repaint();
        revalidate();
    }
    
    public void removeErrorMessage() {
        if (errorLabel != null) {
            bottomPanel.remove(errorLabel);
            errorLabel = null;
            repaint();
            revalidate();
        }
    }
    
}
