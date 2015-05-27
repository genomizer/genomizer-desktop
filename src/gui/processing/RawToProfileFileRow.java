package gui.processing;

import gui.JTextFieldLimit;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * CommandFileRow for a {@link RawToProfileCommandComponent}.
 * @author oi12mlw, oi12pjn
 *
 */
@SuppressWarnings("serial")
public class RawToProfileFileRow extends CommandFileRow {

    private JComboBox<String> inFileComboBox;
    private JTextField outFileTextField;
    private JTextField flagsTextField;
    private JComboBox<String> genomeReleaseComboBox;

    private JLabel inFileLabel;
    private JLabel outFileLabel;
    private JLabel flagsLabel;
    private JLabel genomeReleaseLabel;

    private String[] fileNames;
    private String[] genomeReleases;

    /**
     * Constructs a new RawToProfileFileRow object with the available file names
     * and genome releases. The file names and genome releases will be added to
     * combo boxes.
     *
     * @param fileNames
     *            the files names
     * @param genomeReleases
     *            the genome releases
     */
    public RawToProfileFileRow(String[] fileNames, String[] genomeReleases) {
        this.fileNames = fileNames;
        this.genomeReleases = genomeReleases;

        this.setLayout(new GridLayout(2,4));

        addLabels();
        addInputFields();
    }

    private void addLabels() {
        inFileLabel = new JLabel("Infile");
        outFileLabel = new JLabel("Outfile");
        flagsLabel = new JLabel("Flags");
        genomeReleaseLabel = new JLabel("GR");

        this.add(inFileLabel);
        this.add(outFileLabel);
        this.add(flagsLabel);
        this.add(genomeReleaseLabel);
    }

    private void addInputFields() {
        inFileComboBox = new JComboBox<String>(fileNames);
        outFileTextField = new JTextField();
        flagsTextField = new JTextField();
        genomeReleaseComboBox = new JComboBox<String>(genomeReleases);

        outFileTextField.setDocument(new JTextFieldLimit(32));
        flagsTextField.setDocument(new JTextFieldLimit(32));

        this.add(inFileComboBox);
        this.add(outFileTextField);
        this.add(flagsTextField);
        this.add(genomeReleaseComboBox);
    }

    /**
     * Returns the in file parameter
     * @return the in file parameter
     */
    public String getInFile() {
        return inFileComboBox.getSelectedItem().toString();
    }

    /**
     * Returns the out file parameter
     * @return the out file parameter
     */
    public String getOutFile() {
        return outFileTextField.getText();
    }

    /**
     * Returns the flags parameter
     * @return the flags parameter
     */
    public String getFlags() {
        return flagsTextField.getText();
    }

    /**
     * Returns the genome release parameter
     * @return the genome release parameter
     */
    public String getGenomeRelease() {
        return genomeReleaseComboBox.getSelectedItem().toString();
    }
}