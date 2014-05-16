package gui.sysadmin.genomereleaseview;

import com.sun.org.apache.xpath.internal.SourceTree;
import gui.sysadmin.SysadminController;
import gui.sysadmin.SysadminTab;
import gui.sysadmin.strings.SysStrings;

import javax.swing.*;
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
                System.out.println("Add");
                sysController.sendNewGenomeRelease();
                sysTab.getGenomeReleaseView().clearTextFields();
                break;
            case SysStrings.GENOME_BUTTON_CLEAR:
                sysController.clearAddGenomeText();
                break;
            case SysStrings.GENOME_BUTTON_DELETE:
                new GenomeDeletePopup();
                System.out.println("Delete");
                break;
            case SysStrings.GENOME_BUTTON_FILE:
                sysTab.getGenomeReleaseView().selectFile();
                System.out.println("File");
                break;
        }
    }
}
