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
    
    private SysadminController sysController;
    private String lastTab;
    
    public SysadminTabChangeListener(SysadminController sysContoller) {
        
        this.sysController = sysContoller;
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
                sysController.updateGenomeReleaseTab();
                
                lastTab = SysStrings.GENOME;
                break;
            case SysStrings.ANNOTATIONS:
                
                sysController.updateAnnotationTable();
                lastTab = SysStrings.ANNOTATIONS;
                break;
        
        }
        
    }
    
}
