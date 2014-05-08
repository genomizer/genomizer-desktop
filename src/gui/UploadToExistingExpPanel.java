package gui;

import util.AnnotationDataType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class UploadToExistingExpPanel extends JPanel {

    private JButton selectFilesToUploadButton;
    private JButton uploadFilesToExperimentButton;
    private ArrayList<JComboBox> annotationBoxes;
    private ArrayList<JTextField> annotationFields;
    private AnnotationDataType[] annotations;

    public UploadToExistingExpPanel() {
        selectFilesToUploadButton = new JButton("Select files");
        uploadFilesToExperimentButton = new JButton("Upload to experiment");
        uploadFilesToExperimentButton.disable();

        setBorder(BorderFactory.createTitledBorder("Experiment"));
/*        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
        gbl_panel.rowHeights = new int[] { 0, 0, 0, 0 };
        gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE };
        gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };

        setLayout(gbl_panel);
*/
        add(selectFilesToUploadButton, BorderLayout.SOUTH);
        add(uploadFilesToExperimentButton, BorderLayout.SOUTH);
    }

    public void addSelectFilesToUploadButtonListener(ActionListener listener) {
        selectFilesToUploadButton.addActionListener(listener);
    }

    public void addUploadToExperimentButtonListener(ActionListener listener) {
        uploadFilesToExperimentButton.addActionListener(listener);
    }

    public void setAnnotations(AnnotationDataType[] annotations) {
        this.annotations = annotations;
    }

    public void addSelectFilesToUploadButton() {
        add(selectFilesToUploadButton);
    }

    public void addUploadFilesToExperimentButton() {
        add(uploadFilesToExperimentButton);
    }

    public void addAnnotationsForExistingExp() throws NullPointerException {
        annotationBoxes = new ArrayList<JComboBox>();
        annotationFields = new ArrayList<JTextField>();
        int x = 0;
        int y = 0;
        String[] annotationNames = new String[annotations.length];
        for (int i = 0; i < annotations.length; i++) {
            if (annotations[i].isForced()) {
                if (x > 6) {
                    System.out.println("H�R");
                    x = 0;
                    y++;
                }
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.anchor = GridBagConstraints.WEST;
                gbc.insets = new Insets(5, 0, 5, 30);
                gbc.gridx = x;
                gbc.gridy = y;
                JPanel p = new JPanel(new BorderLayout());
                JLabel annotationLabel = new JLabel(annotations[i].getName());
                p.add(annotationLabel, BorderLayout.NORTH);
                if (annotations[i].getValues()[0].equals("freetext")) {
                    JTextField textField = new JTextField();
                    textField.setColumns(10);
                    annotationFields.add(textField);
                    p.add(textField, BorderLayout.CENTER);
                    add(p, gbc);
                } else {
                    JComboBox comboBox = new JComboBox(
                            annotations[i].getValues());
                    comboBox.setPreferredSize(new Dimension(120, 31));
                    annotationBoxes.add(comboBox);
                    p.add(comboBox, BorderLayout.CENTER);
                    add(p, gbc);
                }
                x++;
            }
        }
    }
}
