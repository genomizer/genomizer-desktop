package gui.sysadmin;

import gui.sysadmin.annotationview.AddAnnotationPopup;
import gui.sysadmin.annotationview.AnnotationButtonsListener;
import gui.sysadmin.annotationview.AnnotationTableModel;
import gui.sysadmin.annotationview.EditAnnotationPopup2;
import gui.sysadmin.genomereleaseview.GenomeReleaseViewCreator;
import gui.sysadmin.genomereleaseview.GenomereleaseTableModel;

import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.GenomizerModel;
import util.AnnotationDataType;
import util.GenomeReleaseData;

public class SysadminController {

    private SysadminTab sysTab;
    private GenomizerModel model;

    public SysadminController() {

    }

    public SysadminController(GenomizerModel model) {
        this.model = model;
    }

    public ActionListener createAnnotationButtonListener() {
        return new AnnotationButtonsListener(sysTab);
    }

    /* You need me */
    public void setSysadminPanel(SysadminTab sysTab) {

        this.sysTab = sysTab;

    }

    public void sendNewAnnotation() {
        AddAnnotationPopup popup = sysTab.getPop();
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
        EditAnnotationPopup2 edPop = sysTab.getEditPopup(); // TODO: START
                                                            // HERE!!!!!
        AnnotationDataType oldAnnotation = edPop.getAnnotation();
        AnnotationDataType newAnnotation = new AnnotationDataType(
                edPop.getNewAnnotationName(),
                edPop.getNewAnnotationCategories(),
                edPop.getNewAnnotationForcedValue());

        if (!(oldAnnotation.name.equals(newAnnotation.name))) {
            System.out
                    .println("Name has been changed! Calling renameAnnotationField!");
            // model.renameAnnotationField(oldAnnotation.name,
            // newAnnotation.name);
        } else {
            System.out.println("No changes were made in name!");
        }

        if (!(oldAnnotation.isForced() == newAnnotation.isForced())) {
            System.out
                    .println("Forced value changed! Calling changeAnnotationForced (?)");
            // model.changeAnnotationForced(name);
        } else {
            System.out.println("Forced value not changed");
        }
        System.out.println("There are " + newAnnotation.getValues().length
                + " new values");
        System.out.println("There are " + oldAnnotation.getValues().length
                + " old values");
        if (newAnnotation.getValues().length > oldAnnotation.getValues().length) {
            System.out.println("New value(s) added to " + oldAnnotation.name
                    + "!");
            // model.addAnnotationValue(name, valueName);
        }

        if (newAnnotation.getValues().length < oldAnnotation.getValues().length) {
            System.out.println("Value removed from " + oldAnnotation.name);
            // model.removeAnnotationValue(name, valueName);
        }

    }

    public util.AnnotationDataType[] getAnnotations() {
        return model.getAnnotations();
    }


    public String[] getSpecies() {

        AnnotationDataType[] annotations = model.getAnnotations();

        for (AnnotationDataType a : annotations) {

            if (a.getName().equals("Species")) {

                System.out.println("FOUND SPECIES!");
                return a.getValues();
            }
        }

        return null;
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
                                + annotation.name + " annotation?",
                        "Remove annotation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (model.deleteAnnotation(annotation.name)) {
                        JOptionPane.showMessageDialog(null, annotation.name
                                + " has been removed!");
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
            grdarray = model.getGenomeReleases();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return grdarray;

    }

    public void deleteGenomeRelease(String version, String specie) {

        if (model.deleteGenomeRelease(specie, version)) {
            setGenomeReleaseTable();
        }
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

    public void sendNewGenomeRelease() {
        GenomeReleaseViewCreator gr = sysTab.getGenomeReleaseView();
        model.uploadGenomeReleaseFile(gr.getFileText(), gr.getSpeciesText(),
                gr.getVersionText());
        setGenomeReleaseTable();
    }

    public void clearAddGenomeText() {
        GenomeReleaseViewCreator gr = sysTab.getGenomeReleaseView();
        gr.clearTextFields();
    }

    public boolean  renameAnnotationField(String oldName, String newName) {
        if (model.renameAnnotationField(oldName, newName)){
            return true;
        } else {
            return false;
        }

    }

    public void renameAnnotationValue(String name, String oldValue,
            String newValue) {
        model.renameAnnotationValue(name, oldValue, newValue);

    }

    public void removeAnnotationValue(String annotationName, String annotationValue) {
        model.removeAnnotationValue(annotationName, annotationValue);

    }

    public void addAnnotationValue(String annotationName, String valueName) {
        model.addNewAnnotationValue(annotationName, valueName);
    }
    
    public SysadminTab getSysTab() {
        return sysTab;
    }
}
