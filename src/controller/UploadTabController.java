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
        view.addSelectFilesToUploadButtonListener(new SelectFilesToUploadButtonListener());
        view.addAddToExistingExpButtonListener(new AddToExistingExpButtonListener());
        view.addUploadToExperimentButtonListener(new UploadToExperimentButtonListener());
        view.addNewExpButtonListener(new NewExpButtonListener());
        view.addSelectButtonListener(new SelectFilesToNewExpListener());
        view.addUploadButtonListener(new UploadNewExpListener());
        view.setOngoingUploads(model.getOngoingUploads());
        view.addUploadSelectedFilesListener(new UploadSelectedFilesListener());
        view.addSpeciesSelectedListener(new SpeciesSelectedListener());
    }

    class SelectFilesToUploadButtonListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            new Thread(this).start();
        }

        @Override
        public void run() {
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
    }

    class AddToExistingExpButtonListener implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            new Thread(this).start();
        }

        @Override
        public void run() {
            UploadTab uploadTab = view.getUploadTab();
            String expID = uploadTab.getSearchText();
            if (expID.length() > 0) {
                try {
                    ExperimentData ed = model.retrieveExperiment(expID);
                    String species = null;
                    boolean existingSpecies = false;
                    for (AnnotationDataValue adv : ed.getAnnotations()) {
                        if (adv.getName().equalsIgnoreCase("species")) {
                            species = adv.getValue();
                            existingSpecies = true;
                        }

                    }
                    if (existingSpecies) {
                        uploadTab.addExistingExpPanel(ed,
                                model.getAnnotations());
                        GenomeReleaseData[] grd = model
                                .getSpeciesGenomeReleases(species);
                        view.setGenomeReleases(grd);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Missing species in experiment.", "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NullPointerException e) {
                    ErrorLogger.log(e);
                    JOptionPane.showMessageDialog(null,
                            "Couldn't find or retrieve experiment.", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Please fill in experiment name.", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class UploadToExperimentButtonListener implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            new Thread(this).start();
        }

        @Override
        public void run() {
            String expName = view.getNewExpName();
            AnnotationDataValue[] annotations = view.getUploadAnnotations();
            ArrayList<File> files = view.getUploadTab().getExistExpPanel()
                    .getFilesToUpload();
            HashMap<String, String> types = view.getUploadTab()
                    .getExistExpPanel().getTypes();
            // Should be genome release from uploadTab
            // String release = "wk1m";

            System.out.println(model.changeExperiment(expName, annotations));

            ExperimentData ed = view.getUploadTab().getExistExpPanel()
                    .getExperiment();

            for (File f : files) {
                if (model.uploadFile(ed.getName(), f, types.get(f.getName()),
                        view.getUsername(), false, view.getGenomeVersion(f))) {
                    view.getUploadTab().getExistExpPanel().deleteFileRow(f);
                    if (view.getUploadTab().getExistExpPanel().getFileRows()
                            .size() == 0) {
                        JOptionPane.showMessageDialog(null,
                                "Upload to experiment \"" + ed.getName()
                                        + "\" complete.");
                        view.refreshSearch();
                    }
                    for (HTTPURLUpload upload : model.getOngoingUploads()) {
                        if (f.getName().equals(upload.getFileName())) {
                            model.getOngoingUploads().remove(upload);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Upload of " + f.getName() + " failed.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    class NewExpButtonListener implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            new Thread(this).start();
        }

        @Override
        public void run() {
            AnnotationDataType[] annotations = model.getAnnotations();
            view.createNewExp(annotations);
        }
    }

    class SelectFilesToNewExpListener implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setMultiSelectionEnabled(true);
            int ret = fileChooser.showOpenDialog(new JPanel());
            File[] files;
            if (ret == JFileChooser.APPROVE_OPTION) {
                files = fileChooser.getSelectedFiles();
            } else {
                return;
            }
            view.selectFilesToNewExp(files);
            view.enableUploadButton(true);
        }
    }

    class UploadNewExpListener implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            boolean expCreated;
            String expName = view.getNewExpName();
            AnnotationDataValue[] annotations = view.getUploadAnnotations();
            ArrayList<File> files = view.getFilesToUpload();

            HashMap<String, String> types = view.getFilesToUploadTypes();
            // Should be genome release from uploadTab
            // String release = "wk1m";
            // Test purpose
            if (view.getIsNewExp()) {
                expCreated = model.addNewExperiment(expName, annotations);
            } else {
                expCreated = model.changeExperiment(expName, annotations);
                System.err.println("Ändrad " + expCreated);
                expCreated = true;
            }
            if (expCreated) {
                for (File f : files) {
                    view.disableSelectedRow(f);
                    if (model
                            .uploadFile(expName, f, types.get(f.getName()),
                                    view.getUsername(), false,
                                    view.getGenomeVersion(f))) {
                        view.deleteUploadFileRow(f);
                        for (HTTPURLUpload upload : model.getOngoingUploads()) {
                            if (f.getName().equals(upload.getFileName())) {
                                model.getOngoingUploads().remove(upload);
                            }
                        }
                        view.refreshSearch();
                    } else {
                        JOptionPane.showMessageDialog(null, "Couldn't upload "
                                + f.getName() + ".", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Couldn't create experiment " + expName + ".", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class UploadSelectedFilesListener implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            String expName = view.getNewExpName();
            AnnotationDataValue[] annotations = view.getUploadAnnotations();
            ArrayList<File> files = view.getSelectedFilesToUpload();
            if (files != null && files.size() > 0 && annotations != null
                    && expName != null) {
                HashMap<String, String> types = view.getFilesToUploadTypes();
                // Should be genome release from uploadTab
                // String release = "wk1m";
                // Test purpose
                boolean created = model.addNewExperiment(expName, annotations);
                if (created) {
                    for (File f : files) {
                        view.disableSelectedRow(f);
                        if (model.uploadFile(expName, f,
                                types.get(f.getName()), view.getUsername(),
                                false, view.getGenomeVersion(f))) {
                            view.deleteUploadFileRow(f);
                            for (HTTPURLUpload upload : model
                                    .getOngoingUploads()) {
                                if (f.getName().equals(upload.getFileName())) {
                                    model.getOngoingUploads().remove(upload);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Couldn't upload " + f.getName() + ".",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            ErrorLogger.log("Couldn't upload", "Upload");
                        }
                    }
                    // Shown when all files have been uploaded to experiment.
                    JOptionPane.showMessageDialog(null, "Upload to the new "
                            + "experiment \"" + expName + "\" complete");
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Couldn't create new experiment " + expName + ".",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No files selected.");
            }
        }
    }

    class SpeciesSelectedListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            String species = view.getSelectedSpecies();
            GenomeReleaseData[] grd = model.getSpeciesGenomeReleases(species);
            view.setGenomeReleases(grd);
        }
    }
}
