package gui.sysadmin.genomereleaseview;

import gui.sysadmin.strings.SysStrings;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class GenomeReleaseViewCreator {

    private GenomereleaseTableModel grTablemodel;
    private ActionListener          buttonListener;

    private JTextField versionText;
    private JTextField speciesText;
    private JTextField fileText;

    private JButton addButton;
    private JButton clearButton;
    private JButton deleteButton;
    private JButton fileButton;
    private GenomeTextFieldListener textListner;

    public GenomeReleaseViewCreator() {

    }

    public JPanel buildGenomeReleaseView(ActionListener buttonListener,
            GenomeTextFieldListener textListener) {
        this.buttonListener = buttonListener;
        this.textListner = textListener;
        textListener.setGenomeView(this);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 250, 250));
        mainPanel.add(buildGenomeReleasePanel(), BorderLayout.NORTH);
        return mainPanel;
    }

    public JPanel buildGenomeReleasePanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());


        JPanel headerPanel = buildGenomeHeaderPanel();
        JPanel listPanel = buildGenomeFileList();
        JPanel addGenomePanel = buildAddGenomeFilePanel();

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(listPanel, BorderLayout.CENTER);
        mainPanel.add(addGenomePanel, BorderLayout.EAST);
        return mainPanel;
    }

    private JPanel buildGenomeHeaderPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JLabel label = new JLabel();
        /** TODO: set variable string! */
        label.setText("Genome release files");
        
        Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        label.setBorder(border);
        mainPanel.add(label, BorderLayout.WEST);
        return mainPanel;
    }
    
    private JPanel buildAddGenomeFilePanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel containerPanel = new JPanel();

        GroupLayout layout = new GroupLayout(containerPanel);
        containerPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        Border border = BorderFactory.createEmptyBorder(0, 5, 0, 0);

        JLabel versionLabel = new JLabel();
        JLabel speciesLabel = new JLabel();
        JLabel fileLabel = new JLabel();

        versionLabel.setText(SysStrings.GENOME_TEXT_GR_VERSION);
        speciesLabel.setText(SysStrings.GENOME_TEXT_SPECIES);
        fileLabel.setText(SysStrings.GENOME_TEXT_GFILE);

        versionLabel.setBorder(border);
        speciesLabel.setBorder(border);
        fileLabel.setBorder(border);

        addButton = new JButton(SysStrings.GENOME_BUTTON_ADD);
        addButton.addActionListener(buttonListener);
        clearButton = new JButton(SysStrings.GENOME_BUTTON_CLEAR);
        clearButton.addActionListener(buttonListener);
        deleteButton = new JButton(SysStrings.GENOME_BUTTON_DELETE);
        deleteButton.addActionListener(buttonListener);
        fileButton = new JButton(SysStrings.GENOME_BUTTON_FILE);
        fileButton.addActionListener(buttonListener);

        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(flowLayout.LEADING);


        JPanel buttonPanel = new JPanel(flowLayout);
        JPanel buttonDeletePanel = new JPanel(flowLayout);

        JPanel buttonCeptionPanel = new JPanel(new BorderLayout());
        JPanel buttonCepDeletePanel = new JPanel(new BorderLayout());


        buttonPanel.add(fileButton);
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);

        buttonDeletePanel.add(deleteButton);

        buttonCeptionPanel.add(buttonPanel, BorderLayout.WEST);
        buttonCepDeletePanel.add(buttonDeletePanel, BorderLayout.WEST);
        
        versionText = new JTextField(20);
        speciesText = new JTextField(20);
        fileText = new JTextField(20);
        fileText.setEditable(false);


        layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(versionLabel).addComponent(versionText)
                        .addComponent(speciesLabel).addComponent(speciesText)
                        .addComponent(fileLabel).addComponent(fileText)
                        .addComponent(buttonCeptionPanel)
                        .addComponent(buttonCepDeletePanel)
        ));
        
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(versionLabel).addComponent(versionText)
                .addComponent(speciesLabel).addComponent(speciesText)
                .addComponent(fileLabel).addComponent(fileText).addComponent(buttonCeptionPanel)
                .addComponent(buttonCepDeletePanel)
        );
        

        mainPanel.add(containerPanel, BorderLayout.NORTH);
        return mainPanel;
    }

    public JPanel buildGenomeFileList() {
        JPanel mainPanel = new JPanel(new BorderLayout());


        grTablemodel = new GenomereleaseTableModel();

        JTable grTable = new JTable(grTablemodel);

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

    public String getVersionText() {
        return versionText.getText();
    }

    public String getSpeciesText() {
        return speciesText.getText();
    }

    public String getFileText() {
        return fileText.getText();
    }

    public void clearTextFields(){
        versionText.setText("");
        speciesText.setText("");
        fileText.setText("");
    }

    public boolean isTextFieldsEmpty(){
        return false;
    }

    public void enableClearButton(boolean status){
        this.clearButton.setEnabled(status);
    }

    public void enableAddButton(boolean status){
        this.addButton.setEnabled(status);
    }

    public void enableDeleteButton(boolean status){
        this.deleteButton.setEnabled(status);
    }
}
