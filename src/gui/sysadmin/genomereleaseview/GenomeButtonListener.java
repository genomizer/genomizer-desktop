package gui.sysadmin.genomereleaseview;

import gui.ErrorDialog;
import gui.sysadmin.SysadminTab;
import gui.sysadmin.strings.SysStrings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import util.RequestException;

import controller.SysadminController;

/**
 * Created by dv12ilr on 2014-05-16.
 */
public class GenomeButtonListener implements ActionListener {

    SysadminTab sysTab;
    SysadminController sysController;

    public GenomeButtonListener(SysadminTab sysTab) {
        this.sysTab = sysTab;
        this.sysController = sysTab.getSysController();
    }

    /***
     * Receives the event, figures out what happened and acts accordingly.
     */
    public void actionPerformed(final ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case SysStrings.GENOME_BUTTON_UPLOAD:
                ((JButton) actionEvent.getSource()).setEnabled(false);
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        sysController.addGenomeRelease();
                        sysTab.getGenomeReleaseView().clearTextFields();
                        sysTab.getGenomeReleaseView().updateFileProgressPanel();
                        ((JButton) actionEvent.getSource()).setEnabled(true);
                    }
                }).start();
                sysController.uploadGenomeReleaseProgress();

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
            case SysStrings.GENOME_BUTTON_ADD_SPECIE:
                String specie = sysTab.getGenomeReleaseView().getSpecieText();
                if (specie != null && !specie.equals("")) {
                    try {
                        sysController.addAnnotationValue("Species", specie);
                        sysController.updateAnnotationTable();
                        sysController.updateGenomeReleaseTab();
                    } catch (RequestException e1) {
                        new ErrorDialog("Add Species Failure", e1).showDialog();
                    }
                }
                break;
        }
    }
}
