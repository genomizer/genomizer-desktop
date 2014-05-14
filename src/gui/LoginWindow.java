package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginWindow extends JDialog {
    
    private JButton        loginButton;
    private JTextField     usernameField;
    private JPasswordField passwordField;
    private JTextField     ipField;
    private JPanel         mainPanel;
    private JLabel         errorLabel;
    
    public LoginWindow(final GenomizerView parent) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                parent.getFrame().dispose();
            }
        });
        setTitle("Genomizer Login");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 170);
        setResizable(false);
        this.setLocationRelativeTo(parent.getFrame());
        placeComponents();
    }
    
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
        usernameField.setText("Genome researcher 1");
        mainPanel.add(usernameField);
        
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 40, 80, 25);
        mainPanel.add(passwordLabel);
        
        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 40, 160, 25);
        passwordField.setText("superhemligt");
        mainPanel.add(passwordField);
        
        JLabel ipLabel = new JLabel("IP");
        ipLabel.setBounds(10, 70, 80, 25);
        mainPanel.add(ipLabel);
        
        ipField = new JTextField(20);
        ipField.setBounds(100, 70, 160, 25);
        ipField.setText("scratchy.cs.umu.se:7000");
        // ipField.setText("hankatt.cs.umu.se:7000");
        mainPanel.add(ipField);
        
        loginButton = new JButton("login");
        loginButton.setBounds(10, 100, 80, 25);
        mainPanel.add(loginButton);
        
    }
    
    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }
    
    public String getUsernameInput() {
        return usernameField.getText();
    }
    
    public String getPasswordInput() {
        return passwordField.getText();
    }
    
    public String getIPInput() {
        return ipField.getText();
    }
    
    public void updateLoginFailed(String errorMessage) {
        paintErrorMessage(errorMessage);
    }
    
    public void paintErrorMessage(String message) {
        errorLabel = new JLabel(message);
        errorLabel.setIcon(UIManager.getIcon("OptionPane.warningIcon"));
        errorLabel.setBounds(120, 100, 160, 25);
        mainPanel.add(errorLabel);
        repaint();
    }
    
    public void removeErrorMessage() {
        if (errorLabel != null) {
            mainPanel.remove(errorLabel);
            errorLabel = null;
            repaint();
        }
    }
    
}
