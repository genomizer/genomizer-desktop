package gui.sysadmin.annotationview.panels;

import gui.sysadmin.annotationview.EditAnnotationPopup2;
import gui.sysadmin.strings.SysStrings;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Dimension;

public class AnnotationValuePanel extends AnnotationNamePanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JButton modifyNameButton, removeButton;
    private JTextField valueTextField;



    public AnnotationValuePanel(EditAnnotationPopup2 popup, String valueName) {

        super(popup, valueName);

        removeButton = new JButton(SysStrings.ANNOTATIONS_MODIFY_REMOVE);

        popup.addButtonToButtonList(removeButton);

        add(removeButton);
    }


    public JButton getRemoveButton() {
        return removeButton;
    }
}
