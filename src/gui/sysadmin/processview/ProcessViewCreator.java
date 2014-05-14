package gui.sysadmin.processview;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * Created by dv12ilr on 2014-05-12.
 */
public class ProcessViewCreator {

    public ProcessViewCreator() {

    }

    public JPanel buildProcessView() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        JPanel mainContent = new JPanel(new BorderLayout());
        JPanel sideBar = new JPanel(new BorderLayout());

        mainPanel.setBackground(Color.cyan);

        JPanel processTab = processListPanel();
        JPanel processInfo = processDetailedInfoPanel();
        JPanel serverInfo = serverInfoPanel();

        mainContent.add(processInfo, BorderLayout.NORTH);
        mainContent.add(serverInfo, BorderLayout.CENTER);

        sideBar.add(processTab, BorderLayout.EAST);

        mainPanel.add(mainContent, BorderLayout.CENTER);
        mainPanel.add(sideBar, BorderLayout.EAST);
        return mainPanel;

    }

    private JPanel processListPanel() {
        JPanel processPanel = new JPanel(new BorderLayout());

        JTable table = new JTable(new Object[][] { { "Test" }, { "This" } },
                new String[] { "Processes in queue" });

        table.setPreferredSize(new Dimension(50, 200));
        table.setMaximumSize(new Dimension(50, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        processPanel.setBackground(Color.DARK_GRAY);
        JScrollPane scrollPane = new JScrollPane(table);
        processPanel.add(scrollPane);
        return processPanel;
    }

    private JPanel processDetailedInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.RED);
        return infoPanel;
    }

    private JPanel serverInfoPanel() {
        JPanel serverPanel = new JPanel();
        serverPanel.setBackground(Color.BLUE);
        return serverPanel;
    }

}
