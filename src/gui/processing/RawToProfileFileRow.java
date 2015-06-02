package gui.processing;

import gui.JTextFieldLimit;

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

    private static final String FLAGS_DEFAULT = " -a -m 1 --best -p 10 -v 2 -q -S--phred33";
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

        this.add(inFileLabel, CommandFileRow.WIDE);
        this.add(outFileLabel, CommandFileRow.WIDE);
        this.add(flagsLabel, CommandFileRow.WIDE);
        this.add(genomeReleaseLabel, CommandFileRow.NARROW);
        this.add(keepSamLabel, "wrap");
    }

    @Override
    protected void addInputFields() {
        inFileComboBox = new JComboBox<String>(fileNames);
        inFileComboBox.setEditable(true);

        outFileTextField = new JTextField();
        flagsTextField = new JTextField();
        genomeReleaseComboBox = new JComboBox<String>(genomeReleases);
        keepSamCheckBox = new JCheckBox();
        keepSamCheckBox.setHorizontalAlignment(JCheckBox.CENTER);

        outFileTextField.setDocument(new JTextFieldLimit(512));
        flagsTextField.setDocument(new JTextFieldLimit(256));

        this.add(inFileComboBox, CommandFileRow.WIDE);
        this.add(outFileTextField, CommandFileRow.WIDE);
        this.add(flagsTextField, CommandFileRow.EXTRA_WIDE);
        this.add(genomeReleaseComboBox, CommandFileRow.NARROW);
        this.add(keepSamCheckBox, "wrap");

        inFileComboBox.addActionListener(new InfileActionListener(outFileTextField, ".wig"));
        inFileComboBox.setSelectedIndex(0);
        flagsTextField.setText(FLAGS_DEFAULT);

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
