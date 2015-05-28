package gui.sysadmin.usersview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class UsersViewCreator {


    private JTextField newUserName;
    private JTextField newPassword;
    @SuppressWarnings("rawtypes")
    private JComboBox newRole;
    private JTextField newRealName;
    private JTextField newMail;
    private JTextField deleteUserName;
    private JTextField updateUserName;
    private JTextField updatePassword;
    @SuppressWarnings("rawtypes")
    private JComboBox updateRole;
    private JTextField updateRealName;
    private JTextField updateMail;
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

    @SuppressWarnings("unchecked")
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

        namePanel.setLayout(nameLayout);
        textPanel.setLayout(textLayout);
        buttonPanel.setLayout(buttonLayout);
        buttonPanel.setBorder(null);

        JLabel newUserNameLabel = new JLabel("Username   ");
        newUserName = new JTextField();
        newUserName.setPreferredSize(new Dimension(120, 25));
        JLabel newPasswordLabel = new JLabel("Password   ");
        newPassword = new JTextField();
        newPassword.setPreferredSize(new Dimension(120, 25));
        JLabel newRoleLabel = new JLabel("Role       ");
        newRole = new JComboBox<String>();
        newRole.addItem(new String("USER"));
        newRole.addItem(new String("ADMIN"));
        newRole.addItem(new String("GUEST"));
        newRole.setPreferredSize(new Dimension(120, 25));
        JLabel newRealNameLabel = new JLabel("Real name   ");
        newRealName = new JTextField();
        newRealName.setPreferredSize(new Dimension(120, 25));
        JLabel newMailLabel = new JLabel("Mail       ");
        newMail = new JTextField();
        newMail.setPreferredSize(new Dimension(120, 25));

        createUserButton = new JButton("Create User");
        createUserButton.setPreferredSize(new Dimension(120, 40));

        namePanel.add(newUserNameLabel);
        namePanel.add(Box.createRigidArea(new Dimension(5,20)));
        namePanel.add(newPasswordLabel);
        namePanel.add(Box.createRigidArea(new Dimension(5,20)));
        namePanel.add(newRealNameLabel);
        namePanel.add(Box.createRigidArea(new Dimension(5,20)));
        namePanel.add(newMailLabel);
        namePanel.add(Box.createRigidArea(new Dimension(5,20)));
        namePanel.add(newRoleLabel);

        textPanel.add(newUserName);
        textPanel.add(new JPanel());
        textPanel.add(newPassword);
        textPanel.add(new JPanel());
        textPanel.add(newRealName);
        textPanel.add(new JPanel());
        textPanel.add(newMail);
        textPanel.add(new JPanel());
        textPanel.add(newRole);

        buttonPanel.add(createUserButton);

        creatorPanel.add(namePanel);
        creatorPanel.add(textPanel);
        creatorPanel.add(buttonPanel);

        return creatorPanel;
    }


    @SuppressWarnings("unchecked")
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


        namePanel.setLayout(nameLayout);
        textPanel.setLayout(textLayout);
        buttonPanel.setLayout(buttonLayout);
        buttonPanel.setBorder(null);
        

        JLabel newUserNameLabel = new JLabel("Username   ");
        updateUserName = new JTextField();
        updateUserName.setPreferredSize(new Dimension(120, 25));
        JLabel newPasswordLabel = new JLabel("Password   ");
        updatePassword = new JTextField();
        updatePassword.setPreferredSize(new Dimension(120, 25));
        JLabel newRoleLabel = new JLabel("Role       ");
        updateRole = new JComboBox<String>();
        updateRole.addItem(new String("USER"));
        updateRole.addItem(new String("ADMIN"));
        updateRole.addItem(new String("GUEST"));
        updateRole.setPreferredSize(new Dimension(120, 25));
        JLabel newRealNameLabel = new JLabel("Real name   ");
        updateRealName = new JTextField();
        updateRealName.setPreferredSize(new Dimension(120, 25));
        JLabel newMailLabel = new JLabel("Mail       ");
        updateMail = new JTextField();
        updateMail.setPreferredSize(new Dimension(120, 25));

        updateUserButton = new JButton("Update User");
        updateUserButton.setPreferredSize(new Dimension(120, 40));

        namePanel.add(newUserNameLabel);
        namePanel.add(Box.createRigidArea(new Dimension(5,20)));
        namePanel.add(newPasswordLabel);
        namePanel.add(Box.createRigidArea(new Dimension(5,20)));
        namePanel.add(newRealNameLabel);
        namePanel.add(Box.createRigidArea(new Dimension(5,20)));
        namePanel.add(newMailLabel);
        namePanel.add(Box.createRigidArea(new Dimension(5,20)));
        namePanel.add(newRoleLabel);

        textPanel.add(updateUserName);
        textPanel.add(new JPanel());
        textPanel.add(updatePassword);
        textPanel.add(new JPanel());
        textPanel.add(updateRealName);
        textPanel.add(new JPanel());
        textPanel.add(updateMail);
        textPanel.add(new JPanel());
        textPanel.add(updateRole);

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


        namePanel.setLayout(nameLayout);
        textPanel.setLayout(textLayout);
        buttonPanel.setLayout(buttonLayout);
        buttonPanel.setBorder(null);

        JLabel deleteUserNameLabel = new JLabel("Username   ");
        deleteUserName = new JTextField();
        deleteUserName.setPreferredSize(new Dimension(120, 25));

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
        return (String) newRole.getSelectedItem();
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
        return (String) updateRole.getSelectedItem();
    }

    public String getUpdateRealName() {
        return updateRealName.getText();
    }

    public String getUpdateMail() {
        return updateMail.getText();
    }

}
