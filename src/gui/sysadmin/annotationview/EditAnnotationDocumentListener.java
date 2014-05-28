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

    public EditAnnotationDocumentListener(AnnotationValuePanel panel,
            EditAnnotationPopup2 edPop) {
        this.panel = panel;
        this.oldString = panel.getNameField().getText();

        this.button = panel.getRenameButton();
        this.edPop = edPop;

    }

    @Override
    public void changedUpdate(DocumentEvent ev) {
        System.out.println("ChangedUpdate called");

    }

    @Override
    public void insertUpdate(DocumentEvent ev) {
        String newString = panel.getNameField().getText();
        if (edPop.valueRenameIsValid(oldString, newString)) {
            edPop.activateUpdateButton(button);

        } else {
            edPop.deactivateUpdateButton(button);
        }
    }

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

    public void updateOldString() {
        oldString = panel.getNameField().getText();
    }

}
