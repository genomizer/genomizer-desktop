package gui.sysadmin.genomereleaseview;

import javax.swing.*;
import java.awt.*;

public class GenomeReleaseViewCreator {

    public GenomeReleaseViewCreator() {

    }

    public JPanel buildGenomeReleaseView() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 250, 250));
        mainPanel.add(buildGenomeReleasePanel(), BorderLayout.WEST);
        return mainPanel;
    }

    public JPanel buildGenomeReleasePanel(){
        return buildGenomeFileList();
    }


    public JPanel buildGenomeFileList(){
        JPanel mainPanel = new JPanel(new BorderLayout());

        String[] header = new String[] { "Genome version", "Species",
                "File name"};
        Object[][] table = new Object[][] {
                { "hg38", "Human", "randomfilename.txt" },
                { "hg36", "Human", "randomfilename.txt" },
                { "hg32", "Human", "randomfilename.txt" },
                { "rn5", "Rat", "randomfilename.txt"}
        };

        JTable cfTable = new JTable(table, header);
        cfTable.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(cfTable);
        scrollPane.setPreferredSize(new Dimension(500, 80));

        mainPanel.add(scrollPane, BorderLayout.CENTER);
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
