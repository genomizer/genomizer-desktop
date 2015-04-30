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
import gui.GenomizerView;
import gui.UploadTab;
import gui.UploadToExistingExpPanel;
import model.ErrorLogger;
import model.GenomizerModel;
import model.Model;

public class UploadTabController {
    GenomizerView view;
    GenomizerModel model;
    private final JFileChooser fileChooser;

    public UploadTabController(GenomizerView view, GenomizerModel model,
            JFileChooser fileChooser) {
        this.view = view;
        this.model = model;
        this.fileChooser = fileChooser;
        UploadTab uploadTab = view.getUploadTab();
        uploadTab.getExistExpPanel().addSelectFilesToUploadButtonListener(SelectFilesToUploadButtonListener());
        uploadTab.addAddToExistingExpButtonListener(AddToExistingExpButtonListener());
        uploadTab.getExistExpPanel().addUploadToExperimentButtonListener(UploadToExperimentButtonListener());
        uploadTab.addNewExpButtonListener(NewExpButtonListener());
        uploadTab.getNewExpPanel().addSelectButtonListener(SelectFilesToNewExpListener());
        uploadTab.getNewExpPanel().addUploadButtonListener(UploadNewExpListener());
        uploadTab.setOngoingUploads(model.getOngoingUploads());
        uploadTab.getNewExpPanel().addUploadSelectedFilesListener(UploadSelectedFilesListener());
        uploadTab.getNewExpPanel().addSpeciesSelectedListener(
                SpeciesSelectedListener());
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

                UploadToExistingExpPanel uploadToExistingExpPanel = view
                        .getUploadTab().getExistExpPanel();
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
                view.getUploadTab().getNewExpPanel().createUploadFileRow(files);
                view.getUploadTab().getNewExpPanel().enableUploadButton(true);
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
                        UploadTab uploadTab = view.getUploadTab();
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
                                    uploadTab.addExistingExpPanel(ed,model.getAnnotations());
                                    GenomeReleaseData[] grd = model
                                            .getSpeciesGenomeReleases(species);
                                    view.getUploadTab().setGenomeReleases(grd);
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
                        ArrayList<File> files = view.getUploadTab()
                                .getExistExpPanel().getFilesToUpload();
                        HashMap<String, String> types = view.getUploadTab()
                                .getExistExpPanel().getTypes();
                        // Should be genome release from uploadTab
                        // String release = "wk1m";

                        ExperimentData ed = view.getUploadTab()
                                .getExistExpPanel().getExperiment();

                        for (File f : files) {
                            if (model.uploadFile(ed.getName(), f,
                                    types.get(f.getName()), view.getLoginWindow().getUsernameInput(),
                                    false, view.getUploadTab().getGenomeVersion(f))) {
                                view.getUploadTab().getExistExpPanel()
                                        .deleteFileRow(f);
                                if (view.getUploadTab().getExistExpPanel()
                                        .getFileRows().size() == 0) {
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "Upload to experiment \""
                                                    + ed.getName()
                                                    + "\" complete.");
                                    view.getQuerySearchTab().refresh();
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
     * TODO: Threads, creates new panel from non-EDT.
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
                        view.getUploadTab().addNewExpPanel(annotations);
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
                        boolean expCreated;
                        String expName = view.getUploadTab().getNewExpPanel().getNewExpID();
                        AnnotationDataValue[] annotations = view.getUploadTab().getNewExpPanel().getUploadAnnotations();
                        ArrayList<File> files = view.getUploadTab().getNewExpPanel().getUploadFiles();
//                        if (files != null && files.size() > 0
//                                && annotations != null && expName != null) {
                            HashMap<String, String> types = view.getUploadTab().getNewExpPanel().getTypes();
                            // Should be genome release from uploadTab
                            // String release = "wk1m";
                            // Test purpose
                            if (view.getIsNewExp()) {
                                expCreated = model.addNewExperiment(expName, annotations);
                            } else {
                                expCreated = model.changeExperiment(expName, annotations);
                                System.err.println("Ändrad " + expCreated);
                            }
                            if (expCreated) {
                                for (File f : files) {
                                    view.disableSelectedRow(f);
                                    if (model.uploadFile(expName, f,
                                            types.get(f.getName()),
                                            view.getLoginWindow().getUsernameInput(), false,
                                            view.getUploadTab().getGenomeVersion(f))) {
                                        view.getUploadTab().getNewExpPanel().deleteFileRow(f);
                                        for (HTTPURLUpload upload : model
                                                .getOngoingUploads()) {
                                            if (f.getName().equals(
                                                    upload.getFileName())) {
                                                model.getOngoingUploads()
                                                        .remove(upload);
                                            }
                                        }
                                        view.getQuerySearchTab().refresh();
                                    } else {
                                        JOptionPane.showMessageDialog(
                                                null,
                                                "Couldn't upload "
                                                        + f.getName() + ".",
                                                "Error",
                                                JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
//                        }
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
                        String expName = view.getUploadTab().getNewExpPanel().getNewExpID();
                        AnnotationDataValue[] annotations = view.getUploadTab().getNewExpPanel().getUploadAnnotations();
                        ArrayList<File> files = view.getUploadTab().getNewExpPanel().getSelectedFilesToUpload();
                        if (files != null && files.size() > 0
                                && annotations != null && expName != null) {
                            HashMap<String, String> types = view.getUploadTab().getNewExpPanel().getTypes();
                            // Should be genome release from uploadTab
                            // String release = "wk1m";
                            // Test purpose
                            boolean created = model.addNewExperiment(expName,
                                    annotations);
                            if (created) {
                                for (File f : files) {
                                    view.disableSelectedRow(f);
                                    if (model.uploadFile(expName, f,
                                            types.get(f.getName()),
                                            view.getLoginWindow().getUsernameInput(), false,
                                            view.getUploadTab().getGenomeVersion(f))) {
                                        view.getUploadTab().getNewExpPanel().deleteFileRow(f);
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
                                JOptionPane.showMessageDialog(null,
                                        "Upload to the new " + "experiment \""
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
                        String species =  view.getUploadTab().getNewExpPanel().getSelectedSpecies();

                        // TODO: Thread, although connection here, should not
                        // below.
                        GenomeReleaseData[] grd = model
                                .getSpeciesGenomeReleases(species);

                        view.getUploadTab().setGenomeReleases(grd);
                    };
                }.start();
            }
        };
    }

}
