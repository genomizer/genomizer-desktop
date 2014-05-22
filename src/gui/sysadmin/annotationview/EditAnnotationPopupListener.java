package gui.sysadmin.annotationview;

import gui.sysadmin.SysadminController;
import gui.sysadmin.SysadminTab;
import gui.sysadmin.strings.SysStrings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class EditAnnotationPopupListener implements ActionListener {

    private SysadminTab sysTab;
    private SysadminController sysController;
    private EditAnnotationPopup2 editPopup;

    public EditAnnotationPopupListener(SysadminTab sysTab) {
        this.sysTab = sysTab;
        this.sysController = sysTab.getSysController();
        this.editPopup = sysTab.getEditPopup();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case SysStrings.ANNOTATIONS_MODIFY:
                System.out.println("Editing annotation....");
                sysController.editAnnotation();
                sysController.updateAnnotationTable();
                break;

            case SysStrings.ANNOTATIONS_RENAME:
                String oldName = editPopup.getAnnotation().name;

                String newName = editPopup.getNewAnnotationName();
                if (sysController.renameAnnotationField(oldName, newName)){
                    editPopup.getAnnotation().name = newName;
                }
                sysController.updateAnnotationTable();
                break;

            case SysStrings.ANNOTATIONS_MODIFY_RENAME:
                //John was here:
                JTextField j1 = getJTextFieldFromEvent(e);
                if (sysController.renameAnnotationValue(editPopup.getNewAnnotationName(), j1.getName(), j1.getText())) {
                    j1.setName(j1.getText());
                    editPopup.deactivateUpdateButton((JButton)e.getSource());
                    editPopup.updateDocListeners();

                    System.out.println("set name");
                } else {
                    JOptionPane.showMessageDialog(editPopup, "Could not rename annotation value!");
                }

                sysController.updateAnnotationTable();
                break;

            case SysStrings.ANNOTATIONS_MODIFY_REMOVE:
                System.out.println("remove!");
                JTextField j2 = getJTextFieldFromEvent(e);
                JPanel panel = (JPanel) j2.getParent();
                if (sysController.removeAnnotationValue(editPopup.getNewAnnotationName(), j2.getName())) {
                    panel.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(editPopup, "Could not remove annotation value!");
                }
                sysController.updateAnnotationTable();
                break;

            case SysStrings.ANNOTATIONS_MODIFY_ADD_VALUE:
                System.out.println("add annotation value");
                JTextField j3 = getJTextFieldFromEvent(e);
                if (!j3.getText().isEmpty() ){
                    if (sysController.addAnnotationValue(editPopup.getNewAnnotationName(), j3.getText())) {
                        sysController.updateAnnotationTable();
                        editPopup.updateAnnotation(j3.getText());
                        editPopup.addEditAnnotationListener(this);
                        editPopup.updateDocListeners();
                    } else {
                        JOptionPane.showMessageDialog(editPopup, "Could not add annotation value!");
                    }
                }

                break;

            case SysStrings.ANNOTATIONS_MODIFY_SET_FORCED:
                System.out.println("HEJSAN!");
                break;

            case SysStrings.ANNOTATIONS_VALUE_NAME_CHANGED:
                System.out.println("Value name has changed!");
                break;
        }
    }

    private JTextField getJTextFieldFromEvent(ActionEvent e){
        JButton b = (JButton)e.getSource();
        JTextField jt = (JTextField) b.getParent().getComponent(0);
        return jt;
    }

}
