package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import communication.HTTPURLUpload;

import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.GenomeReleaseData;

import gui.ErrorDialog;
import gui.GUI;
import gui.UploadFileRow;
import gui.UploadTab;
import gui.UploadToExistingExpPanel;
import model.ErrorLogger;
import model.GenomizerModel;

public class UploadTabController {
    private final GenomizerModel model;
    private final UploadTab uploadTab;
    private final JFileChooser fileChooser;
    GUI view;
    

    public UploadTabController(GUI view, GenomizerModel model, JFileChooser fileChooser) {
        this.view = view;
        this.model = model;
        this.fileChooser = fileChooser;
        this.uploadTab = view.getUploadTab();



        uploadTab.getExistExpPanel().addSelectFilesToUploadButtonListener(
                SelectFilesToUploadButtonListener());
        uploadTab
                .addAddToExistingExpButtonListener(AddToExistingExpButtonListener());
        uploadTab.getExistExpPanel().addUploadToExperimentButtonListener(
                UploadToExperimentButtonListener());
        uploadTab.addNewExpButtonListener(NewExpButtonListener());
        uploadTab.getNewExpPanel().addSelectButtonListener(
                SelectFilesToNewExpListener());
        uploadTab.getNewExpPanel().addUploadButtonListener(
                UploadNewExpListener());
        uploadTab.getNewExpPanel().addUploadSelectedFilesListener(
                UploadSelectedFilesListener());
        uploadTab.getNewExpPanel().addSpeciesSelectedListener(
                SpeciesSelectedListener());



        updateProgress();
    }

    /**
     * Display a fileChooser, and let the user enter the files to upload. Used
     * for existingExp.
     *
     */
    public ActionListener SelectFilesToUploadButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setMultiSelectionEnabled(true);
                int ret = fileChooser.showOpenDialog(new JPanel());
                File[] files;
                if (ret == JFileChooser.APPROVE_OPTION) {
                    files = fileChooser.getSelectedFiles();
                } else {
                    return;
                }

