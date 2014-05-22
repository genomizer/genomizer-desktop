package gui.sysadmin.annotationview.panels;

import gui.sysadmin.annotationview.EditAnnotationPopup2;
import gui.sysadmin.strings.SysStrings;
import javax.swing.JButton;
import javax.swing.JTextField;

public class AnnotationValuePanel extends AnnotationNamePanel {

    private JButton modifyNameButton, removeButton;
    private JTextField valueTextField;

    /**
     * A JPanel which contains a JTextField and two JButtons; one that renames
     * the value and one that removes it.
     *
     * @param popup
     *            the EditAnnotationPopup which built the panel
     * @param valueName
     *            the name of the annotation value as a string
     */
    public AnnotationValuePanel(EditAnnotationPopup2 popup, String valueName) {

        super(popup, valueName);

        removeButton = new JButton(SysStrings.ANNOTATIONS_MODIFY_REMOVE);

        popup.addButtonToButtonList(removeButton);

        add(removeButton);
    }

    /**
     * @return the button which removes the value which the panel represents
     */
    public JButton getRemoveButton() {
        return removeButton;
    }
}
