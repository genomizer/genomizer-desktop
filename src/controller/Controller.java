package controller;

import gui.CheckListItem;
import gui.DownloadWindow;
import gui.GenomizerView;
import gui.UploadTab;
import gui.UploadToExistingExpPanel;
import gui.sysadmin.SysadminController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.GenomizerModel;
import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.FileData;
import util.GenomeReleaseData;
import util.ProcessFeedbackData;

import communication.HTTPURLUpload;

public class Controller {

    private GenomizerView view;
    private GenomizerModel model;
    private final JFileChooser fileChooser = new JFileChooser();
    private SysadminController sysController;

    public Controller(GenomizerView view, GenomizerModel model) {
        this.view = view;
        this.model = model;
        view.addLoginListener(new LoginListener());
        view.addLogoutListener(new LogoutListener());
        view.addSearchListener(new QuerySearchListener());
        view.addConvertFileListener(new ConvertFileListener());
        view.addQuerySearchListener(new QuerySearchListener());
        view.addRawToProfileDataListener(new RawToProfileDataListener());
        view.addRawToRegionDataListener(new RawToRegionDataListener());
        view.addScheduleFileListener(new ScheduleFileListener());
        view.addDownloadFileListener(new DownloadWindowListener());
        view.addSelectFilesToUploadButtonListener(new SelectFilesToUploadButtonListener());
        view.setSysadminController(sysController = new SysadminController(model));
        view.addAddToExistingExpButtonListener(new AddToExistingExpButtonListener());
        view.addUploadToExperimentButtonListener(new UploadToExperimentButtonListener());
        view.addUpdateSearchAnnotationsListener(new updateSearchAnnotationsListener());
        view.addProcessFileListener(new ProcessFileListener());
        view.addSearchToWorkspaceListener(new SearchToWorkspaceListener());
        view.addNewExpButtonListener(new NewExpButtonListener());
        view.addSelectButtonListener(new SelectFilesToNewExpListener());
        view.addUploadButtonListener(new UploadNewExpListener());
        view.addAnalyzeSelectedListener(new AnalyzeSelectedListener());
        fileListAddMouseListener(view.getfileList());
        view.addRatioCalcListener(new RatioCalcListener());
        view.addProcessFeedbackListener(new ProcessFeedbackListener());
        view.addCancelListener(new CancelListener());
        view.addOkListener(new OkListener());
        view.addDeleteFromDatabaseListener(new DeleteFromDatabaseListener());
        view.setOngoingUploads(model.getOngoingUploads());
        view.addUploadSelectedFilesListener(new UploadSelectedFilesListener());
    }

    class ConvertFileListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {

