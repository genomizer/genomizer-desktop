package gui.sysadmin.annotationview;

import gui.sysadmin.strings.SysStrings;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.util.ArrayList;

public class AnnotationValuePanel extends JPanel{
    private EditAnnotationPopup2 popup;
    private JButton modifyNameButton, removeButton;
    private JTextField valueTextField;


    public AnnotationValuePanel(EditAnnotationPopup2 popup, String valueName) {
        this.popup = popup;

        valueTextField = new JTextField(valueName);
        valueTextField.setName(valueName);
        valueTextField.setPreferredSize(new Dimension(180, 30));
        add(valueTextField, 0);
        modifyNameButton = new JButton(
                SysStrings.ANNOTATIONS_MODIFY_RENAME);
        modifyNameButton.setEnabled(false);
        removeButton = new JButton(SysStrings.ANNOTATIONS_MODIFY_REMOVE);

        popup.addButtonToButtonList(removeButton);
        popup.addButtonToButtonList(modifyNameButton);
        popup.addTextFieldToFieldList(valueTextField);

        add(modifyNameButton);
        add(removeButton);
    }

    public JButton getRenameButton() {
        return modifyNameButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JTextField getNameField() {
        return valueTextField;
    }


}
