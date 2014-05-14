package gui.sysadmin;

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
            case ButtonNames.ANNOTATIONS_ADD:
                System.out.println("add");
                sysTab.addAnnotationsPopup();
                break;
            case ButtonNames.ANNOTATIONS_MODIFY:
                System.out.println("modify");
                break;
            case ButtonNames.ANNOTATIONS_DELETE:
                System.out.println("delete");
                break;
        
        }
    }
    
}
