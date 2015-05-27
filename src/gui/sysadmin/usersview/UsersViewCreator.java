package gui.sysadmin.usersview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

public class UsersViewCreator {

    private JButton createButton;

    public UsersViewCreator() {

    }

    public JPanel buildUsersView() {

        JPanel mainPanel = new JPanel(new FlowLayout());
      //  mainPanel.setBackground(new Color(255, 250, 250));
       // mainPanel.add(buildUsersList(), BorderLayout.WEST);
        mainPanel.add(buildCreateUserBar());
        return mainPanel;
    }
//
//    private JScrollPane buildUsersList() {
//
//        // TODO: Read the data from somwhere instead? (OO)
//        /** Example data only */
//        Object[] testers = { "Max Bågling", "Jonas Hedin",
//                "Marcus Lööw", "Petter Johansson", "Oskar Ottander","Christoffer Fladvad", "Viktor Bengtsson"};
//
//
//        JList<Object> usersList = new JList<Object>(testers);
//        usersList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//        usersList.setLayoutOrientation(JList.VERTICAL);
//
//
//
//        JScrollPane scrollPane = new JScrollPane(usersList);
//        scrollPane.setPreferredSize(new Dimension(250, 80));
//
//        return scrollPane;
//    }


    private JPanel buildCreateUserBar() {

        // TODO: Search panel not implemented (OO)
        JPanel searchPanel = new JPanel(new BorderLayout());
        JPanel namePanel = new JPanel();
        JPanel textPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel spacePanel = new JPanel();

        spacePanel.setPreferredSize(new Dimension(120,5));

        BoxLayout nameLayout = new BoxLayout(namePanel,BoxLayout.PAGE_AXIS);
        BoxLayout textLayout = new BoxLayout(textPanel,BoxLayout.PAGE_AXIS);
        BorderLayout buttonLayout = new BorderLayout();

        buttonPanel.setBorder(BorderFactory.createEtchedBorder());

        namePanel.setLayout(nameLayout);
        textPanel.setLayout(textLayout);
        buttonPanel.setLayout(buttonLayout);


        JLabel newUserNameLabel = new JLabel("Username   ");
        JTextPane newUserName = new JTextPane();
        newUserName.setPreferredSize(new Dimension(120,20));
        JLabel newPasswordLabel = new JLabel("Password   ");
        JTextPane newPassword = new JTextPane();
        newPassword.setPreferredSize(new Dimension(120,20));
        JLabel newRoleLabel = new JLabel("Role       ");
        JTextPane newRole = new JTextPane();
        newRole.setPreferredSize(new Dimension(120,20));
        JLabel newRealNameLabel = new JLabel("Real name   ");
        JTextPane newRealName = new JTextPane();
        newRealName.setPreferredSize(new Dimension(120,20));
        JLabel newMailLabel = new JLabel("Mail       ");
        JTextPane newMail = new JTextPane();
        newMail.setPreferredSize(new Dimension(120,20));


        createButton = new JButton("Create User");
        createButton.setPreferredSize(new Dimension(120,40));
        
        

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

        buttonPanel.add(createButton);


        searchPanel.add(namePanel, BorderLayout.WEST);
        searchPanel.add(textPanel, BorderLayout.CENTER);
        searchPanel.add(buttonPanel, BorderLayout.EAST);



        return searchPanel;
    }
    
    /***
     * Sets the same listener to all the buttons.
     *
     * @param addAnnotationListener
     *            the listener.
     */
    public void createUserButtonListener(ActionListener actionListener) {
        createButton.addActionListener(actionListener);

    }

}