            System.out.println("CONVERT");
            System.out.println(view.getAllMarkedFiles());

        }
    }

    /**
     * The listener to create profile data, Sends a request to the server for
     * every RAW-file that the user wants to create profile data.
     *
     * @author c11ann
     */
    class RawToProfileDataListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {

            view.setBowtieParameters();
            ArrayList<FileData> allMarked = view.getAllMarkedFileData();
            String message = null;
            Boolean isConverted = false;

            if(view.isCorrectToProcess()){
            if (!allMarked.isEmpty()) {

                for (FileData data : allMarked) {

                    String fileName = data.filename;
                    String fileID = data.id;
                    String author = view.getUsername();
                    String parameters[] = new String[8];
                    String processtype = "rawtoprofile";

                    parameters[0] = view.getParameters()[0];
                    parameters[1] = view.getParameters()[1];
                    parameters[2] = view.getOtherParameters()[0];// "y";
                    parameters[3] = view.getOtherParameters()[1];// "y";
                    parameters[4] = view.getParameters()[2];
                    parameters[5] = view.getParameters()[3];
                    parameters[6] = view.getRatioCalcParameters()[0]; // "single 4 0";
                    parameters[7] = view.getRatioCalcParameters()[1]; // "150 1 7 0 0";

                    String expid = data.expId;
                    String genomeVersion = data.grVersion;
                    String metadata = data.metaData;

                    // isConverted = model.rawToProfile(fileName, fileID, expid,
                    // processtype, parameters, metadata, genomeRelease,
                    // author);

                    isConverted = model.rawToProfile(expid, parameters,
                            metadata, genomeVersion, author);

                    if (isConverted) {
                        message = "The server has converted: " + fileName
                                + " with file id: " + fileID + " from " + expid
                                + "\n";
                        view.printToConvertText(message, "green");

                    } else {
                        message = "WARNING - The server couldn't convert: "
                                + fileName + " with file id: " + fileID
                                + " from " + expid + "\n";
                        view.printToConvertText(message, "red");
                        }
                    }
                }
            }else{
                message = "Parameters are invalid!";
                view.printToConvertText(message, "red");
            }
        }

    }

    /**
     * The listener to create region data,
     *
     * @author c11ann
     */
    class RawToRegionDataListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {

            System.out.println("RAW TO REGION");
            System.out.println(view.getAllMarkedFiles());

        }
    }

    /**
     * Listener thats keeps track of which files that the user wants to
     * schedule.
     *
     * @author c11ann
     */
    class ScheduleFileListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {

            System.out.println("SCHEDULEING FILE");
            System.out.println(view.getAllMarkedFiles());

        }
    }

    class ProcessFileListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            System.out.println("Process");
            // TODO Skicka in filedata arrayen
            ArrayList<ExperimentData> selectedData = view
                    .getSelectedDataInWorkspace();
            ArrayList<FileData> selectedFiles = new ArrayList<FileData>();
            for (ExperimentData experiment : selectedData) {
                for (FileData file : experiment.files) {
                    if (!selectedFiles.contains(file)) {
                        selectedFiles.add(file);
                    }
                }
            }
            view.setProccessFileList(selectedFiles);
        }
    }

    class LoginListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            model.setIp(view.getIp());
            String username = view.getUsername();
            String pwd = view.getPassword();
            if (model.loginUser(username, pwd)) {
                view.updateLoginAccepted(username, pwd, "Desktop User");
                sysController.updateAnnotationTable();
            } else {
                view.updateLoginNeglected("Could not login");
            }
        }
    }

    class QuerySearchListener implements ActionListener, Runnable {
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            String pubmed = view.getQuerySearchString();
            ArrayList<ExperimentData> searchResults = model.search(pubmed);
            if (searchResults != null) {
                view.updateQuerySearchResults(searchResults);
            } else {
                /*
                 * searchResults = new
                 * ArrayList<ExperimentData>(Arrays.asList(ExperimentData
                 * .getExample()));
                 * view.updateQuerySearchResults(searchResults);
                 */
                JOptionPane.showMessageDialog(null, "No search results!",
                        "Search Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    class LogoutListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            if (model.logoutUser()) {
                view.updateLogout();
            } else {
                view.updateLogout();
            }

        }
    }

    class UploadListener implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            // if (model.uploadFile()) {
            // update view?
            // }

        }
    }

    class DownloadWindowListener implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            new Thread(this).start();
        }

        @Override
        public void run() {
            // Skicka med arraylist<FileData> för de filer som ska nerladdas
            ArrayList<ExperimentData> selectedData = view
                    .getSelectedDataInWorkspace();
            ArrayList<FileData> selectedFiles = new ArrayList<FileData>();
            for (ExperimentData experiment : selectedData) {
                for (FileData file : experiment.files) {
                    if (!selectedFiles.contains(file)) {
                        selectedFiles.add(file);
                    }
                }
            }
            DownloadWindow downloadWindow = new DownloadWindow(selectedFiles,
                    model.getOngoingDownloads());
            view.setDownloadWindow(downloadWindow);
            downloadWindow.addDownloadFileListener(new DownloadFileListener());
        }
    }

    class DownloadFileListener implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            new Thread(this).start();
        }

        @Override
        public void run() {

            DownloadWindow downloadWindow = view.getDownloadWindow();
            ArrayList<FileData> fileData = downloadWindow.getFiles();
            /*
             * >>>>>>> branch 'dev' of
             * https://github.com/genomizer/genomizer-desktop.git FileDialog
             * fileDialog = new FileDialog((java.awt.Frame) null,
             * "Choose a directory", FileDialog.SAVE);
             * fileDialog.setVisible(true); String directoryName =
             * fileDialog.getDirectory(); System.out.println("You chose " +
             * directoryName);
             *
             * if (fileData == null) {
             * System.err.println("No directory selected"); return; }
             */
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int ret = fileChooser.showOpenDialog(new JPanel());
            String directoryName = "";
            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    directoryName = fileChooser.getSelectedFile()
                            .getCanonicalPath();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(directoryName);
            } else {
                return;
            }

            for (FileData data : fileData) {
                System.out.println(data.url);
                model.downloadFile(data.url, data.id, directoryName + "/"
                        + data.filename, data.filename);
            }
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
                    ArrayList<FileData> f = new ArrayList<FileData>();
                    uploadTab.addExistingExpPanel(ed);
                    // uploadTab.repaint();
                    // uploadTab.revalidate();
                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(null,
                            "Couldn't find or retrieve experiment", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Please fill in experiment name", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
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

            /*
             * FileDialog fileDialog = new java.awt.FileDialog( (java.awt.Frame)
             * view); fileDialog.setMultipleMode(true);
             * fileDialog.setVisible(true); File[] files =
             * fileDialog.getFiles();
             */
            String[] fileNames = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                fileNames[i] = files[i].getName();
            }
            UploadToExistingExpPanel uploadToExistingExpPanel = view
                    .getUploadTab().getUploadToExistingExpPanel();
            uploadToExistingExpPanel.createUploadFileRow(files);
            uploadToExistingExpPanel.enableUploadButton(true);
            uploadToExistingExpPanel.addFileDrop();
        }
    }

    class UploadToExperimentButtonListener implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            new Thread(this).start();
        }

        @Override
        public void run() {
            ArrayList<File> files = view.getUploadTab()
                    .getUploadToExistingExpPanel().getFilesToUpload();
            HashMap<String, String> types = view.getUploadTab()
                    .getUploadToExistingExpPanel().getTypes();
            // Should be genome release from uploadTab
            String release = "rn5";

            ExperimentData ed = view.getUploadTab()
                    .getUploadToExistingExpPanel().getExperiment();

            for (File f : files) {
                if (model.uploadFile(ed.getName(), f, types.get(f.getName()),
                        view.getUsername(), false, release)) {
                    view.getUploadTab().getUploadToExistingExpPanel()
                            .deleteFileRow(f);
                    if (view.getUploadTab().getUploadToExistingExpPanel()
                            .getFileRows().size() == 0) {
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
                            "Upload of " + f.getName() + " not complete",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    class updateSearchAnnotationsListener implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            new Thread(this).start();
        }

        @Override
        public void run() {
            AnnotationDataType[] annotations = model.getAnnotations();
            if (annotations != null && annotations.length > 0) {
                view.setSearchAnnotationTypes(annotations);
            }
        }
    }

    class SearchToWorkspaceListener implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            new Thread(this).start();
        }

        @Override
        public void run() {
            ArrayList<ExperimentData> selectedData = view
                    .getSelectedDataInSearch();
            if (selectedData != null && selectedData.size() > 0) {
                view.addToWorkspace(view.getSelectedDataInSearch());
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
            /*
             * FileDialog fileDialog = new java.awt.FileDialog( (java.awt.Frame)
             * view); fileDialog.setMultipleMode(true);
             * fileDialog.setVisible(true); File[] files =
             * fileDialog.getFiles();
             */

            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setMultiSelectionEnabled(true);
            int ret = fileChooser.showOpenDialog(new JPanel());
            String directoryName = "";
            File[] files;
            System.out.println(ret);
            if (ret == JFileChooser.APPROVE_OPTION) {
                files = fileChooser.getSelectedFiles();
                System.out.println(directoryName);
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
            String expName = view.getNewExpName();
            AnnotationDataValue[] annotations = view.getUploadAnnotations();
            ArrayList<File> files = view.getFilesToUpload();
            if (files != null && files.size() > 0 && annotations != null
                    && expName != null) {
                HashMap<String, String> types = view.getFilesToUploadTypes();
                // Should be genome release from uploadTab
                String release = "rn5";
                // Test purpose
                for (AnnotationDataValue a : annotations) {
                    System.out.println(a.getName() + " " + a.getValue());
                }
                boolean created = model.addNewExperiment(expName,
                        view.getUsername(), annotations);
                System.out.println(created);
                if (created) {
                    for (File f : files) {
                        view.disableSelectedRow(f);
                        System.out.println(f.getName());
                        if (model.uploadFile(expName, f,
                                types.get(f.getName()), view.getUsername(),
                                false, release)) {
                            view.deleteUploadFileRow(f);
                            for (HTTPURLUpload upload : model
                                    .getOngoingUploads()) {
                                if (f.getName().equals(upload.getFileName())) {
                                    model.getOngoingUploads().remove(upload);
                                }
                            }
                            view.refreshSearch();
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Couldn't upload " + f.getName() + ".",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Couldn't create experiment " + expName + ".",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    class AnalyzeSelectedListener implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            System.out.println("ANALYZE");
        }
    }

    private void fileListAddMouseListener(JList fileList) {
        fileList.addMouseListener(new MouseAdapter() {
            String species = "";
            int count = 0;

            @Override
            public void mouseClicked(MouseEvent event) {
                JList list = (JList) event.getSource();
                String specie = "";

                if (list.getModel().getSize() > 0) {
                    int index = list.locationToIndex(event.getPoint());

                    CheckListItem item = (CheckListItem) list.getModel()
                            .getElementAt(index);
                    if (count == 0) {
                        species = "";
                    }
                    if (species.equals("") && count == 0) {
                        species = item.getSpecie();
                    }
                    if (item.getSpecie().equals(species)) {

                        item.setSelected(!item.isSelected());

                        GenomeReleaseData[] genome = model
                                .getSpecieGenomeReleases(item.getSpecie());
                        view.setGenomeFileList(genome);

                        if (item.isSelected()) {
                            count++;
                        } else {
                            count--;
                        }
                    }
                    list.repaint(list.getCellBounds(index, index));
                }
            }
        });
    }

    class RatioCalcListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            System.out.println("RATIO CALC");
            view.getRatioCalcPopup().setDefaultRatioPar();
            view.showRatioPopup();
        }
    }

    class ProcessFeedbackListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            ProcessFeedbackData[] processFeedbackData = model
                    .getProcessFeedback();
            if (processFeedbackData != null && processFeedbackData.length > 0) {
                System.out.println("Feedbackdata received");
                view.showProcessFeedback(processFeedbackData);
            }
        }
    }

    class DeleteFromDatabaseListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            int i = -1;
            if (JOptionPane
                    .showConfirmDialog(
                            null,
                            "Are you sure you want to delete the selected data from the database?",
                            "Delete from database", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                i = 0;
                ArrayList<ExperimentData> expData = view
                        .getSelectedDataInWorkspace();
                for (ExperimentData data : expData) {
                    for (FileData fileData : data.files) {
                        model.deleteFileFromExperiment(fileData.id);
                        i++;
                    }
                }
                expData = view.getSelectedExperimentsInWorkspace();
                for (ExperimentData data : expData) {
                    model.deleteExperimentFromDatabase(data.name);
                    i++;
                }
            }

            if (i == 0) {
                JOptionPane.showMessageDialog(null, "No data was selected",
                        "Delete error", JOptionPane.ERROR_MESSAGE);
            } else if (i > 0) {
                JOptionPane.showMessageDialog(null,
                        "Selected data was removed from database",
                        "Delete success", JOptionPane.INFORMATION_MESSAGE);
                view.removeSelectedFromWorkspace();
                view.refreshSearch();
            }
        }
    }

    class OkListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            System.out.println("OK");
            view.getRatioCalcPopup().hideRatioWindow();
        }

    }

    class CancelListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            System.out.println("CANCEL");
            view.setUnusedRatioPar();
            view.getRatioCalcPopup().hideRatioWindow();
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
                String release = "rn5";
                // Test purpose
                for (AnnotationDataValue a : annotations) {
                    System.out.println(a.getName() + " " + a.getValue());
                }
                boolean created = model.addNewExperiment(expName,
                        view.getUsername(), annotations);
                System.out.println(created);
                if (created) {
                    for (File f : files) {
                        System.out.println(f.getName());
                        view.disableSelectedRow(f);
                        if (model.uploadFile(expName, f,
                                types.get(f.getName()), view.getUsername(),
                                false, release)) {
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
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Couldn't create new experiment " + expName + ".",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "No files selected.");
            }
        }
    }
}