                UploadToExistingExpPanel uploadToExistingExpPanel = uploadTab
                        .getExistExpPanel();
                uploadToExistingExpPanel.createUploadFileRow(files);
                uploadToExistingExpPanel.enableUploadButton();
                uploadToExistingExpPanel.addFileDrop();
            }
        };
    }

    /**
     * Display a fileChooser, and let the user enter the files to upload. Used
     * for NewExp. TODO: Same code as for oldexp?
     */
    public ActionListener SelectFilesToNewExpListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setMultiSelectionEnabled(true);
                int ret = fileChooser.showOpenDialog(new JPanel());
                File[] files;
                if (ret == JFileChooser.APPROVE_OPTION) {
                    files = fileChooser.getSelectedFiles();
                } else {
                    return;
                }
                uploadTab.getNewExpPanel().createUploadFileRow(files);
                uploadTab.getNewExpPanel().enableUploadButton(true);
            }
        };
    }

    public ActionListener AddToExistingExpButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        String expID = uploadTab.getSearchText();
                        if (expID.length() > 0) {
                            try {
                                ExperimentData ed = model
                                        .retrieveExperiment(expID);
                                String species = null;
                                boolean existingSpecies = false;
                                for (AnnotationDataValue adv : ed
                                        .getAnnotations()) {
                                    if (adv.getName().equalsIgnoreCase(
                                            "species")) {
                                        species = adv.getValue();
                                        existingSpecies = true;
                                    }

                                }
                                if (existingSpecies) {
                                    uploadTab.addExistingExpPanel(ed,
                                            model.getAnnotations());
                                    GenomeReleaseData[] grd = model
                                            .getSpeciesGenomeReleases(species);
                                    uploadTab.setGenomeReleases(grd);
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Missing species in experiment.",
                                            "ERROR", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (NullPointerException e) {
                                ErrorLogger.log(e);
                                JOptionPane
                                        .showMessageDialog(
                                                null,
                                                "Couldn't find or retrieve experiment.",
                                                "ERROR",
                                                JOptionPane.ERROR_MESSAGE);
                            }
                        } else {

                            JOptionPane.showMessageDialog(null,
                                    "Please fill in experiment name.", "ERROR",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    };
                }.start();
            }
        };
    }

    public ActionListener UploadToExperimentButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        ArrayList<File> files = uploadTab.getExistExpPanel()
                                .getFilesToUpload();
                        HashMap<String, String> types = uploadTab
                                .getExistExpPanel().getTypes();

                        ExperimentData ed = uploadTab.getExistExpPanel()
                                .getExperiment();

                        for (File f : files) {
                            if (model.uploadFile(ed.getName(), f,
                                    types.get(f.getName()), false,
                                    uploadTab.getGenomeVersion(f))) {
                                uploadTab.getExistExpPanel().deleteFileRow(f);
                                if (uploadTab.getExistExpPanel().getFileRows()
                                        .size() == 0) {
                                    String status = "Upload to experiment \"" + ed.getName() + "\" complete.";
                                    view.setStatusPanel(status);
                                    JOptionPane.showMessageDialog(
                                            null,status);
                                    
                                    // TODO: Decide whether to refresh this view
                                    // part -
                                    // view.getQuerySearchTab().refresh();
                                }
                                for (HTTPURLUpload upload : model
                                        .getOngoingUploads()) {
                                    if (f.getName()
                                            .equals(upload.getFileName())) {
                                        model.getOngoingUploads()
                                                .remove(upload);
                                    }
                                }
                            } else {
                                JOptionPane
                                        .showMessageDialog(null, "Upload of "
                                                + f.getName() + " failed.",
                                                "Error",
                                                JOptionPane.ERROR_MESSAGE);
                            }
                        }

                    };
                }.start();
            }
        };
    }

    /**
     * Get the annotations and create a new NewExp Panel with them.
     *
     * TODO: Threads, creates new panel from non-EDT, getAnnot is connectoin...
     */
    public ActionListener NewExpButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        AnnotationDataType[] annotations = model
                                .getAnnotations();
                        uploadTab.addNewExpPanel(annotations);
                    };
                }.start();
            }
        };
    }

    public ActionListener UploadNewExpListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        String expName = uploadTab.getNewExpPanel()
                                .getNewExpID();
                        AnnotationDataValue[] annotations = uploadTab
                                .getNewExpPanel().getUploadAnnotations();

                        ArrayList<File> files = uploadTab.getNewExpPanel()
                                .getUploadFiles();
                        // TODO: Now replaced your code bits, EXCEPT FOR
                        // REMOVED IF BIT -
                        // Did not dare to touch it since it looks
                        // implortants. Check later!
                        if (files != null && files.size() > 0
                                && annotations != null && expName != null) {

                            HashMap<String, String> types = uploadTab
                                    .getNewExpPanel().getTypes();
                            boolean created;

                            if (uploadTab.getUploadToNewExpPanel()
                                    .getIsNewExp()) {
                                created = model.addNewExperiment(expName,
                                        annotations);
                            } else {
                                created = model.changeExperiment(expName,
                                        annotations);
                            }

                            if (created) {
                                for (File f : files) {
                                    uploadTab.disableSelectedRow(f);
                                    if (model.uploadFile(expName, f,
                                            types.get(f.getName()), false,
                                            uploadTab.getGenomeVersion(f))) {
                                        uploadTab.getNewExpPanel()
                                                .deleteFileRow(f);
                                        for (HTTPURLUpload upload : model
                                                .getOngoingUploads()) {
                                            if (f.getName().equals(
                                                    upload.getFileName())) {
                                                model.getOngoingUploads()
                                                        .remove(upload);
                                            }
                                        }
                                        // TODO: Decide whether to refresh this
                                        // view part -
                                        // view.getQuerySearchTab().refresh();


                                    } else {

                                        JOptionPane.showMessageDialog(
                                                null,
                                                "Couldn't upload "
                                                        + f.getName() + ".",
                                                "Error",
                                                JOptionPane.ERROR_MESSAGE);

                                    }
                                }
                                
                                String status = "Upload to experiment \"" + expName + "\" complete.";
                                view.setStatusPanel(status);

                                //JOptionPane.showMessageDialog(null,status);
                                
                            } else {

//                                // TODO: Fix the error dialog?
//                                new ErrorDialog("Couldn't create experiment",
//                                        "The experiment " + expName
//                                                + " could not be created.",
//                                        "singdudeldej").showDialog();

                            }
                        }
                    };
                }.start();
            }
        };
    }

    public ActionListener UploadSelectedFilesListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        String expName = uploadTab.getNewExpPanel()
                                .getNewExpID();
                        AnnotationDataValue[] annotations = uploadTab
                                .getNewExpPanel().getUploadAnnotations();
                        ArrayList<File> files = uploadTab.getNewExpPanel()
                                .getSelectedFilesToUpload();
                        if (files != null && files.size() > 0
                                && annotations != null && expName != null) {
                            HashMap<String, String> types = uploadTab
                                    .getNewExpPanel().getTypes();
                            // Should be genome release from uploadTab
                            // String release = "wk1m";
                            // Test purpose
                            boolean created = model.addNewExperiment(expName,
                                    annotations);
                            if (created) {
                                for (File f : files) {
                                    uploadTab.disableSelectedRow(f);
                                    if (model.uploadFile(expName, f,
                                            types.get(f.getName()), false,
                                            uploadTab.getGenomeVersion(f))) {
                                        uploadTab.getNewExpPanel()
                                                .deleteFileRow(f);
                                        for (HTTPURLUpload upload : model
                                                .getOngoingUploads()) {
                                            if (f.getName().equals(
                                                    upload.getFileName())) {
                                                model.getOngoingUploads()
                                                        .remove(upload);
                                            }
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(
                                                null,
                                                "Couldn't upload "
                                                        + f.getName() + ".",
                                                "Error",
                                                JOptionPane.ERROR_MESSAGE);
                                        ErrorLogger.log("Couldn't upload",
                                                "Upload");
                                    }
                                }
                                // Shown when all files have been uploaded to
                                // experiment.
                                
                                JOptionPane.showMessageDialog(null,"Upload to the new " + "experiment \""
                                        + expName + "\" complete");
                                
                                
                                
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "Couldn't create new experiment "
                                                + expName + ".", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "No files selected.");
                        }

                    };
                }.start();
            }
        };
    }

    public ActionListener SpeciesSelectedListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {

                        String species = uploadTab.getNewExpPanel()
                                .getSelectedSpecies();

                        if (species.equals("")){

                            uploadTab.setGenomeReleases(new GenomeReleaseData[] {});

                            return;
                        }

                        // TODO: Thread, although connection here, should not
                        // below.
                        GenomeReleaseData[] grd = model
                                .getSpeciesGenomeReleases(species);

                        uploadTab.setGenomeReleases(grd);
                    };
                }.start();
            }
        };
    }

    /**
     * Method updating the progress of ongoing uploads. OO Now adding to the
     * ticker updater thread.
     */
    private void updateProgress() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                for (File key : uploadTab.getNewExpPanel().getFileRows()
                        .keySet()) {
                    UploadFileRow row = uploadTab.getNewExpPanel()
                            .getFileRows().get(key);
                    for (HTTPURLUpload upload : model.getOngoingUploads()) {
                        if (upload.getFileName().equals(row.getFileName())) {
                            row.updateProgressBar(upload.getCurrentProgress());
                        }
                    }
                }
                for (File key : uploadTab.getExistExpPanel().getFileRows()
                        .keySet()) {
                    UploadFileRow row = uploadTab.getExistExpPanel()
                            .getFileRows().get(key);
                    for (HTTPURLUpload upload : model.getOngoingUploads()) {
                        if (upload.getFileName().equals(row.getFileName())) {
                            row.updateProgressBar(upload.getCurrentProgress());
                        }
                    }
                }
            }
        };
        model.addTickingTask(task);
    }
}
