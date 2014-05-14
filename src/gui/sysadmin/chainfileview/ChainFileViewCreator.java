package gui.sysadmin.chainfileview;

import javax.swing.*;
import java.awt.*;

public class ChainFileViewCreator {

    public ChainFileViewCreator() {

    }

    public JPanel buildChainFileView() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 250, 250));
        mainPanel.add(buildChainFileList(), BorderLayout.WEST);
        return mainPanel;
    }

    private JScrollPane buildChainFileList() {

        String[] header = new String[] { "To version", "From version",
                "File name" };

        Object[][] table = new Object[][] {
                { "Genome release 3.0", "Genome release 1.0",
                        "randomfilename.txt" },
                { "Genome release 4.0", "Genome release 3.0",
                        "randomfilename.txt" },
                { "Genome release 3.0", "Genome release 4.0",
                        "randomfilename.txt" },
                { "Genome release 4.0", "Genome release 5.0",
                        "randomfilename.txt" },
                { "Genome release 5.0", "Genome release 3.0",
                        "randomfilename.txt" }
        };

        JTable cfTable = new JTable(table, header);
        cfTable.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        cfTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(cfTable);
        scrollPane.setPreferredSize(new Dimension(500, 80));

        return scrollPane;
    }

    private JPanel buildAddChainFilePanel() {
        JPanel mainPanel = new JPanel();

        return mainPanel;
    }

}
