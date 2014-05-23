package gui.sysadmin.annotationview.panels;

import gui.sysadmin.annotationview.EditAnnotationPopup2;
import gui.sysadmin.strings.SysStrings;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AnnotationNamePanel extends JPanel {
    
    private JButton modifyNameButton;
    private JTextField valueTextField;
    
    /**
     * A superclass for AnnotationValuePanel. May one day be used to create more
     * panels with documentlisteners.
     * 
     * @param popup
     *            is the EditAnnotationPopup which created the panel
     * @param valueName
     *            is the name of the value in the textfield
     */
    public AnnotationNamePanel(EditAnnotationPopup2 popup, String valueName) {
        
        valueTextField = new JTextField(valueName);
        valueTextField.setName(valueName);
        valueTextField.setPreferredSize(new Dimension(180, 30));
        add(valueTextField, 0);
        modifyNameButton = new JButton(SysStrings.ANNOTATIONS_MODIFY_RENAME);
        modifyNameButton.setEnabled(false);
        
        popup.addButtonToButtonList(modifyNameButton);
        popup.addTextFieldToFieldList(valueTextField);
        
        add(modifyNameButton);
    }
    
    /**
     * @return the button which renames the field
     */
    public JButton getRenameButton() {
        return modifyNameButton;
    }
    
    /**
     * @return the text field which contains the name of the field
     */
    public JTextField getNameField() {
        return valueTextField;
    }
    
}
