package gui.sysadmin;

import gui.sysadmin.strings.SysStrings;

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
        case SysStrings.ANNOTATIONS:
            break;
        case SysStrings.USERS:
            break;
        }

    }

}
