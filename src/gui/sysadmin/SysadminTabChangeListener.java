package gui.sysadmin;

import gui.sysadmin.strings.SysStrings;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SysadminTabChangeListener implements ChangeListener {


    public SysadminTabChangeListener() {

    }

    @Override
    public void stateChanged(ChangeEvent e) {

        JTabbedPane tab = (JTabbedPane) e.getSource();
        String tabName = tab.getTitleAt(tab.getSelectedIndex());


        switch (tabName) {

            case SysStrings.GENOME:

                System.out.println("Clicked the genome tab.");

                break;
            case SysStrings.ANNOTATIONS:
                System.out.println("Clicked the annotations tab.");
                break;

        }

    }

}
