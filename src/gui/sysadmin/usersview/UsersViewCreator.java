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
      //  mainPanel.setBackground(new Color(255, 250, 250));
       // mainPanel.add(buildUsersList(), BorderLayout.WEST);
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
        JPanel searchPanel = new JPanel();
        return searchPanel;
    }

}
