package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class SettingsTab extends JPanel{

    /**
     *
     */
    private static final long serialVersionUID = -9196939709569898418L;

    private JPanel mainPanel;

    private JTextPane oldPassword;
    private JTextPane newPassword;
    private JTextPane name;
    private JTextPane email;
    private JButton updateUserButton;

    public SettingsTab() {
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);
        
        JPanel namePanel = new JPanel();
        JPanel textPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        BoxLayout nameLayout = new BoxLayout(namePanel, BoxLayout.PAGE_AXIS);
        BoxLayout textLayout = new BoxLayout(textPanel, BoxLayout.PAGE_AXIS);
        BorderLayout buttonLayout = new BorderLayout();

        buttonPanel.setBorder(BorderFactory.createEtchedBorder());

        namePanel.setLayout(nameLayout);
        textPanel.setLayout(textLayout);
        buttonPanel.setLayout(buttonLayout);

        JLabel oldPasswordLabel = new JLabel("Old Password   ");
        oldPassword = new JTextPane();
        oldPassword.setPreferredSize(new Dimension(120, 20));
        JLabel newPasswordLabel = new JLabel("New Password   ");
        newPassword = new JTextPane();
        newPassword.setPreferredSize(new Dimension(120, 20));
        JLabel nameLabel = new JLabel("Name       ");
        name = new JTextPane();
        name.setPreferredSize(new Dimension(120, 20));
        JLabel emailLabel = new JLabel("Email   ");
        email = new JTextPane();
        email.setPreferredSize(new Dimension(120, 20));


        updateUserButton = new JButton("Create User");
        updateUserButton.setPreferredSize(new Dimension(120, 40));

        namePanel.add(oldPasswordLabel);
        namePanel.add(new JPanel());
        namePanel.add(newPasswordLabel);
        namePanel.add(new JPanel());
        namePanel.add(nameLabel);
        namePanel.add(new JPanel());
        namePanel.add(emailLabel);


        textPanel.add(oldPassword);
        textPanel.add(new JPanel());
        textPanel.add(newPassword);
        textPanel.add(new JPanel());
        textPanel.add(name);
        textPanel.add(new JPanel());
        textPanel.add(email);


        buttonPanel.add(updateUserButton);

        mainPanel.add(namePanel, BorderLayout.WEST);
        mainPanel.add(textPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);
    }
}
