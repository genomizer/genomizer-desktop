package gui.sysadmin.annotationview;

import gui.sysadmin.annotationview.panels.AnnotationValuePanel;
import gui.sysadmin.strings.SysStrings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import util.AnnotationDataType;

public class EditAnnotationPopup2 extends JPanel {
    
    private JTable table;
    private AnnotationDataType annotation;
    JButton cancelButton = new JButton(SysStrings.ANNOTATIONS_MODIFY_CANCEL);
    private JButton activateNameChangeButton = new JButton(
            SysStrings.ANNOTATIONS_RENAME_FINAL);;
    
    private JButton renameButton;
    private JTextField nameField;
    private ArrayList<JButton> valueButtons = new ArrayList<JButton>();
    private ArrayList<JTextField> valueFields = new ArrayList<JTextField>();
    private JPanel centerpanel;
    private ArrayList<AnnotationValuePanel> valuePanels = new ArrayList<AnnotationValuePanel>();
    private ArrayList<EditAnnotationDocumentListener> docListeners = new ArrayList<EditAnnotationDocumentListener>();
    
    public EditAnnotationPopup2(JTable table) {
        this.table = table;
        if (!setAnnotation()) {
            JOptionPane.showMessageDialog(null,
                    "Please select an annotation to edit");
            this.setEnabled(false);
        } else {
            this.setLayout(new BorderLayout());
            createAnnotationNamePanel();
            createValuesPanel();
            createCancelPanel();
        }
    }
    
    /**
     * Creates the panel holding the "close" button and sets it to the south
     */
    private void createCancelPanel() {
        JPanel cancelPanel = new JPanel();
        cancelPanel.add(cancelButton);
        this.add(cancelPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Creates the panel which holds all of the annotation's values
     */
    private void createValuesPanel() {
        
        centerpanel = new JPanel();
        for (String annotationValue : annotation.getValues()) {
            AnnotationValuePanel panel = createAnnotationValue(annotationValue);
            valuePanels.add(panel);
            centerpanel.add(panel);
        }
        JPanel addValuePanel = createAddValuePanel();
        centerpanel.add(addValuePanel);
        JPanel scrollPanel = new JPanel();
        scrollPanel.add(centerpanel);
        this.add(centerpanel, BorderLayout.CENTER);
    }
    
    /**
     * Creates a JPanel containing the text field and button required to create
     * a new value for an annotations
     * 
     * @return the JPanel containing the field and button
     */
    private JPanel createAddValuePanel() {
        JPanel panel = new JPanel();
        JButton addValueButton = new JButton(
                SysStrings.ANNOTATIONS_MODIFY_ADD_VALUE);
        addValueButton.setName(SysStrings.ANNOTATIONS_MODIFY_ADD_VALUE);
        JTextField valueTextField = new JTextField("");
        valueTextField.setName("addedTextField");
        valueTextField.setPreferredSize(new Dimension(180, 30));
        panel.add(valueTextField, 0);
        panel.add(addValueButton);
        valueButtons.add(addValueButton);
        return panel;
    }
    
    /**
     * Adds a button to the EditAnnotationPopup's list of buttons
     * 
     * @param button
     *            is the button to be added
     */
    public void addButtonToButtonList(JButton button) {
        valueButtons.add(button);
        
    }
    
    /**
     * Adds a text field to the EditAnnotationPopup's list of text fields
     * 
     * @param field
     *            is the field to be added
     */
    public void addTextFieldToFieldList(JTextField field) {
        valueFields.add(field);
    }
    
    /**
     * Creates a panel holding an annotation value and the tools to rename and
     * remove it.
     * 
     * @param name
     *            is the name of the annotation value
     * @return the AnnotationValuePanel
     */
    private AnnotationValuePanel createAnnotationValue(String name) {
        AnnotationValuePanel panel = new AnnotationValuePanel(this, name);
        return panel;
    }
    
    /**
     * Creates the panel holding the annotation name and the button which
     * renames it
     */
    private void createAnnotationNamePanel() {
        JPanel annotationNamePanel = new JPanel();
        
        JLabel name = new JLabel("Name: ");
        annotationNamePanel.add(name);
        
        nameField = new JTextField(annotation.name);
        nameField.setPreferredSize(new Dimension(200, 30));
        annotationNamePanel.add(nameField);
        renameButton = new JButton(SysStrings.ANNOTATIONS_RENAME);
        renameButton.setMinimumSize(new Dimension(80, 10));
        renameButton.setEnabled(true);
        annotationNamePanel.add(renameButton);
        
        // JButton forced = new JButton("set Required");
        // forced.setMinimumSize(new Dimension(80, 10));
        // annotationNamePanel.add(forced);
        this.add(annotationNamePanel, BorderLayout.NORTH);
        // valuePanels.add(annotationNamePanel);
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
    
    /**
     * @return the annotation which is to be edited as an AnnotationDataType
     */
    public AnnotationDataType getAnnotation() {
        return annotation;
    }
    
    /**
     * @return the name of the annotation to be edited as a String
     */
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
    
    public void addEditAnnotationListener(ActionListener listener) {
        renameButton.addActionListener(listener);
        activateNameChangeButton.addActionListener(listener);
        cancelButton.addActionListener(listener);
        
        for (JButton button : valueButtons) {
            button.addActionListener(listener);
        }
        
        for (AnnotationValuePanel panel : valuePanels) {
            EditAnnotationDocumentListener listen = new EditAnnotationDocumentListener(
                    panel, this);
            panel.getNameField().getDocument().addDocumentListener(listen);
            docListeners.add(listen);
        }
        
    }
    
    protected void deactivateUpdateButton(JButton button) {
        button.setEnabled(false);
    }
    
    public void activateUpdateButton(JButton button) {
        
        button.setEnabled(true);
        
    }
    
    public void updateAnnotation(String name) {
        centerpanel.remove(centerpanel.getComponents().length - 1);
        centerpanel.add(createAnnotationValue(name));
        centerpanel.add(createAddValuePanel());
        centerpanel.updateUI();
        updateUI();
        this.repaint();
        
    }
    
    public boolean valueRenameIsValid(String oldString, String newName) {
        if (!(oldString.equals(newName))) {
            return true;
        } else
            return false;
        
    }
    
    public void updateDocListeners() {
        for (EditAnnotationDocumentListener listener : docListeners) {
            listener.updateOldString();
        }
    }
    
}
