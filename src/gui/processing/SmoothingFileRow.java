package gui.processing;

import gui.JTextFieldLimit;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

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

        this.setLayout(new GridLayout(2,5));

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
        //minSmoothLabel.setHorizontalAlignment(JLabel.CENTER);

        this.add(inFileLabel);
        this.add(outFileLabel);
        this.add(windowSizeLabel);
        this.add(meanOrMedianLabel);
        this.add(minSmoothLabel);

    }

    @Override
    protected void addInputFields() {

        inFileComboBox = new JComboBox<String>(fileNames);
        outFileTextField = new JTextField();
        SpinnerModel windowSizeSpinnerModel = new SpinnerNumberModel(1, 1, 1000, 1);
        windowSizeSpinner = new JSpinner(windowSizeSpinnerModel);
        meanOrMedianComboBox = new JComboBox<String>(new String[]{"Mean", "Median"});
        SpinnerModel minSmoothSpinnerModel = new SpinnerNumberModel(1, 1, 1000, 1);
        minSmoothSpinner = new JSpinner(minSmoothSpinnerModel);

        outFileTextField.setDocument(new JTextFieldLimit(512));

        this.add(inFileComboBox);
        this.add(outFileTextField);
        this.add(windowSizeSpinner);
        this.add(meanOrMedianComboBox);
        this.add(minSmoothSpinner);

    }

    public String getInFile() {
        return inFileComboBox.getSelectedItem().toString();
    }

    public String getOutFile() {
        return outFileTextField.getText();
    }

    public int getWindowSize() {
        return (int) windowSizeSpinner.getValue();
    }

    public String getMeanOrMedian() {
        return meanOrMedianComboBox.getSelectedItem().toString();
    }

    public int getMinSmooth() {
        return (int) minSmoothSpinner.getValue();
    }

}
