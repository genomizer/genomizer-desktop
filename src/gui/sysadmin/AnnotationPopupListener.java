package gui.sysadmin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnnotationPopupListener implements ActionListener {

    private SysadminTab sysTab;
    private SysadminController sysController;

    public AnnotationPopupListener(SysadminTab sysTab) {
        this.sysTab = sysTab;
        this.sysController = sysTab.getSysController();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (SysAdminButton.fromString(e.getActionCommand())) {
        case POPUP_CREATE_ANNOTATION:
            System.out.println("Creating new annotation....");
            sysController.sendNewAnnotation();
            break;

        }
    }
}
