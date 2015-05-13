package controller;

import gui.sysadmin.SysadminTab;
import gui.sysadmin.annotationview.AddAnnotationPopup;
import gui.sysadmin.annotationview.AnnotationButtonsListener;
import gui.sysadmin.annotationview.AnnotationTableModel;
import gui.sysadmin.genomereleaseview.GenomeReleaseViewCreator;
import gui.sysadmin.genomereleaseview.GenomereleaseTableModel;
import gui.sysadmin.strings.SysStrings;

import java.awt.event.ActionListener;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import communication.HTTPURLUpload;

import model.DeleteAnnotationException;
import model.ErrorLogger;
import model.GenomizerModel;
import util.AnnotationDataType;
import util.GenomeReleaseData;

/**
 * The controller for the admin part of the program.
 */
public class SysadminController {

    private SysadminTab sysTab;
    private GenomizerModel model;

    public SysadminController() {

    }

    /**
     * Constructs a controller for the admin part of the program
     *
     * @param model
     *            is the model with which the controller communicates
     */
    public SysadminController(GenomizerModel model) {
        this.model = model;
    }

    /**
     * Creates a listener for the buttons in the sysadmin tab.
     *
     * @return a new AnnotationButtonsListener
     */
    public ActionListener createAnnotationButtonListener() {
        return new AnnotationButtonsListener(sysTab);
    }

    /**
     * Sets the tab which the controller is connected to
     *
     * @param sysTab
     */
    public void setSysadminPanel(SysadminTab sysTab) {

        this.sysTab = sysTab;

    }

