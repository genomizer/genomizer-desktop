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
public class StepFileRow extends CommandFileRow {

    private JComboBox<String> inFileComboBox;
    private JTextField outFileTextField;
    private JSpinner stepSizeSpinner;

    private JLabel inFileLabel;
    private JLabel outFileLabel;
    private JLabel stepSizeLabel;

    private String[] fileNames;

    public StepFileRow(String[] fileNames) {

        this.fileNames = fileNames;

        this.setLayout(new MigLayout());

        addLabels();
        addInputFields();

    }

    @Override
    protected void addLabels() {


        inFileLabel = new JLabel("Infile");
        outFileLabel = new JLabel("Outfile");
        stepSizeLabel = new JLabel("Step size");

        this.add(inFileLabel);
        this.add(outFileLabel, CommandFileRow.WIDE);
        this.add(stepSizeLabel, "wrap");
    }

    @Override
    protected void addInputFields() {

        inFileComboBox = new JComboBox<String>(fileNames);
        inFileComboBox.setEditable(true);

        outFileTextField = new JTextField();
        SpinnerModel stepSizeSpinnerModel = new SpinnerNumberModel(1, 0, Integer.MAX_VALUE, 1);
        stepSizeSpinner = new JSpinner(stepSizeSpinnerModel);

        outFileTextField.setDocument(new JTextFieldLimit(512));

        this.add(inFileComboBox);
        this.add(outFileTextField, CommandFileRow.WIDE);
        this.add(stepSizeSpinner, CommandFileRow.MEDIUM);

        inFileComboBox.addActionListener(new InfileActionListener(outFileTextField, ".sgr"));
        inFileComboBox.setSelectedIndex(0);

    }

    public String getInFile() {
        return inFileComboBox.getSelectedItem().toString();
    }

    public String getOutFile() {
        return outFileTextField.getText();
    }

    public int getStepSize() {
        return (int) stepSizeSpinner.getValue();
    }
}
