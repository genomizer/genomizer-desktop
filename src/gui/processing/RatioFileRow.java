package gui.processing;

import gui.JTextFieldLimit;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

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

        this.setLayout(new GridLayout(2, 6));

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

        this.add(preChipFileLabel);
        this.add(postChipFileLabel);
        this.add(outFileLabel);
        this.add(meanLabel);
        this.add(readsCutoffLabel);
        this.add(chromosomeLabel);
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

        this.add(preChipFileComboBox);
        this.add(postChipFileComboBox);
        this.add(outFileTextField);
        this.add(meanComboBox);
        this.add(readsCutoffSpinner);
        this.add(chromosomeTextField);
    }

    public String getpreChipFile() {
        Object o = preChipFileComboBox.getSelectedItem();
        if( o == null) return "null";
        return o.toString();
    }

    public String getpostChipFile() {
        Object o = postChipFileComboBox.getSelectedItem();
        if( o == null) return "null";
        return o.toString();
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
