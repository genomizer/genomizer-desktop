package gui.sysadmin;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SysadminSideTabsListener implements ChangeListener {
    
    private JTabbedPane sysadminTabPane;
    private SysadminTab sysTab;
    
    public SysadminSideTabsListener(JTabbedPane sysadminTabPane,
            SysadminTab sysTab) {
        
        this.sysadminTabPane = sysadminTabPane;
        this.sysTab = sysTab;
        sysadminTabPane.addChangeListener(this);
        
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        
        switch (sysadminTabPane.getTitleAt(sysadminTabPane.getSelectedIndex())) {
            case ButtonNames.ANNOTATIONS:
                System.out.println("something");
                break;
            case ButtonNames.USERS:
                System.out.println("asd");
                break;
            case ButtonNames.TEST:
                break;
        }
        
    }
    
}