    /**
     * Sends a message to the model to make a new annotation.
     */
    public void sendNewAnnotation() {
        AddAnnotationPopup popup = sysTab.getPop();
        try {
            model.addNewAnnotation(popup.getNewAnnotationName(),
                    popup.getNewAnnotationCategories(),
                    popup.getNewAnnotationForcedValue());
            updateAnnotationTable();
        } catch (IllegalArgumentException e) {
            ErrorLogger.log(e);
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * @return the list of current annotations from the database
     */
    public util.AnnotationDataType[] getAnnotations() {
        return model.getAnnotations();
    }

    /**
     * @return a string array with the values of the "species"-annotation.
     */
    public String[] getSpecies() {

        AnnotationDataType[] annotations = model.getAnnotations();

        for (AnnotationDataType a : annotations) {

            if (a.getName().equals("Species")) {

                return a.getValues();
            }
        }

        return null;
    }

    /**
     * Removes the annotation currently highlighted in the annotation table. If
     * no annotation is selected, an error message will be shown.
     */
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
                    try {
                        model.deleteAnnotation(annotation.name);
                        JOptionPane.showMessageDialog(null, annotation.name
                                + " has been removed!");
                        SwingUtilities.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                updateAnnotationTable();
                            }
                        });

                    } catch (DeleteAnnotationException e) {
                        ErrorLogger.log(e);
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                }
            } catch (IllegalArgumentException e) {
                ErrorLogger.log(e);
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No annotation selected!");
        }
    }

    public util.GenomeReleaseData[] getGenomeReleases() {

        GenomeReleaseData[] grdarray = null;
        // TODO Behövs felmeddelandet? Det poppar upp när man loggar ut.
        // mycket underligt
        try {
            grdarray = model.getGenomeReleases();
            if (!(grdarray == null)) {
                if (grdarray.length == 0) {
                    // JOptionPane.showMessageDialog(null,
                    // "Could not get genomereleases!");
                }
            }

        } catch (IllegalArgumentException e) {
            ErrorLogger.log(e);
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return grdarray;

    }

    public void deleteGenomeRelease(String version, String specie) {

        if (model.deleteGenomeRelease(specie, version)) {
            updateGenomeReleaseTable();
        }
    }

    /**
     * Updates the table model of the table containing the current annotations.
     */
    public void updateAnnotationTable() {
        new Thread() {
            public void run() {
                final AnnotationDataType[] grs = getAnnotations();

                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        ((AnnotationTableModel) sysTab.getAnnotationsView()
                                .getTableModel()).setAnnotations(grs);
                    }
                });
            }
        }.start();
    }

    public void updateGenomeReleaseTable() {

        new Thread() {
            public void run() {
                final GenomeReleaseData[] grs = getGenomeReleases();

                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        ((GenomereleaseTableModel) sysTab
                                .getGenomeReleaseTableModel())
                                .setGenomeReleases(grs);
                    }
                });
            }
        }.start();
    }

    public void updateGenomeReleaseTab() {
        // TODO Beh�vs dessa tr�dar och runnable i invokeLater? CF
        new Thread() {
            public void run() {
                // sysController.getGenomeReleases();

                updateGenomeReleaseTable();

                final String[] species = getSpecies();
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        getSysTab().getGenomeReleaseView().setSpeciesDDList(
                                species);
                    }
                });

            }
        }.start();
    }

    public void addGenomeRelease() {
        GenomeReleaseViewCreator gr = sysTab.getGenomeReleaseView();
        if (model.addGenomeReleaseFile(gr.getFilenames(), gr.getSpeciesItem(),
                gr.getVersionText())) {

            updateGenomeReleaseTable();
            JOptionPane.showMessageDialog(null,
                    "Added genom release " + gr.getVersionText()
                            + " for species " + gr.getSpeciesItem());
            // TODO: Consider statusPanel, and make messages similar
        } else {
            JOptionPane.showMessageDialog(null, "Could not add genome release");
        }
    }

    public void clearAddGenomeText() {
        GenomeReleaseViewCreator gr = sysTab.getGenomeReleaseView();
        gr.clearTextFields();
    }

    /**
     * Sends a message to the model to rename an annotation
     *
     * @param oldName
     *            is the annotation to be renamed
     * @param newName
     *            is the new name
     * @return true if successfully renamed, otherwise false
     */
    public boolean renameAnnotationField(String oldName, String newName) {
        return (model.renameAnnotationField(oldName, newName));
    }

    /**
     * Sends a message to the model to rename an annotation value
     *
     * @param name
     *            is the name of the annotation
     * @param oldValue
     *            is the name of the annotation value to be renamed
     * @param newValue
     *            is the new name for the value
     * @return true if successfully renamed, otherwise false
     */
    public boolean renameAnnotationValue(String name, String oldValue,
            String newValue) {
        System.out.println(name + oldValue + newValue);
        return model.renameAnnotationValue(name, oldValue, newValue);

    }

    /**
     * Sends a message to the model to remove an annotation value
     *
     * @param annotationName
     *            is the name of the annotation containing the value
     * @param annotationValue
     *            is the value to be removed
     * @return true if successfully removed, otherwise false
     */
    public boolean removeAnnotationValue(String annotationName,
            String annotationValue) {
        return model.removeAnnotationValue(annotationName, annotationValue);

    }

    /**
     * Adds a value to an annotation
     *
     * @param annotationName
     *            the name of the annotation
     * @param valueName
     *            the name of the new value
     * @return true if successfully created, otherwise false
     */
    public boolean addAnnotationValue(String annotationName, String valueName) {
        return model.addNewAnnotationValue(annotationName, valueName);
    }

    /**
     * @return the SysadminTab connected to the controller
     */
    public SysadminTab getSysTab() {
        return sysTab;
    }

    public boolean addGenomRelease() {
        return model.addGenomeRelease();
    }

    /**
     * Start a new thread updating the genome release view every 100ms.
     */
    public void uploadGenomeReleaseProgress() {
        new Thread(new Runnable() {
            private boolean running;

            @Override
            public void run() {
                running = true;
                while (running) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            CopyOnWriteArrayList<HTTPURLUpload> ongoingUploads = model
                                    .getOngoingUploads();
                            running = sysTab.getGenomeReleaseView()
                                    .updateUploadProgress(ongoingUploads);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        ErrorLogger.log(e);
                        running = false;
                    }
                }
            }
        }).start();

    }
}
