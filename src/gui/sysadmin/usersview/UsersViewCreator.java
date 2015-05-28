package gui.sysadmin.usersview;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class UsersViewCreator {


    private JTextPane newUserName;
    private JTextPane newPassword;
    private JTextPane newRole;
    private JTextPane newRealName;
    private JTextPane newMail;
    private JTextPane deleteUserName;
    private JTextPane updateUserName;
    private JTextPane updatePassword;
    private JTextPane updateRole;
    private JTextPane updateRealName;
    private JTextPane updateMail;
    private JButton createUserButton;
    private JButton updateUserButton;
    private JButton deleteUserButton;

    public UsersViewCreator() {

    }

    public JPanel buildUsersView() {

        JPanel mainPanel = new JPanel();
        JPanel megaMainPanel = new JPanel();
    
        

        
        BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(layout);
        // mainPanel.setBackground(new Color(255, 250, 250));
        // mainPanel.add(buildUsersList(), BorderLayout.WEST);
        mainPanel.add(buildCreateUserBar());
        mainPanel.add(buildUpdateUserBar());
        mainPanel.add(buildDeleteUserBar());
        
        
        megaMainPanel.add(mainPanel, BoxLayout.X_AXIS);
        
        
        JPanel extraPanel = new JPanel();
        extraPanel.setPreferredSize(new Dimension(700,500));
        
        
        megaMainPanel.add(extraPanel);
        return megaMainPanel;
    }

    private JPanel buildCreateUserBar() {

        // TODO: Search panel not implemented (OO)
        JPanel creatorPanel = new JPanel();
        creatorPanel.setBorder(BorderFactory.createTitledBorder("Create"));
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

        creatorPanel.add(namePanel);
        creatorPanel.add(textPanel);
        creatorPanel.add(buttonPanel);

        return creatorPanel;
    }
    
    
    private JPanel buildUpdateUserBar() {

        // TODO: Search panel not implemented (OO)
        JPanel updaterPanel = new JPanel();
        updaterPanel.setBorder(BorderFactory.createTitledBorder("Update"));
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
        updateUserName = new JTextPane();
        updateUserName.setPreferredSize(new Dimension(120, 20));
        JLabel newPasswordLabel = new JLabel("Password   ");
        updatePassword = new JTextPane();
        updatePassword.setPreferredSize(new Dimension(120, 20));
        JLabel newRoleLabel = new JLabel("Role       ");
        updateRole = new JTextPane();
        updateRole.setPreferredSize(new Dimension(120, 20));
        JLabel newRealNameLabel = new JLabel("Real name   ");
        updateRealName = new JTextPane();
        updateRealName.setPreferredSize(new Dimension(120, 20));
        JLabel newMailLabel = new JLabel("Mail       ");
        updateMail = new JTextPane();
        updateMail.setPreferredSize(new Dimension(120, 20));

        updateUserButton = new JButton("Update User");
        updateUserButton.setPreferredSize(new Dimension(120, 40));

        namePanel.add(newUserNameLabel);
        namePanel.add(new JPanel());
        namePanel.add(newPasswordLabel);
        namePanel.add(new JPanel());
        namePanel.add(newRoleLabel);
        namePanel.add(new JPanel());
        namePanel.add(newRealNameLabel);
        namePanel.add(new JPanel());
        namePanel.add(newMailLabel);

        textPanel.add(updateUserName);
        textPanel.add(new JPanel());
        textPanel.add(updatePassword);
        textPanel.add(new JPanel());
        textPanel.add(updateRole);
        textPanel.add(new JPanel());
        textPanel.add(updateRealName);
        textPanel.add(new JPanel());
        textPanel.add(updateMail);

        buttonPanel.add(updateUserButton);

        updaterPanel.add(namePanel);
        updaterPanel.add(textPanel);
        updaterPanel.add(buttonPanel);

        return updaterPanel;
    }
    
    

    private JPanel buildDeleteUserBar() {

        // TODO: Search panel not implemented (OO)
        JPanel deleterPanel = new JPanel();
        deleterPanel.setBorder(BorderFactory.createTitledBorder("Delete"));
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

        deleterPanel.add(namePanel);
        deleterPanel.add(textPanel);
        deleterPanel.add(buttonPanel);

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
        updateUserButton.addActionListener(actionListener);

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

    public String getUpdateUserName() {
        return updateUserName.getText();
    }

    public String getUpdatePassword() {
        return updatePassword.getText();
    }

    public String getUpdateRole() {
        return updateRole.getText();
    }

    public String getUpdateRealName() {
        return updateRealName.getText();
    }

    public String getUpdateMail() {
        return updateMail.getText();
    }

}
