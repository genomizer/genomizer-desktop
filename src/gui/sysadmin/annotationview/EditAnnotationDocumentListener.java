package gui.sysadmin.annotationview;

import gui.sysadmin.annotationview.panels.AnnotationValuePanel;

import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class EditAnnotationDocumentListener implements DocumentListener {

    private String oldString;
    private JButton button;
    private EditAnnotationPopup2 edPop;
    private AnnotationValuePanel panel;

    /**
     * The EditAnnotationDocumentListener is created with an
     * AnnotationValuePanel and the EditAnnotationPopup that it belongs to as
     * arguments. To make sure the user knows that an annotation value has been
     * changed, it also has an "oldString"-value which will be compared to
     * strings entered to the text field.
     *
     * @param panel
     *            the JPanel holding the textfield and button
     * @param edPop
     *            the EditAnnotationPopup holding the panel
     */
    public EditAnnotationDocumentListener(AnnotationValuePanel panel,
            EditAnnotationPopup2 edPop) {
        this.panel = panel;
        this.oldString = panel.getNameField().getText();

        this.button = panel.getRenameButton();
        this.edPop = edPop;

    }

    @Override
    public void changedUpdate(DocumentEvent ev) {
        // Changed

    }

    /**
     * If a character is added to the text field that the listener is assigned
     * to, the listener checks that the string currently in the field is a valid
     * annotation value. If it is, the button connected to the field is
     * activated.
     */
    @Override
    public void insertUpdate(DocumentEvent ev) {
        String newString = panel.getNameField().getText();
        if (edPop.valueRenameIsValid(oldString, newString)) {
            edPop.activateUpdateButton(button);

        } else {
            edPop.deactivateUpdateButton(button);
        }
    }

    /**
     * If a character is removed from the text field that the listener is
     * assigned to, the listener checks that the string currently in the field
     * is a valid annotation value. If it is, the button connected to the field
     * is activated.
     */
    @Override
    public void removeUpdate(DocumentEvent ev) {
        try {
            String newString = ev.getDocument().getText(0,
                    ev.getDocument().getLength());

            if (edPop.valueRenameIsValid(oldString, newString)) {
                edPop.activateUpdateButton(button);
            } else {
                edPop.deactivateUpdateButton(button);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the value of the listener's oldString to the current string in
     * the text field
     */
    public void updateOldString() {
        oldString = panel.getNameField().getText();
    }

}
