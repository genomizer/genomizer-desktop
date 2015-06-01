package controller;

import gui.ErrorDialog;
import gui.GUI;
import gui.UploadFileRow;
import gui.UploadTab;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.ErrorLogger;
import model.GenomizerModel;
import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.GenomeReleaseData;
import util.RequestException;

import communication.HTTPURLUpload;

public class UploadTabController {
    private final GenomizerModel model;
    private final UploadTab uploadTab;
    private final JFileChooser fileChooser;
    GUI view;

    public UploadTabController(GUI view, GenomizerModel model,
            JFileChooser fileChooser) {
        this.view = view;
        this.model = model;
        this.fileChooser = fileChooser;
        this.uploadTab = view.getUploadTab();

        uploadTab
                .addAddToExistingExpButtonListener(AddToExistingExpButtonListener());
        uploadTab.addNewExpButtonListener(NewExpButtonListener());
        uploadTab.getNewExpPanel().addSelectButtonListener(
                SelectFilesToNewExpListener());
        uploadTab.getNewExpPanel().addUploadButtonListener(uploadExpListener());
        uploadTab.getNewExpPanel().addUploadSelectedFilesListener(
                uploadSelectedFilesListener());
        uploadTab.getNewExpPanel().addSpeciesSelectedListener(
                SpeciesSelectedListener());
        updateProgress();
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
                        uploadTab.getNewExpPanel().setSelectButtonEnabled(true);
                        AnnotationDataType[] annotations = model
                                .getAnnotations();
                        uploadTab.addNewExpPanel(annotations);
                    };
                }.start();
            }
        };
    }

    public ActionListener uploadExpListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        JButton newExpButton = uploadTab.getNewExpButton();
                        newExpButton.setEnabled(false);
                        // uploadTab
                        String expName = uploadTab.getNewExpPanel()
                                .getNewExpID();
                        AnnotationDataValue[] annotations = uploadTab
                                .getNewExpPanel().getUploadAnnotations();

                        ArrayList<File> files = uploadTab.getNewExpPanel()
                                .getUploadFiles();

                        HashMap<String, String> types = uploadTab
                                .getNewExpPanel().getTypes();

                        try {
                            if (uploadTab.getUploadToNewExpPanel()
                                    .getIsNewExp()) {
                                model.addNewExperiment(expName, annotations);
                            } else {
                                // TODO Ska anv�ndas n�r edit annot
                                // implementerats
                                model.changeExperiment(expName, annotations);
                            }

                            uploadTab.getUploadToNewExpPanel()
                                    .setSelectButtonEnabled(false);
                            uploadTab.getUploadToNewExpPanel()
                                    .enableUploadButton(false);
                            uploadTab.setFileRowsEnabled(false);
                            int count = 0;
                            for (File f : files) {

                                model.uploadFile(expName, f,
                                        types.get(f.getName()), false,
                                        uploadTab.getGenomeVersion(f));
                                uploadTab.getNewExpPanel().deleteFileRow(f);
                                for (HTTPURLUpload upload : model
                                        .getOngoingUploads()) {
                                    if (f.getName()
                                            .equals(upload.getFileName())) {
                                        model.getOngoingUploads()
                                                .remove(upload);
                                    }
                                }
                                count++;
                                view.setStatusPanel("Upload " + count + "/"
                                        + files.size() + " done.");
                                // TODO: Decide whether to refresh this
                                // view part -
                                // view.getQuerySearchTab().refresh();

                                // TODO Beh�vs nog inte
                            }
                            uploadTab.getUploadToNewExpPanel()
                                    .enableUploadButton(true);
                            uploadTab.getUploadToNewExpPanel()
                            .setSelectButtonEnabled(true);
                            uploadTab.setFileRowsEnabled(true);
                            String status = "Changes to experiment \"" + expName
                                    + "\" completed.";
                            view.setStatusPanel(status);
                            view.setStatusPanelColor("success");
                        } catch (RequestException e) {
                            new ErrorDialog(
                                    "Could not upload file to experiment", e)
                                    .showDialog();
                        } catch (IllegalArgumentException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        newExpButton.setEnabled(true);
                    };
                }.start();
            }
        };
    }

    public ActionListener uploadSelectedFilesListener() {
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

                            try {
                                model.addNewExperiment(expName, annotations);
                                uploadTab.getUploadToNewExpPanel()
                                        .setSelectButtonEnabled(false);
                                uploadTab.setFileRowsEnabled(false);
                                uploadTab.getUploadToNewExpPanel()
                                        .enableUploadButton(false);
                                int count = 0;
                                for (File f : files) {
                                    model.uploadFile(expName, f,
                                            types.get(f.getName()), false,
                                            uploadTab.getGenomeVersion(f));
                                    uploadTab.getNewExpPanel().deleteFileRow(f);

                                    for (HTTPURLUpload upload : model
                                            .getOngoingUploads()) {
                                        if (f.getName().equals(
                                                upload.getFileName())) {
                                            model.getOngoingUploads().remove(
                                                    upload);
                                        }
                                    }
                                    count++;
                                    view.setStatusPanel("Upload " + count + "/"
                                            + files.size() + " done.");
                                }
                                // Shown when all files have been uploaded to
                                // experiment.
                                uploadTab.getUploadToNewExpPanel()
                                        .enableUploadButton(true);
                                uploadTab.getUploadToNewExpPanel()
                                .setSelectButtonEnabled(true);
                                uploadTab.setFileRowsEnabled(true);
                                String status = "Upload to new experiment \""
                                        + expName + "\" complete.";
                                view.setStatusPanel(status);
                                view.setStatusPanelColor("success");
                            } catch (RequestException e) {
                                new ErrorDialog(
                                        "Could not upload file to experiment",
                                        e).showDialog();
                            } catch (IllegalArgumentException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
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

                        if (species.equals("")) {

                            uploadTab
                                    .setGenomeReleases(new GenomeReleaseData[] {});

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

            }
        };
        model.addTickingTask(task);
    }
}
