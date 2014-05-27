package gui.sysadmin.genomereleaseview;

import gui.sysadmin.SysadminController;
import gui.sysadmin.SysadminTab;
import gui.sysadmin.strings.SysStrings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by dv12ilr on 2014-05-16.
 */
public class GenomeButtonListener implements ActionListener{

    SysadminTab sysTab;
    SysadminController sysController;

    public GenomeButtonListener(SysadminTab sysTab){
        this.sysTab = sysTab;
        this.sysController = sysTab.getSysController();
    }

    @Override public void actionPerformed(ActionEvent actionEvent) {
        switch(actionEvent.getActionCommand()){
            case SysStrings.GENOME_BUTTON_ADD:
                sysController.addGenomeRelease();
                sysTab.getGenomeReleaseView().clearTextFields();
                sysTab.getGenomeReleaseView().updateFileProgressPanel();
                break;
            case SysStrings.GENOME_BUTTON_CLEAR:
                sysTab.getGenomeReleaseView().clearTextFields();
                sysTab.getGenomeReleaseView().updateFileProgressPanel();
                break;
            case SysStrings.GENOME_BUTTON_DELETE:
                new GenomeDeletePopup(sysTab);
                break;
            case SysStrings.GENOME_BUTTON_FILE:
                sysTab.getGenomeReleaseView().selectFile();
                sysTab.getGenomeReleaseView().updateFileProgressPanel();
                break;
            case SysStrings.GENOME_BUTTON_CLOSE:
                sysTab.getGenomeReleaseView().removeExtraInfoPanel();
                break;
        }
    }
}
