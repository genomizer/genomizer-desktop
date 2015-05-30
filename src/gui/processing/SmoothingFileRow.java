package gui.processing;

import gui.JTextFieldLimit;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class SmoothingFileRow extends CommandFileRow {

    private JComboBox<String> inFileComboBox;
    private JTextField outFileTextField;
    private JSpinner windowSizeSpinner;
    private JComboBox<String> meanOrMedianComboBox;
    private JSpinner minSmoothSpinner;

    private JLabel inFileLabel;
    private JLabel outFileLabel;
    private JLabel windowSizeLabel;
    private JLabel meanOrMedianLabel;
    private JLabel minSmoothLabel;

    private String[] fileNames;

    public SmoothingFileRow(String[] fileNames) {
        this.fileNames = fileNames;

        this.setLayout(new MigLayout());

        addLabels();
        addInputFields();
    }

    @Override
    protected void addLabels() {
        inFileLabel = new JLabel("Infile");
        outFileLabel = new JLabel("Outfile");
        windowSizeLabel = new JLabel("Window size");
        meanOrMedianLabel = new JLabel("Mean/median");
        minSmoothLabel = new JLabel("Min smoothing");

        this.add(inFileLabel);
        this.add(outFileLabel, CommandFileRow.WIDE);
        this.add(windowSizeLabel, CommandFileRow.NARROW);
        this.add(meanOrMedianLabel, CommandFileRow.MEDIUM);
        this.add(minSmoothLabel, "wrap");

    }

    @Override
    protected void addInputFields() {

        inFileComboBox = new JComboBox<String>(fileNames);
        inFileComboBox.setEditable(true);

        outFileTextField = new JTextField();
        SpinnerModel windowSizeSpinnerModel = new SpinnerNumberModel(1, 0, Integer.MAX_VALUE, 1);
        windowSizeSpinner = new JSpinner(windowSizeSpinnerModel);
        meanOrMedianComboBox = new JComboBox<String>(new String[]{"mean", "median"});
        SpinnerModel minSmoothSpinnerModel = new SpinnerNumberModel(1, 0, Integer.MAX_VALUE, 1);
        minSmoothSpinner = new JSpinner(minSmoothSpinnerModel);

        outFileTextField.setDocument(new JTextFieldLimit(512));

        this.add(inFileComboBox);
        this.add(outFileTextField, CommandFileRow.WIDE);
        this.add(windowSizeSpinner, CommandFileRow.NARROW);
        this.add(meanOrMedianComboBox, CommandFileRow.MEDIUM);
        this.add(minSmoothSpinner);

        inFileComboBox.addActionListener(new InfileActionListener(outFileTextField, ".sgr"));
        inFileComboBox.setSelectedIndex(0);

    }

    public String getInFile() {
        Object o = inFileComboBox.getSelectedItem();
        if( o == null) return "null";
        return o.toString();
    }

    public String getOutFile() {
        return outFileTextField.getText();
    }

    public int getWindowSize() {
        return (int) windowSizeSpinner.getValue();
    }

    public String getMeanOrMedian() {
        Object o = meanOrMedianComboBox.getSelectedItem();
        if( o == null) return "null";
        return o.toString().toLowerCase();
    }

    public int getMinSmooth() {
        return (int) minSmoothSpinner.getValue();
    }

}
