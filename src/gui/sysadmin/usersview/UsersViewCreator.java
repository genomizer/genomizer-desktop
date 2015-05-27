package gui.sysadmin.usersview;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class UsersViewCreator {

    private JButton createUserButton;
    private JTextPane newUserName;
    private JTextPane newPassword;
    private JTextPane newRole;
    private JTextPane newRealName;
    private JTextPane newMail;
    private JTextPane deleteUserName;
    private JButton deleteUserButton;

    public UsersViewCreator() {

    }

    public JPanel buildUsersView() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        // mainPanel.setBackground(new Color(255, 250, 250));
        // mainPanel.add(buildUsersList(), BorderLayout.WEST);
        mainPanel.add(buildCreateUserBar(), BorderLayout.NORTH);
        mainPanel.add(buildDeleteUserBar(), BorderLayout.SOUTH);
        return mainPanel;
    }

    private JPanel buildCreateUserBar() {

        // TODO: Search panel not implemented (OO)
        JPanel creatorPanel = new JPanel(new BorderLayout());
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

        JLabel newUserNameLabel = new JLabel("Username   ");
        newUserName = new JTextPane();
        newUserName.setPreferredSize(new Dimension(120, 20));
        JLabel newPasswordLabel = new JLabel("Password   ");
        newPassword = new JTextPane();
        newPassword.setPreferredSize(new Dimension(120, 20));
        JLabel newRoleLabel = new JLabel("Role       ");
        newRole = new JTextPane();
        newRole.setPreferredSize(new Dimension(120, 20));
        JLabel newRealNameLabel = new JLabel("Real name   ");
        newRealName = new JTextPane();
        newRealName.setPreferredSize(new Dimension(120, 20));
        JLabel newMailLabel = new JLabel("Mail       ");
        newMail = new JTextPane();
        newMail.setPreferredSize(new Dimension(120, 20));

        createUserButton = new JButton("Create User");
        createUserButton.setPreferredSize(new Dimension(120, 40));

        namePanel.add(newUserNameLabel);
        namePanel.add(new JPanel());
        namePanel.add(newPasswordLabel);
        namePanel.add(new JPanel());
        namePanel.add(newRoleLabel);
        namePanel.add(new JPanel());
        namePanel.add(newRealNameLabel);
        namePanel.add(new JPanel());
        namePanel.add(newMailLabel);

        textPanel.add(newUserName);
        textPanel.add(new JPanel());
        textPanel.add(newPassword);
        textPanel.add(new JPanel());
        textPanel.add(newRole);
        textPanel.add(new JPanel());
        textPanel.add(newRealName);
        textPanel.add(new JPanel());
        textPanel.add(newMail);

        buttonPanel.add(createUserButton);

        creatorPanel.add(namePanel, BorderLayout.WEST);
        creatorPanel.add(textPanel, BorderLayout.CENTER);
        creatorPanel.add(buttonPanel, BorderLayout.EAST);

        return creatorPanel;
    }

    private JPanel buildDeleteUserBar() {

        // TODO: Search panel not implemented (OO)
        JPanel deleterPanel = new JPanel(new BorderLayout());
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

        JLabel deleteUserNameLabel = new JLabel("Username   ");
        deleteUserName = new JTextPane();
        deleteUserName.setPreferredSize(new Dimension(120, 20));

        deleteUserButton = new JButton("Delete User");
        deleteUserButton.setPreferredSize(new Dimension(120, 40));

        namePanel.add(deleteUserNameLabel);

        textPanel.add(deleteUserName);

        buttonPanel.add(deleteUserButton);

        deleterPanel.add(namePanel, BorderLayout.WEST);
        deleterPanel.add(textPanel, BorderLayout.CENTER);
        deleterPanel.add(buttonPanel, BorderLayout.EAST);

        return deleterPanel;
    }

    /***
     * Sets the same listener to all the buttons.
     *
     * @param addAnnotationListener
     *            the listener.
     */
    public void createUserButtonListener(ActionListener actionListener) {
        createUserButton.addActionListener(actionListener);
        deleteUserButton.addActionListener(actionListener);

    }

    public String getUserName() {
        return newUserName.getText();
    }

    public String getPassword() {
        return newPassword.getText();
    }

    public String getRole() {
        return newRole.getText();
    }

    public String getRealName() {
        return newRealName.getText();
    }

    public String getMail() {
        return newMail.getText();
    }

    public String getDeleteUserName() {
        return deleteUserName.getText();
    }

}
