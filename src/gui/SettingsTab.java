package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import controller.SettingsTabController;

import util.RequestException;

import model.GenomizerModel;

public class SettingsTab extends JPanel {
    
    /**
     *
     */
    private static final long serialVersionUID = -9196939709569898418L;
    
    private JPanel mainPanel;
    
    private JTextField oldPassword;
    private JTextField newPassword;
    private JTextField namefield;
    private JTextField email;
    private JButton updateUserButton;
    private SettingsTabController st;
    
    public SettingsTab() {
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);
        
        JPanel namePanel = new JPanel();
        JPanel textPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel p = new JPanel();
        BoxLayout nameLayout = new BoxLayout(namePanel, BoxLayout.PAGE_AXIS);
        BoxLayout textLayout = new BoxLayout(textPanel, BoxLayout.PAGE_AXIS);
        BorderLayout buttonLayout = new BorderLayout();
        
        namePanel.setLayout(nameLayout);
        textPanel.setLayout(textLayout);
        buttonPanel.setLayout(buttonLayout);
        
        JLabel oldPasswordLabel = new JLabel("Old Password   ");
        oldPassword = new JTextField();
        oldPassword.setPreferredSize(new Dimension(120, 25));
        JLabel newPasswordLabel = new JLabel("New Password   ");
        newPassword = new JTextField();
        newPassword.setPreferredSize(new Dimension(120, 25));
        JLabel nameLabel = new JLabel("Name       ");
        namefield = new JTextField();
        namefield.setPreferredSize(new Dimension(120, 25));
        JLabel emailLabel = new JLabel("Email   ");
        email = new JTextField();
        email.setPreferredSize(new Dimension(120, 25));
        
        updateUserButton = new JButton("Update settings");
        updateUserButton.setPreferredSize(new Dimension(150, 40));
        
        namePanel.add(Box.createRigidArea(new Dimension(5, 50)));
        namePanel.add(oldPasswordLabel);
        namePanel.add(Box.createRigidArea(new Dimension(5, 10)));
        namePanel.add(newPasswordLabel);
        namePanel.add(Box.createRigidArea(new Dimension(5, 10)));
        namePanel.add(nameLabel);
        namePanel.add(Box.createRigidArea(new Dimension(5, 10)));
        namePanel.add(emailLabel);
        
        textPanel.add(oldPassword);
        namePanel.add(Box.createRigidArea(new Dimension(5, 20)));
        textPanel.add(newPassword);
        namePanel.add(Box.createRigidArea(new Dimension(5, 20)));
        textPanel.add(namefield);
        namePanel.add(Box.createRigidArea(new Dimension(5, 20)));
        textPanel.add(email);
        
        buttonPanel.add(updateUserButton);
        
        p.add(namePanel, BorderLayout.WEST);
        p.add(textPanel, BorderLayout.CENTER);
        p.add(buttonPanel, BorderLayout.EAST);
        mainPanel.add(p, BorderLayout.WEST);
    }
    
    public void updateUserButtonListener(ActionListener listener) {
        updateUserButton.addActionListener(listener);
    }
    
    public String getOld() {
        return oldPassword.getText();
    }
    
    public String getNew() {
        return newPassword.getText();
    }
    
    public String getEmail() {
        return email.getText();
    }
    
    public void updateUser(GenomizerModel model) {
        try {
            model.updateUserSettings(getOld(), getNew(), namefield.getText(),
                    getEmail());
        } catch (RequestException e) {
            new ErrorDialog("Update settings failed", e).showDialog();
        }
    }
    
    public void setController(SettingsTabController st) {
        this.st = st;
    }
}
