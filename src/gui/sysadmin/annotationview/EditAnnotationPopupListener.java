package gui.sysadmin.annotationview;

import gui.ErrorDialog;
import gui.sysadmin.SysadminTab;
import gui.sysadmin.strings.SysStrings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.JTextField;

import util.RequestException;

import controller.SysadminController;

public class EditAnnotationPopupListener implements ActionListener {

    private SysadminTab sysTab;
    private SysadminController sysController;
    private EditAnnotationPopup editPopup;

    public EditAnnotationPopupListener(SysadminTab sysTab) {
        this.sysTab = sysTab;
        this.sysController = sysTab.getSysController();
        this.editPopup = sysTab.getEditPopup();
    }

    /**
     * The actionPerformed method checks and compares the ActionCommand of the
     * event and performs different actions depending on where the action
     * originated.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {

            case SysStrings.ANNOTATIONS_RENAME:
                String oldName = editPopup.getAnnotation().name;
                String newName = editPopup.getNewAnnotationName();
                try {
                    sysController.renameAnnotationField(oldName, newName);
                    editPopup.getAnnotation().name = newName;
                } catch (RequestException e1) {
                    new ErrorDialog("Couldn't rename annotation value", e1)
                    .showDialog();
                }
                sysController.updateAnnotationTable();
                break;

            case SysStrings.ANNOTATIONS_MODIFY_RENAME:
                // John was here:
                JTextField j1 = getJTextFieldFromEvent(e);
                try {
                    sysController.renameAnnotationValue(
                            editPopup.getAnnotationName(), j1.getName(),
                            j1.getText());
                } catch (RequestException e1) {
                    new ErrorDialog("Couldn't rename annotation value", e1)
                            .showDialog();
                }
                j1.setName(j1.getText());
                editPopup.deactivateUpdateButton((JButton) e.getSource());
                editPopup.updateDocListeners();
                sysController.updateAnnotationTable();
                break;

            case SysStrings.ANNOTATIONS_MODIFY_REMOVE:
                JTextField j2 = getJTextFieldFromEvent(e);
                JPanel panel = (JPanel) j2.getParent();
                try {
                    sysController.removeAnnotationValue(editPopup.getAnnotationName(), j2.getName());
                    panel.setVisible(false);
                } catch(RequestException e1) {
                    new ErrorDialog("Couldn't remove annotation value", e1).showDialog();
                }

                sysController.updateAnnotationTable();
                break;

            case SysStrings.ANNOTATIONS_MODIFY_ADD_VALUE:
                JTextField j3 = getJTextFieldFromEvent(e);
                if (!j3.getText().isEmpty()) {
                    try {
                        sysController.addAnnotationValue(
                            editPopup.getAnnotationName(), j3.getText());
                        sysController.updateAnnotationTable();
                        editPopup.updateAnnotation(j3.getText());
                        editPopup.addEditAnnotationListener(this);
                        editPopup.updateDocListeners();
                    } catch(RequestException e1) {
                        new ErrorDialog("Couldn't add annotation value", e1)
                        .showDialog();
                    }
                }

                break;

            case SysStrings.ANNOTATIONS_MODIFY_SET_FORCED:
                break;

            case SysStrings.ANNOTATIONS_VALUE_NAME_CHANGED:
                break;

            case SysStrings.ANNOTATIONS_MODIFY_CANCEL:
                sysTab.getEditFrame().setVisible(false);
                sysTab.getEditFrame().dispose();
                sysTab.requestFocus();
                break;
        }
    }

    /**
     * @param e
     *            the ActionEvent
     * @return the JTextField connected to the JButton that the ActionEvent
     *         originated from
     */
    private JTextField getJTextFieldFromEvent(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        JTextField jt = (JTextField) b.getParent().getComponent(0);
        return jt;
    }

}
