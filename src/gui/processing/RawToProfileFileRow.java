package gui.processing;

import gui.JTextFieldLimit;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

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
    private JCheckBox keepSamCheckBox;

    private JLabel inFileLabel;
    private JLabel outFileLabel;
    private JLabel flagsLabel;
    private JLabel genomeReleaseLabel;
    private JLabel keepSamLabel;

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

        this.setLayout(new MigLayout());

        addLabels();
        addInputFields();
    }

    @Override
    protected void addLabels() {
        inFileLabel = new JLabel("Infile");
        outFileLabel = new JLabel("Outfile");
        flagsLabel = new JLabel("Flags");
        genomeReleaseLabel = new JLabel("GR");
        keepSamLabel = new JLabel("Keep .SAM");
        keepSamLabel.setHorizontalAlignment(JLabel.CENTER);

        this.add(inFileLabel);
        this.add(outFileLabel, "w 80:120:160");
        this.add(flagsLabel, "w 80:100:120");
        this.add(genomeReleaseLabel, "w 60:80:100");
        this.add(keepSamLabel, "wrap");
    }

    @Override
    protected void addInputFields() {
        inFileComboBox = new JComboBox<String>(fileNames);
        outFileTextField = new JTextField();
        flagsTextField = new JTextField();
        genomeReleaseComboBox = new JComboBox<String>(genomeReleases);
        keepSamCheckBox = new JCheckBox();
        keepSamCheckBox.setHorizontalAlignment(JCheckBox.CENTER);

        outFileTextField.setDocument(new JTextFieldLimit(512));
        flagsTextField.setDocument(new JTextFieldLimit(256));

        this.add(inFileComboBox);
        this.add(outFileTextField, "w 80:120:160");
        this.add(flagsTextField, "w 80:100:120");
        this.add(genomeReleaseComboBox, "w 60:80:100");
        this.add(keepSamCheckBox, "wrap");
    }

    /**
     * Returns the in file parameter
     * @return the in file parameter
     */
    public String getInFile() {
        Object o = inFileComboBox.getSelectedItem();
        if( o == null) return "null";
        return o.toString();
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
        Object o =  genomeReleaseComboBox.getSelectedItem();
        if( o == null) return "null";
        return o.toString();
    }

    /**
     * Returns the keep .SAM parameter
     * @return the keep .SAM parameter
     */
    public boolean getKeepSam() {
        return keepSamCheckBox.isSelected();
    }
}
