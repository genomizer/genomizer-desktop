package gui.sysadmin.annotationview;

import gui.sysadmin.SysadminController;
import gui.sysadmin.SysadminTab;
import gui.sysadmin.strings.SysStrings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import util.AnnotationDataType;

public class EditAnnotationPopupListener implements ActionListener {

    private SysadminTab sysTab;
    private SysadminController sysController;

    public EditAnnotationPopupListener(SysadminTab sysTab) {
        this.sysTab = sysTab;
        this.sysController = sysTab.getSysController();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case SysStrings.ANNOTATIONS_MODIFY:
                // TODO: Start working here Adam :)
                System.out.println("Editing annotation....");
                sysController.editAnnotation();
                break;

            case SysStrings.ANNOTATIONS_RENAME:
                sysTab.getAnnotationsView().getEditPopup().buildRenameAnnotationPanel();
                break;

            case SysStrings.ANNOTATIONS_RENAME_FINAL:
                sysController.renameAnnotationField();
                break;

        }
    }
}
