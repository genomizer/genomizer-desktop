package gui.sysadmin.genomereleaseview;

import gui.sysadmin.strings.SysStrings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import communication.HTTPURLUpload;

/***
 * Builds the genome release view.
 *
 * @author oi11ahn
 *
 *
 *
 */
public class GenomeReleaseViewCreator {

    private GenomereleaseTableModel grTablemodel;
    private ActionListener buttonListener;

    private JTable grTable;

    private JTextField versionText;
    private JComboBox speciesText;
    private JTextField fileText;

    private JButton addButton;
    private JButton clearButton;
    private JButton deleteButton;
    private JButton fileButton;
    private GenomeTextFieldListener textListner;
    private MouseListener mouseGenomeTableListener;
    private KeyListener keyGenomeTableListener;

    private JPanel fileListPanel;
    private JPanel extraInfoPanel;
    private String[] filenames;
    private JPanel fileProgressPanel;
    private ArrayList<JProgressBar> progressbars = new ArrayList<JProgressBar>();

    public GenomeReleaseViewCreator() {
    }

    /***
     *
     * Creates all the basics for the main panel for the genome release view.
     *
     * @param buttonListener
     *            the listener for all the buttons
     * @param textListener
     *            the text listener
     * @param mgrListener
     *            the mouse listener for the table
     * @param kgrListener
     *            the key listener for the table
     * @return
     */
    public JPanel buildGenomeReleaseView(ActionListener buttonListener,
            GenomeTextFieldListener textListener, MouseListener mgrListener,
            KeyListener kgrListener) {
        this.buttonListener = buttonListener;
        this.textListner = textListener;
        this.mouseGenomeTableListener = mgrListener;
        this.keyGenomeTableListener = kgrListener;
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 250, 250));
        mainPanel.add(buildGenomeReleasePanel(), BorderLayout.CENTER);
        setupExtraInfoPanel();
        return mainPanel;
    }

    /***
     * Builds the main panel for the genome release view.
     *
     * @return the main panel
     */
    public JPanel buildGenomeReleasePanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = buildGenomeHeaderPanel();
        JPanel listPanel = buildGenomeFileList();
        JPanel rightSidePanel = buildSidePanel();

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(listPanel, BorderLayout.CENTER);
        mainPanel.add(rightSidePanel, BorderLayout.EAST);
        return mainPanel;
    }

        /***
     * Builds the available genome releases table.
     *
     * @return the panel containing the genome release table
     */
    public JPanel buildGenomeFileList() {
        fileListPanel = new JPanel(new BorderLayout());

        grTablemodel = new GenomereleaseTableModel();

        grTable = new JTable(grTablemodel);

        grTable.setShowGrid(false);

        TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(
                grTablemodel);


        /** Set the sorting if the column is clicked. */
        grTable.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        grTable.setRowSorter(rowSorter);

        JScrollPane scrollPane = new JScrollPane(grTable);

        grTable.addMouseListener(mouseGenomeTableListener);
        grTable.addKeyListener(keyGenomeTableListener);

        fileListPanel.add(scrollPane, BorderLayout.CENTER);

        return fileListPanel;
    }

    public void setupExtraInfoPanel() {
        extraInfoPanel = new JPanel();
        fileListPanel.add(extraInfoPanel, BorderLayout.NORTH);

    }
    /***
     * Creates the extra info panel containing the extra information about tha
     * files in each genome release and the delete button.
     */

    public void addExtraInfoPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        deleteButton = new JButton(SysStrings.GENOME_BUTTON_DELETE);
        deleteButton.addActionListener(buttonListener);
        JButton closeButton = new JButton(SysStrings.GENOME_BUTTON_CLOSE);
        closeButton.addActionListener(buttonListener);

        JPanel buttonCeptionPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(deleteButton);
        buttonPanel.add(closeButton);
        buttonCeptionPanel.add(buttonPanel, BorderLayout.EAST);

        Border border = BorderFactory.createEmptyBorder(0, 5, 0, 0);
        JLabel headerText = new JLabel("Genome release files");

        headerText.setBorder(border);
        buttonCeptionPanel.add(headerText, BorderLayout.WEST);

        String[] data = grTablemodel.getFilenames(grTable
                .convertRowIndexToModel(grTable.getSelectedRow()));

        if (data == null) data = new String[] { "Does not fucking work", ":(" };

        JList<String> fileNameList = new JList<String>(data);
        fileNameList.setEnabled(false);

        mainPanel.add(fileNameList, BorderLayout.CENTER);

        mainPanel.add(buttonCeptionPanel, BorderLayout.NORTH);
        if (extraInfoPanel != null) removeExtraInfoPanel();

        extraInfoPanel = mainPanel;
        fileListPanel.add(extraInfoPanel, BorderLayout.SOUTH);
        extraInfoPanel.setVisible(true);
        fileListPanel.repaint();
        fileListPanel.setVisible(true);
    }

    /***
     * Builds the progress panel for uploading genome releases.
     *
     * @return the progress panel
     */
    private JPanel buildFileProgressPanel() {

        JPanel progressPanel = new JPanel(new GridLayout(0, 1));
        setFilesForProgresspanel();
        return progressPanel;
    }


        /***
     * Uses the array of filenames to create progress bars for each file
     * uploading.
     * 
     */
    private void setFilesForProgresspanel() {
        if (filenames != null) {
            for (String fileName : filenames) {

                File f = new File(fileName);
                JLabel file = new JLabel(f.getName());
                fileProgressPanel.add(file);
                JProgressBar bar = new JProgressBar(0, 100);
                bar.setName(f.getName());
                progressbars.add(bar);
                fileProgressPanel.add(bar);
            }
        } else {
            if (fileProgressPanel != null) {
                fileProgressPanel.removeAll();
            }
        }
    }
    /***
     * Uses the current uploads to
     * 
     * @param uploads
     * @return
     */
    public boolean updateUploadProgress(
            CopyOnWriteArrayList<HTTPURLUpload> uploads) {
        boolean r = true;
        if (!progressbars.isEmpty()) {
            for (JProgressBar bar : progressbars) {
                // TODO: get currentuploads. see if name matches
                for (HTTPURLUpload upload : uploads) {
                    if (upload.getFileName().equals(bar.getName())) {
                        bar.setValue((int) upload.getCurrentProgress());
                        bar.repaint();
                        bar.setStringPainted(true);
                        if (upload.getCurrentProgress() == 100) {
                            r = false;
                        }
                    }
                }
            }
        }
        return r;
    }

    public void updateFileProgressPanel() {
        setFilesForProgresspanel();
        fileProgressPanel.repaint();
        fileProgressPanel.updateUI();
    }

    public void removeExtraInfoPanel() {
        extraInfoPanel.removeAll();
        extraInfoPanel.setVisible(false);
    }

    private JPanel buildSidePanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(buildAddNewSpeciePanel(), BorderLayout.NORTH);
        mainPanel.add(buildAddGenomeFilePanel(), BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel buildGenomeHeaderPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel();
        /** TODO: set variable string! */
        label.setText("Genome releases");

        Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        label.setBorder(border);
        mainPanel.add(label, BorderLayout.WEST);
        return mainPanel;
    }

    private JPanel buildAddNewSpeciePanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder("Add new species"));

        JPanel containerPanel = new JPanel();

        GroupLayout layout = new GroupLayout(containerPanel);
        containerPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        Border border = BorderFactory.createEmptyBorder(0, 5, 0, 0);

        JPanel textNButton = new JPanel(new BorderLayout());

        /** TODO fix this text and button so it works propperly */
        JLabel specieLabel = new JLabel();
        specieLabel.setBorder(border);
        specieLabel.setText("Species");
        JTextField specie = new JTextField(20);

        JButton button = new JButton("Add");

        textNButton.add(specie, BorderLayout.CENTER);
        textNButton.add(button, BorderLayout.EAST);

        layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(specieLabel).addComponent(textNButton)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(specieLabel).addComponent(textNButton));

        mainPanel.add(containerPanel);

        return mainPanel;
    }

    private JPanel buildAddGenomeFilePanel() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory
                .createTitledBorder("Add new genome release"));
        JPanel containerPanel = new JPanel();

        GroupLayout layout = new GroupLayout(containerPanel);
        containerPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        /* text labels */
        JLabel versionLabel = new JLabel();
        JLabel speciesLabel = new JLabel();
        JLabel fileLabel = new JLabel();

        versionLabel.setText(SysStrings.GENOME_TEXT_GR_VERSION);
        speciesLabel.setText(SysStrings.GENOME_TEXT_SPECIES);
        fileLabel.setText(SysStrings.GENOME_TEXT_GFILE);

        Border border = BorderFactory.createEmptyBorder(0, 5, 0, 0);
        versionLabel.setBorder(border);
        speciesLabel.setBorder(border);
        fileLabel.setBorder(border);

        /* text fields */
        versionText = new JTextField(20);
        versionText.addKeyListener(textListner);
        speciesText = new JComboBox();
        fileText = new JTextField(20);
        fileText.addKeyListener(textListner);
        fileText.setEditable(false);
        fileText.setEnabled(false);

        /* upload status panel */
        // TODO place shit here

        JLabel fileName = new JLabel("genomefile.fasta");
        JProgressBar fileUploadProgress = new JProgressBar(0, 100);

        /* buttons */
        addButton = new JButton(SysStrings.GENOME_BUTTON_ADD);
        addButton.addActionListener(buttonListener);
        addButton.setEnabled(false);

        clearButton = new JButton(SysStrings.GENOME_BUTTON_CLEAR);
        clearButton.addActionListener(buttonListener);
        clearButton.setEnabled(false);

        fileButton = new JButton(SysStrings.GENOME_BUTTON_FILE);
        fileButton.addActionListener(buttonListener);

        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(flowLayout.LEADING);

        JPanel buttonPanel = new JPanel(flowLayout);
        JPanel buttonCeptionPanel = new JPanel(new BorderLayout());

        buttonPanel.add(fileButton);
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);

        buttonCeptionPanel.add(buttonPanel, BorderLayout.WEST);

        fileProgressPanel = buildFileProgressPanel();

        layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(versionLabel).addComponent(versionText)
                        .addComponent(speciesLabel).addComponent(speciesText)
                        .addComponent(fileLabel)
                        .addComponent(fileProgressPanel)
                        .addComponent(buttonCeptionPanel)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(versionLabel).addComponent(versionText)
                .addComponent(speciesLabel).addComponent(speciesText)
                .addComponent(fileLabel).addComponent(fileProgressPanel)
                .addComponent(buttonCeptionPanel));

        mainPanel.add(containerPanel, BorderLayout.NORTH);
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
        return (String) speciesText.getSelectedItem();
    }

    // TODO: this is temporary!
    public String[] getFilenames() {
        return filenames;
    }

    public void clearTextFields() {
        versionText.setText("");
        fileText.setText("");
        filenames = null;
        enableClearButton(false);
        enableAddButton(false);
    }

    public boolean isTextFieldsEmpty() {
        boolean returnValue = true;

        if (!versionText.getText().equals("") || !fileText.getText().equals("")) {
            returnValue = false;
        }

        return returnValue;
    }

    public boolean allTextFieldsContainInfo() {
        boolean returnValue = false;
        if (!versionText.getText().equals("") && !fileText.getText().equals("")) {
            returnValue = true;
        }

        return returnValue;
    }

    public void enableClearButton(boolean status) {
        this.clearButton.setEnabled(status);
    }

    public void enableAddButton(boolean status) {
        this.addButton.setEnabled(status);
    }

    public String getSelectedVersion() {
        /*
        String str = (String) grTable.getValueAt(
                grTable.getSelectedRow(),
                grTable.getTableHeader().getColumnModel()
                        .getColumnIndex(SysStrings.GENOME_TABLE_VERSION));
        */
        int selectedRow = grTable.getSelectedRow();
        if (selectedRow == -1) {
            selectedRow = 0;
        }
        JTableHeader tableheader =grTable.getTableHeader();
        TableColumnModel tcm = tableheader.getColumnModel();
        int columnIndex = tcm.getColumnIndex(SysStrings.GENOME_TABLE_VERSION);
        System.out.println(selectedRow + " " + columnIndex);
        String str = (String) grTable.getValueAt(selectedRow, columnIndex);
        return str;
    }

    public String getSelectedSpecie() {
        return (String) grTable.getValueAt(
                grTable.getSelectedRow(),
                grTable.getTableHeader().getColumnModel()
                        .getColumnIndex(SysStrings.GENOME_TABLE_SPECIES));
    }

    public void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        int ret = fileChooser.showOpenDialog(new JPanel());

        String directoryName = "";
        filenames = null;
        File[] selectedFiles;
        if (ret == JFileChooser.APPROVE_OPTION) {
            try {
                directoryName = fileChooser.getSelectedFile()
                        .getCanonicalPath();

                selectedFiles = fileChooser.getSelectedFiles();
                filenames = new String[selectedFiles.length];
                for (int i = 0; i < selectedFiles.length; i++) {
                    filenames[i] = selectedFiles[i].getCanonicalPath();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            fileText.setText(directoryName);
            enableClearButton(true);
            if (allTextFieldsContainInfo()) enableAddButton(true);
        } else {
            return;
        }
    }

    public boolean isGeneSelected() {
        if (this.grTable.getSelectedRow() < 0) {
            return false;
        } else {
            return true;
        }
    }

    public void setSpeciesDDList(String[] listItems) {
        speciesText.removeAllItems();
        if (listItems != null) {
            for (String item : listItems) {
                speciesText.addItem(item);
            }
        }

        speciesText.repaint();
    }

    /* old crap will be needed next year maybe */
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
}
