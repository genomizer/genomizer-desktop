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

    SysadminController sysController;

    public GenomeButtonListener(SysadminTab sysTab){
        this.sysController = sysTab.getSysController();
    }

    @Override public void actionPerformed(ActionEvent actionEvent) {
        switch(actionEvent.getActionCommand()){
            case SysStrings.GENOME_BUTTON_ADD:
                System.out.println("Add");
                sysController.sendNewGenomeRelease();
                break;
            case SysStrings.GENOME_BUTTON_CLEAR:
                sysController.clearAddGenomeText();
                break;
            case SysStrings.GENOME_BUTTON_DELETE:
                System.out.println("Delete");
                break;
            case SysStrings.GENOME_BUTTON_FILE:
                System.out.println("File");
                break;
        }
    }
}
