package controller;

import gui.sysadmin.strings.SysStrings;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * @author oi11ahn
 * @author dv12ilr
 * */

/**
 * @author oi11ahn
 * 
 */

public class SysadminTabChangeListener implements ChangeListener {

    private SysadminController sysContoller;
    private String lastTab;

    public SysadminTabChangeListener(SysadminController sysContoller) {

        this.sysContoller = sysContoller;
        lastTab = SysStrings.ANNOTATIONS;
    }

    /**
     * The listener responding the when a new tab i chosen in the Sysadmin view
     * in the GUI.
     * 
     * @param e
     *            The ChangeEvent that triggered the listner.
     * */

    public void stateChanged(ChangeEvent e) {

        /** Get the source and the name of the tab. */

        JTabbedPane tab = (JTabbedPane) e.getSource();
        String tabName = tab.getTitleAt(tab.getSelectedIndex());

        switch (tabName) {

            case SysStrings.GENOME:
                new Thread() {
                    public void run() {
                        sysContoller.getGenomeReleases();

                        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                            public void run() {

                                sysContoller.setGenomeReleaseTable();

                            }
                        });

                        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                            public void run() {

                                sysContoller.getSysTab().getGenomeReleaseView()
                                        .setSpeciesDDList(sysContoller.getSpecies());
                            }
                        });

                        lastTab = SysStrings.GENOME;
                    }
                }.start();


                break;
            case SysStrings.ANNOTATIONS:
                if (lastTab.equals(SysStrings.ANNOTATIONS)) {

                } else {

                    sysContoller.updateAnnotationTable();

                    /** TODO PLEASE REMOVE ME, ONLY FOR TEST */

                }

                /**
                 * TODO Make sure the annotations are fetched here instead of
                 * the main Controller.
                 */
                break;

        }

    }

}
