package gui.sysadmin.usersview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

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

        ;

        Object[] testers = { "Eric Cartman", "Kyle Broflovski",
                "Kenny McCormick", "Stan Marsh", "Butters Stotch" };

        String testme = "HEKJHFRKJEKJ";
        JList usersList = new JList(testers);
        usersList
                .setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        usersList.setLayoutOrientation(JList.VERTICAL);
        usersList.setName("Eric Cartman");

        JScrollPane scrollPane = new JScrollPane(usersList);
        scrollPane.setPreferredSize(new Dimension(250, 80));

        return scrollPane;
    }

    private JPanel buildSearchBar() {

        JPanel searchPanel = new JPanel();
        return searchPanel;
    }

}
