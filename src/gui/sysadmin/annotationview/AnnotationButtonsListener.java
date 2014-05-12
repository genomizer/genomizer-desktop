package gui.sysadmin.annotationview;

import gui.sysadmin.SysadminTab;
import gui.sysadmin.strings.SysStrings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnnotationButtonsListener implements ActionListener {

    private SysadminTab sysTab;

    public AnnotationButtonsListener(SysadminTab sysTab) {
        this.sysTab = sysTab;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case SysStrings.ANNOTATIONS_ADD:
                System.out.println("add");
                sysTab.addAnnotationsPopup();
                break;
            case SysStrings.ANNOTATIONS_MODIFY:
                System.out.println("modify");
                break;
            case SysStrings.ANNOTATIONS_DELETE:
                System.out.println("delete");
                sysTab.deleteAnnotation();
                break;

        }
    }

}
