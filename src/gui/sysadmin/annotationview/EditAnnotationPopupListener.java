package gui.sysadmin.annotationview;

import gui.sysadmin.SysadminController;
import gui.sysadmin.SysadminTab;
import gui.sysadmin.strings.SysStrings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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
                sysController.renameAnnotationField(oldName, newName);
                sysController.updateAnnotationTable();
                break;
                
            case SysStrings.ANNOTATIONS_MODIFY_RENAME:
                //John was here:
                JButton b1 = (JButton)e.getSource();
                JTextField j1 = (JTextField) b1.getParent().getComponent(0);
                sysController.renameAnnotationValue(editPopup.getNewAnnotationName(), j1.getName(), j1.getText());
                sysController.updateAnnotationTable();
                break;
                
            case SysStrings.ANNOTATIONS_MODIFY_REMOVE:
                System.out.println("remove!");
                JButton b2 = (JButton)e.getSource();
                JPanel panel = (JPanel) b2.getParent();
                JTextField j2 = (JTextField) b2.getParent().getComponent(0);
                sysController.removeAnnotationValue(editPopup.getNewAnnotationName(), j2.getName());
                panel.setVisible(false);
                panel.getParent().remove(panel);
                sysController.updateAnnotationTable();
                break;
                
            case SysStrings.ANNOTATIONS_MODIFY_ADD_VALUE:
                break;
        }
    }
}
