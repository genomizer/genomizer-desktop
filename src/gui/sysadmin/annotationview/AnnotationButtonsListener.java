package gui.sysadmin.annotationview;

import gui.sysadmin.SysadminTab;
import gui.sysadmin.strings.SysStrings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnnotationButtonsListener implements ActionListener {

    private SysadminTab sysTab;

    /**
     * The listener that handles actions within the "Annotations" view in the
     * SysadminTab
     *
     * @param sysTab
     *            is the SysadminTab connected to the listener
     */
    public AnnotationButtonsListener(SysadminTab sysTab) {
        this.sysTab = sysTab;
    }

    /**
     * The actionPerformed method checks and compares the ActionCommand of the
     * event and performs different actions depending on where the action
     * originated.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case SysStrings.ANNOTATIONS_ADD:
                System.out.println("add");
                sysTab.addAnnotationsPopup();
                break;
            case SysStrings.ANNOTATIONS_MODIFY:
                System.out.println("modify");
                sysTab.editAnnotationPopup();

                break;
            case SysStrings.ANNOTATIONS_DELETE:
                System.out.println("delete");
                sysTab.deleteAnnotation();
                break;

        }
    }

}
