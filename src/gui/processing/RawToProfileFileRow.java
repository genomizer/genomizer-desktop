package gui.processing;

import gui.JTextFieldLimit;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class RawToProfileFileRow extends JComponent {

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


}
