package gui.sysadmin;

import gui.sysadmin.annotationview.AnnotationButtonsListener;
import gui.sysadmin.annotationview.AnnotationPopupListener;
import gui.sysadmin.annotationview.AnnotationTableModel;
import gui.sysadmin.annotationview.EditAnnotationPopup;
import gui.sysadmin.annotationview.EditAnnotationPopupListener;
import gui.sysadmin.annotationview.SysadminAnnotationPopup;
import gui.sysadmin.genomereleaseview.GenomereleaseTableModel;

import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.GenomizerModel;
import util.AnnotationDataType;
import util.GenomeReleaseData;

public class SysadminController extends Observable {

    private SysadminTab sysTab;
    private GenomizerModel model;

    public SysadminController(Observer observer) {

        this.addObserver(observer);

    }

    public SysadminController(GenomizerModel model) {
        this.model = model;
    }

    public ActionListener createAnnotationButtonListener() {
        return new AnnotationButtonsListener(sysTab);
    }

    public ActionListener createAnnotationPopupListener() {
        return new AnnotationPopupListener(sysTab);
    }

    public ActionListener createEditAnnotationPopupListener() {
        return new EditAnnotationPopupListener(sysTab);
    }

    /* You need me */
    public void setSysadminPanel(SysadminTab sysTab) {

        this.sysTab = sysTab;

    }

    public void sendNewAnnotation() {
        SysadminAnnotationPopup popup = sysTab.getAnnotationsView().getPop();
        try {
            model.addNewAnnotation(popup.getNewAnnotationName(),
                    popup.getNewAnnotationCategories(),
                    popup.getNewAnnotationForcedValue());
            updateAnnotationTable();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void editAnnotation() {
        System.out.println("Calling editAnnotation in syscontroller");
        EditAnnotationPopup edPop = sysTab.getAnnotationsView().getEditPopup();
        try {
            System.out.println("Trying to ask model to please edit an annotation...");
            model.editAnnotation(edPop.getNewAnnotationName(),
                    edPop.getNewAnnotationCategories(),
                    edPop.getNewAnnotationForcedValue(),
                    edPop.getAnnotation());

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public util.AnnotationDataType[] getAnnotations() {
        return model.getAnnotations();
    }



    public void deleteAnnotation() {

        if (sysTab.getAnnotationTable().getSelectedRow() != -1) {
            int row = sysTab.getAnnotationTable().getSelectedRow();
            row = sysTab.getAnnotationTable().convertRowIndexToModel(row);
            int col = 3;
            AnnotationDataType annotation = (AnnotationDataType) sysTab
                    .getAnnotationTable().getModel().getValueAt(row, col);

            try {
                if (JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete the "
                                + annotation.name + " annotation?") == JOptionPane.YES_OPTION) {
                    if (model.deleteAnnotation(annotation.name)) {
                        JOptionPane.showMessageDialog(null, annotation.name
                                + " has been remove!");
                        SwingUtilities.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                updateAnnotationTable();
                            }
                        });
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Could not remove annotation");
                    }
                }
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } else {
            System.out.println("Please select an annotation to delete");
        }
    }

    public util.GenomeReleaseData[] getGenomeReleases() {

        GenomeReleaseData[] grdarray = null;

        try {
            // grdarray = model.getGenomeReleases();
            /** TODO Implement me.... */
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        /** TODO Fix this */
        /********************* EXAMPLE DATA ONLY ****************************/
        GenomeReleaseData gr1 = new GenomeReleaseData("version1", "dolphin",
                "filename.txt");

        GenomeReleaseData gr2 = new GenomeReleaseData("version2", "pig",
                "bfilename.txt");

        GenomeReleaseData gr3 = new GenomeReleaseData("version3", "zebra",
                "afilename.txt");

        GenomeReleaseData gr4 = new GenomeReleaseData("version 1337",
                "unicorn", "nyancat.song");

        GenomeReleaseData gr5 = new GenomeReleaseData("version 3333",
                "nyancat", "mimimimimi");

        GenomeReleaseData gr6 = new GenomeReleaseData("version 4333",
                "nyancat", "wiOOiiOO");

        GenomeReleaseData gr7 = new GenomeReleaseData("version 5333",
                "unicorn", "coffee icecream");

        GenomeReleaseData gr8 = new GenomeReleaseData("version 666", "devil",
                "burn.txt");

        grdarray = new GenomeReleaseData[8];
        grdarray[0] = gr1;
        grdarray[1] = gr2;
        grdarray[2] = gr3;
        grdarray[3] = gr4;
        grdarray[4] = gr5;
        grdarray[5] = gr6;
        grdarray[6] = gr7;
        grdarray[7] = gr8;

        /*******************************************************************/

        return grdarray;

    }

    public void updateAnnotationTable() {
        AnnotationTableModel tableModel = (AnnotationTableModel) sysTab
                .getAnnotationsView().getTableModel();
        tableModel.setAnnotations(getAnnotations());
    }

    public void setGenomeReleaseTable() {

        GenomereleaseTableModel tableModel = (GenomereleaseTableModel) sysTab
                .getGenomeReleaseTableModel();

        tableModel.setGenomeReleases(getGenomeReleases());
    }

}
