package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import util.FileSizeFormatter;

/**
 * A class representing a row for each browsed files to be uploaded. Each row
 * contains the filename, a progressbar, a dropdown menu for choosing type of
 * file, a close button and a checkbox for selecting the filerow.
 *
 * @author oi11ejn
 */
public class UploadFileRow extends JPanel {

    private static final long serialVersionUID = -4087152834657635393L;
    private ExperimentPanel parent;
    private JPanel filePanel;
    private JLabel fileLabel;
    private JButton closeButton;
    private JCheckBox uploadBox;
    private JComboBox<String> typeBox, genome;
    private JProgressBar uploadBar;
    private File file;

    /**
     * A constructor creating the upload file row.
     *
     * @param f
     *            The file to be uploaded.
     * @param parent
     *            The parent of this file row.
     * @param newExp
     *            Boolean indicating if this file row is for a new experiment or
     *            not.
     */
    public UploadFileRow(File f, ExperimentPanel parent, boolean newExp) {
        this.parent = parent;
        setLayout(new BorderLayout());
        this.file = f;
        filePanel = new JPanel();
        add(filePanel, BorderLayout.SOUTH);
        setContent(newExp);
    }

    /**
     * Method setting the content of this file row.
     *
     * @param newExp
     *            Boolean indicating if this file row is for a new experiment or
     *            not.
     */
    private void setContent(boolean newExp) {
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[] { 0, 0, 0, 0 };
        gbl.rowHeights = new int[] { 0, 0 };
        gbl.columnWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
        gbl.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
        filePanel.setLayout(gbl);

        String fileSize = " ("
                + FileSizeFormatter.convertByteToString(file.length()) + ")";
        fileLabel = new JLabel(file.getName() + fileSize);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        filePanel.add(fileLabel, gbc);

        uploadBar = new JProgressBar(0, 100);
        uploadBar.setStringPainted(true);
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        filePanel.add(uploadBar, gbc);

        String[] fileTypes = { "Profile", "Raw", "Region" };
        typeBox = new JComboBox<String>(fileTypes);
        typeBox.setSelectedItem("Raw");
        typeBox.setPreferredSize(new Dimension(120, 31));
        genome = new JComboBox<String>();
        genome.setPreferredSize(new Dimension(120, 31));
        genome.addItem("NA");
        genome.setEnabled(false);

        // Add actionlistener which sets the GR-box correctly.
        typeBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                genome.removeAllItems();
                if (typeBox.getSelectedItem().toString().equals("Profile")
                        || typeBox.getSelectedItem().toString()
                                .equals("Region")) {
                    ArrayList<String> gr = parent.getGenomeReleases();
                    for (String g : gr) {
                        genome.addItem(g);
                    }
                    if (genome.getItemCount() > 0) {
                        genome.setEnabled(true);
                        typeBox.setSelectedItem(typeBox.getSelectedItem());
                    } else {
                        genome.setEnabled(false);
                    }
                } else if (typeBox.getSelectedItem().toString().equals("Raw")) {
                    genome.removeAllItems();
                    genome.addItem("NA");
                    genome.setSelectedItem(new String("NA"));
                    genome.setEnabled(false);
                }
            }
        });

        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        filePanel.add(typeBox, gbc);

        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 2;
        gbc.gridy = 1;
        filePanel.add(genome, gbc);

        closeButton = new JButton("X");

        // Listener closing this file row when a user presses the close button.
        addCloseButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                parent.deleteFileRow(file);

            }
        });

        GridBagConstraints gbc_btnX = new GridBagConstraints();
        gbc_btnX.gridx = 3;
        gbc_btnX.gridy = 1;
        filePanel.add(closeButton, gbc_btnX);

        if (newExp) {
            JLabel selectLabel = new JLabel(" Select:");
            gbc.insets = new Insets(0, 0, 0, 5);
            gbc.anchor = GridBagConstraints.EAST;
            gbc.gridx = 4;
            gbc.gridy = 1;
            filePanel.add(selectLabel, gbc);
            uploadBox = new JCheckBox();
            gbc.insets = new Insets(0, 0, 0, 5);
            gbc.anchor = GridBagConstraints.EAST;
            gbc.gridx = 5;
            gbc.gridy = 1;
            filePanel.add(uploadBox, gbc);
        }
    }

    /**
     * Method adding a listener to the close button.
     *
     * @param listener
     *            The listener to be added.
     */
    public void addCloseButtonListener(ActionListener listener) {
        closeButton.addActionListener(listener);
    }

    /**
     * Method returning the file name of the file associated to this row.
     *
     * @return a String with the file name.
     */
    public String getFileName() {
        return file.getName();
    }

    /**
     * Method returning the choosen file type associated with this file row.
     *
     * @return a String representing the choosen file type.
     */
    public String getType() {
        return typeBox.getSelectedItem().toString();
    }

    /**
     * Method updating the progress bar of this file row.
     *
     * @param progress
     *            The current progress.
     */
    public void updateProgressBar(float progress) {
        /* Progress is handled as % */
        uploadBar.setMinimum(0);
        uploadBar.setMaximum(100);
        uploadBar.setValue((int) progress);
    }

    /**
     * Boolean indicating if this file row is selected.
     *
     * @return a Boolean indicating if the row is choosen or not.
     */
    public boolean isSelected() {
        return uploadBox.isSelected();
    }

    /**
     * Method returning the chosen genome release for the current file.
     *
     * @return String representing the genome release.
     */
    public String getGenomeRelease() {
        return genome.getSelectedItem().toString();
    }

    /**
     * Method disabling the components of this file row.
     * @param enabled TODO
     */
    public void setRowEnabled(boolean enabled) {
        typeBox.setEnabled(enabled);
        genome.setEnabled(enabled);
        closeButton.setEnabled(enabled);
        if(uploadBox != null) {
            uploadBox.setEnabled(enabled);
        }
    }

    /**
     * Method used to reset the type combobox, when a new species is chosen in
     * the parent panel.
     */
    public void resetType() {
        typeBox.setSelectedItem(typeBox.getSelectedItem());
    }
}
