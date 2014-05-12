package gui.sysadmin;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class UsersViewCreator {

    public UsersViewCreator() {

    }

    public JPanel buildUsersView() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 250, 250));
        mainPanel.add(buildUsersList(), BorderLayout.WEST);
        return mainPanel;
    }

    private JScrollPane buildUsersList() {

        JScrollPane scrollPane = new JScrollPane();
        String testme = "HEKJHFRKJEKJ";
        JList usersList = new JList();


        scrollPane.add(usersList);

        return scrollPane;
    }

}
