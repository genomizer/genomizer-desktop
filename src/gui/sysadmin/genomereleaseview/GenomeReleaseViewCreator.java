package gui.sysadmin.genomereleaseview;

import javax.swing.*;
import java.awt.*;

public class GenomeReleaseViewCreator {

    public GenomeReleaseViewCreator() {

    }

    public JPanel buildChainFileView() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 250, 250));
        mainPanel.add(buildChainFileList(), BorderLayout.WEST);
        return mainPanel;
    }

    private JScrollPane buildfileList() {

        String[] header = new String[] { "From version", "To version",
                "File name", "Species" };

        Object[][] table = new Object[][] {
                { "Genome release 3.0", "Genome release 1.0",
                        "randomfilename.txt", "Human" },
                { "Genome release 4.0", "Genome release 3.0",
                        "randomfilename.txt", "Human" },
                { "Genome release 3.0", "Genome release 4.0",
                        "randomfilename.txt", "Human" },
                { "Genome release 4.0", "Genome release 5.0",
                        "randomfilename.txt", "Human" },
                { "Genome release 5.0", "Genome release 3.0",
                        "randomfilename.txt", "Human" } };

        JTable cfTable = new JTable(table, header);
        cfTable.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        // cfTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(cfTable);
        scrollPane.setPreferredSize(new Dimension(500, 80));

        return scrollPane;
    }

    /** TODO: Anna, add your code here! */
    private JPanel buildDropDownFilter() {
        return new JPanel();
    }

    private JPanel buildChainFileList() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel filterPanel = buildDropDownFilter();
        JScrollPane tablePanel = buildfileList();

        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel buildAddChainFilePanel() {
        JPanel mainPanel = new JPanel();

        return mainPanel;
    }

}
