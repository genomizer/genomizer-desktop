package gui.processing;

import gui.JTextFieldLimit;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import net.miginfocom.swing.MigLayout;

/**
 * CommandFileRow for a {@link RatioCommandComponent}.
 *
 *
 */
@SuppressWarnings("serial")
public class RatioFileRow extends CommandFileRow {

    private JComboBox<String> preChipFileComboBox;
    private JComboBox<String> postChipFileComboBox;
    private JTextField outFileTextField;
    private JComboBox<String> meanComboBox;
    private JSpinner readsCutoffSpinner;
    private JTextField chromosomeTextField;

    private JLabel preChipFileLabel;
    private JLabel postChipFileLabel;
    private JLabel outFileLabel;
    private JLabel meanLabel;
    private JLabel readsCutoffLabel;
    private JLabel chromosomeLabel;

    private String[] fileNames;

    /**
     * Constructs a new RatioFileRow object with the available file names. The
     * file names will be added to combo boxes.
     *
     * @param fileNames
     *            the files names
     */
    public RatioFileRow(String[] fileNames) {
        this.fileNames = fileNames;

        this.setLayout(new MigLayout());

        addLabels();
        addInputFields();
    }

    @Override
    protected void addLabels() {
        preChipFileLabel = new JLabel("PreChipName");
        postChipFileLabel = new JLabel("PostChipName");
        outFileLabel = new JLabel("Outfile");
        meanLabel = new JLabel("Mean");
        readsCutoffLabel = new JLabel("ReadsCutoff");
        chromosomeLabel = new JLabel("Chromosome");

        this.add(preChipFileLabel, CommandFileRow.WIDE);
        this.add(postChipFileLabel, CommandFileRow.WIDE);
        this.add(outFileLabel, CommandFileRow.MEDIUM);
        this.add(meanLabel, CommandFileRow.NARROW);
        this.add(readsCutoffLabel, CommandFileRow.MEDIUM);
        this.add(chromosomeLabel, "wrap");
    }

    @Override
    protected void addInputFields() {
        preChipFileComboBox = new JComboBox<String>(fileNames);
        postChipFileComboBox = new JComboBox<String>(fileNames);
        outFileTextField = new JTextField();
        meanComboBox = new JComboBox<String>(
                new String[] { "single", "double" });
        SpinnerModel windowSizeSpinnerModel = new SpinnerNumberModel(1, 1,
                1000, 1);
        readsCutoffSpinner = new JSpinner(windowSizeSpinnerModel);
        chromosomeTextField = new JTextField();

        outFileTextField.setDocument(new JTextFieldLimit(512));
        chromosomeTextField.setDocument(new JTextFieldLimit(512));

        this.add(preChipFileComboBox, CommandFileRow.WIDE);
        this.add(postChipFileComboBox, CommandFileRow.WIDE);
        this.add(outFileTextField, CommandFileRow.MEDIUM);
        this.add(meanComboBox, CommandFileRow.NARROW);
        this.add(readsCutoffSpinner, CommandFileRow.MEDIUM);
        this.add(chromosomeTextField, CommandFileRow.NARROW);
    }

    public String getpreChipFile() {
        return preChipFileComboBox.getSelectedItem().toString();
    }

    public String getpostChipFile() {
        return postChipFileComboBox.getSelectedItem().toString();
    }

    public String getOutFile() {
        return outFileTextField.getText();
    }

    public String getMean() {
        return (String) meanComboBox.getSelectedItem();
    }

    public int getReadsCutoff() {
        return (int) readsCutoffSpinner.getValue();
    }

    public String getChromosomeText() {
        return chromosomeTextField.getText();
    }
}
