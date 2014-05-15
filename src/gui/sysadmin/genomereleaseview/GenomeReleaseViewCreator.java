package gui.sysadmin.genomereleaseview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import util.GenomeReleaseData;

public class GenomeReleaseViewCreator {

    GenomereleaseTableModel grTablemodel;

    public GenomeReleaseViewCreator() {

    }

    public JPanel buildGenomeReleaseView() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 250, 250));
        mainPanel.add(buildGenomeReleasePanel(), BorderLayout.NORTH);
        return mainPanel;
    }

    public JPanel buildGenomeReleasePanel(){
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel listPanel = buildGenomeFileList();
        JPanel addGenomePanel = buildAddGenomeFilePanel();

        mainPanel.add(listPanel, BorderLayout.CENTER);
        mainPanel.add(addGenomePanel, BorderLayout.EAST);
        return mainPanel;
    }

    private JPanel buildAddGenomeFilePanel() {
        JPanel mainPanel = new JPanel(new FlowLayout());
        JTextField version = new JTextField(20);
        JTextField species = new JTextField(20);


        mainPanel.add(version);
        mainPanel.add(species);
        return mainPanel;
    }

    public JPanel buildGenomeFileList(){
        JPanel mainPanel = new JPanel(new BorderLayout());




        /******************************* TEST *********************************/
        GenomeReleaseData gr1 = new GenomeReleaseData("version1", "dolphin",
                "filename.txt");

        GenomeReleaseData gr2 = new GenomeReleaseData("version2", "pig",
                "bfilename.txt");

        GenomeReleaseData gr3 = new GenomeReleaseData("version3", "zebra",
                "afilename.txt");


        GenomeReleaseData[] grdarray = new GenomeReleaseData[3];
        grdarray[0] = gr1;
        grdarray[1] = gr2;
        grdarray[2] = gr3;

        /***********************************************************************/

        grTablemodel = new GenomereleaseTableModel();

        JTable grTable = new JTable(grTablemodel);
        grTablemodel.setGenomeReleases(grdarray);
        /** Wrong array */
        grTable.setShowGrid(false);

        TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(
                grTablemodel);

        grTable.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        grTable.setRowSorter(rowSorter);

        JScrollPane scrollPane = new JScrollPane(grTable);
        JTableHeader header2 = grTable.getTableHeader();



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


        JScrollPane scrollPane = new JScrollPane(cfTable);


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

    public TableModel getTableModel() {
        // TODO Auto-generated method stub
        return grTablemodel;
    }

}
