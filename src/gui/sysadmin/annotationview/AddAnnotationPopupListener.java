package gui.sysadmin.annotationview;

import gui.sysadmin.SysadminController;
import gui.sysadmin.SysadminTab;
import gui.sysadmin.strings.SysStrings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAnnotationPopupListener implements ActionListener {

    private SysadminTab sysTab;
    private SysadminController sysController;

    /**
     * The listener for actions within the AddAnnotationPopup
     *
     * @param sysTab
     *            is the SysadminTab which opened the popup
     */
    public AddAnnotationPopupListener(SysadminTab sysTab) {
        this.sysTab = sysTab;
        this.sysController = sysTab.getSysController();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case SysStrings.ANNOTATIONS_POPUP_CREATE_ANNO:
                System.out.println("Creating new annotation....");
                sysController.sendNewAnnotation();
                break;

        }
    }
}
