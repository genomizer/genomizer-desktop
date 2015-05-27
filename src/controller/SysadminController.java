package controller;

import gui.ErrorDialog;
import gui.sysadmin.SysadminTab;
import gui.sysadmin.annotationview.AddAnnotationPopup;
import gui.sysadmin.annotationview.AnnotationButtonsListener;
import gui.sysadmin.annotationview.AnnotationTableModel;
import gui.sysadmin.genomereleaseview.GenomeReleaseViewCreator;
import gui.sysadmin.genomereleaseview.GenomereleaseTableModel;
import gui.sysadmin.usersview.CreateUserButtonListener;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.ErrorLogger;
import model.GenomizerModel;
import util.AnnotationDataType;
import util.GenomeReleaseData;
import util.RequestException;

import communication.HTTPURLUpload;

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
     * Creates a listener for the buttons in the sysadmin tab.
     *
     * @return a new AnnotationButtonsListener
     */
    public ActionListener createUserButtonListener() {
        return new CreateUserButtonListener(sysTab);
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
        } catch (Exception e) {
            ErrorLogger.log(e);
            new ErrorDialog("Couldn't add annotation", e).showDialog();
        }
    }

    /**
     * @return the list of current annotations from the database
     * @throws RequestException
     */
    public util.AnnotationDataType[] getAnnotations() {
        return model.getAnnotations();
    }

    /**
     * @return a string array with the values of the "species"-annotation.
     * @throws RequestException
     */
    public String[] getSpecies() {

        AnnotationDataType[] annotations = getAnnotations();

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

            if (JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete the " + annotation.name
                            + " annotation?", "Remove annotation",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
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

                } catch (RequestException e) {
                    ErrorLogger.log(e);
                    new ErrorDialog("Couldn't delete annotation", e)
                            .showDialog();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No annotation selected!");
        }
    }

    public util.GenomeReleaseData[] getGenomeReleases() {
        return model.getGenomeReleases();
    }

    public void deleteGenomeRelease(String version, String specie) {
        try {
            model.deleteGenomeRelease(specie, version);
            updateGenomeReleaseTable();
        } catch (RequestException e) {
            new ErrorDialog("Couldn't delete genome release", e).showDialog();
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
        try {
            model.addGenomeReleaseFile(gr.getFilenames(), gr.getSpeciesItem(),
                    gr.getVersionText());
            updateGenomeReleaseTable();
            JOptionPane.showMessageDialog(null,
                    "Added genom release " + gr.getVersionText()
                            + " for species " + gr.getSpeciesItem());
        } catch (IllegalArgumentException e) {
            // TODO: Consider statusPanel, and make messages similar
            JOptionPane.showMessageDialog(null, "Could not add genome release");
        } catch (RequestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
     * @throws RequestException
     */
    public void renameAnnotationField(String oldName, String newName)
            throws RequestException {
        model.renameAnnotationField(oldName, newName);
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
    public void renameAnnotationValue(String name, String oldValue,
            String newValue) throws RequestException {
        // TODO: test renameing System.out.println(name + oldValue + newValue);
        model.renameAnnotationValue(name, oldValue, newValue);
    }

    /**
     * Sends a message to the model to remove an annotation value
     *
     * @param annotationName
     *            is the name of the annotation containing the value
     * @param annotationValue
     *            is the value to be removed
     * @return true if successfully removed, otherwise false
     * @throws RequestException
     */
    public void removeAnnotationValue(String annotationName,
            String annotationValue) throws RequestException {
        model.removeAnnotationValue(annotationName, annotationValue);
    }

    /**
     * Adds a value to an annotation
     *
     * @param annotationName
     *            the name of the annotation
     * @param valueName
     *            the name of the new value
     * @return true if successfully created, otherwise false
     * @throws RequestException
     */
    public void addAnnotationValue(String annotationName, String valueName)
            throws RequestException {
        model.addNewAnnotationValue(annotationName, valueName);
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


    public void createNewUser(String uName, String pass, String role, String rName, String mail){
        try {
            model.createUser(uName, pass, role, rName, mail);
        } catch (RequestException e) {
            // TODO Auto-generated catch block
            System.out.println("vill inte ha något!");
            e.printStackTrace();
        }
        // TODO
    }

    public void deleteUser(String uName){
        try {
            model.deleteUser(uName);
        } catch (RequestException e) {
            // TODO Auto-generated catch block
            System.out.println("vill inte ha något!");
            e.printStackTrace();
        }
        // TODO
    }

}
