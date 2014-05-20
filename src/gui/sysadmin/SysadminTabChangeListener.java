package gui.sysadmin;

import gui.sysadmin.strings.SysStrings;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SysadminTabChangeListener implements ChangeListener {

    private SysadminController sysContoller;
    private String lastTab;


    public SysadminTabChangeListener(SysadminController sysContoller) {

        this.sysContoller = sysContoller;
        lastTab = SysStrings.ANNOTATIONS;
    }

    @Override
    public void stateChanged(ChangeEvent e) {

        JTabbedPane tab = (JTabbedPane) e.getSource();
        String tabName = tab.getTitleAt(tab.getSelectedIndex());


        switch (tabName) {

            case SysStrings.GENOME:

                System.out.println("Clicked the genome tab.");
                sysContoller.setGenomeReleaseTable();
                lastTab = SysStrings.GENOME;



                break;
            case SysStrings.ANNOTATIONS:
                System.out.println("Clicked the annotations tab.");

                if (lastTab.equals(SysStrings.ANNOTATIONS)) {

                } else {

                    sysContoller.updateAnnotationTable();

                    /**TODO PLEASE REMOVE ME, ONLY FOR TEST*/

                }

                /**
                 * TODO Make sure the annotations are fetched here instead of
                 * the main Controller.
                 */
                break;

        }

    }

}
