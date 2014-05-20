package gui.sysadmin.annotationview;

import gui.sysadmin.strings.SysStrings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import util.AnnotationDataType;

public class EditAnnotationPopup2 extends JPanel {

    private JTable table;
    private AnnotationDataType annotation;
    private JButton activateNameChangeButton = new JButton(SysStrings.ANNOTATIONS_RENAME_FINAL);;
    private JButton renameButton;
    private JTextField nameField;

    public EditAnnotationPopup2(JTable table) {
        this.table = table;
        if (!setAnnotation()) {
            JOptionPane.showMessageDialog(null,
                    "Please select an annotation to edit");
            this.setEnabled(false);
        }
        this.setLayout(new GridLayout(0, 1));
        createAnnotationNamePanel();
        // createForcedPanel();
        createValuesPanel();

    }

    private void createValuesPanel() {

    }

    private void createAnnotationNamePanel() {
        JPanel annotationNamePanel = new JPanel();
        //GroupLayout layout = new GroupLayout(annotationNamePanel);
        //annotationNamePanel.setLayout(layout);

        JLabel name = new JLabel("Name: ");
        annotationNamePanel.add(name);

        JLabel nameLabel = new JLabel(annotation.name);
        Font newLabelFont = new Font(nameLabel.getFont().getName(), Font.BOLD,
                nameLabel.getFont().getSize());
        nameLabel.setFont(newLabelFont);
        annotationNamePanel.add(nameLabel);

        renameButton = new JButton(SysStrings.ANNOTATIONS_RENAME);
        renameButton.setMinimumSize(new Dimension(80, 10));
        annotationNamePanel.add(renameButton);

        JButton forced = new JButton("set Required");
        forced.setMinimumSize(new Dimension(80, 10));
        annotationNamePanel.add(forced);
/*
        layout.setHorizontalGroup(layout.createSequentialGroup()

        .addComponent(renameButton).addComponent(forced));

        layout.setVerticalGroup(layout.createSequentialGroup().addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(renameButton).addComponent(forced)));
*/
        this.add(annotationNamePanel);
    }

    /**
     * Sets the local value for the annotation variable
     */
    private boolean setAnnotation() {
        if (table.getSelectedRow() != -1) {
            int row = table.getSelectedRow();
            row = table.convertRowIndexToModel(row);
            int col = 3;
            annotation = (AnnotationDataType) table.getModel().getValueAt(row,
                    col);
            return true;
        } else {
            System.out.println("You must select an annotation to edit");
            return false;
        }
    }

    public AnnotationDataType getAnnotation() {
        return annotation;
    }

    public String getNewAnnotationName() {
        return nameField.getText();
    }

    public Boolean getNewAnnotationForcedValue() {
        return annotation.isForced();
    }

    public String[] getNewAnnotationCategories() {
        return annotation.getValues();
    }

    public void buildRenameAnnotationPanel() {
        JPanel renameAnnotationPanel = new JPanel();
        nameField = new JTextField();
        nameField.setText(annotation.name);
        nameField.setPreferredSize(new Dimension(200, 30));

        renameAnnotationPanel.add(nameField);
        renameAnnotationPanel.add(activateNameChangeButton);

        this.add(renameAnnotationPanel);
        revalidate();
        validate();
    }

    public void addEditAnnotationListener(ActionListener listener){
        renameButton.addActionListener(listener);
        activateNameChangeButton.addActionListener(listener);

    }


}
