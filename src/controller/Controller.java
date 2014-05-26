package controller;

import gui.CheckListItem;
import gui.DeleteDataWindow;
import gui.DownloadWindow;
import gui.GenomizerView;
import gui.UploadTab;
import gui.UploadToExistingExpPanel;
import gui.sysadmin.SysadminController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.GenomizerModel;
import util.ActiveSearchPanel;
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
    private boolean abortDeletion;
    
    public Controller(GenomizerView view, GenomizerModel model) {
        abortDeletion = false;
        this.view = view;
        this.model = model;
        view.addLoginListener(new LoginListener());
        view.addLogoutListener(new LogoutListener());
        // view.addCancelListener(new CancelListener());
        view.addOkListener(new OkListener());
        updateView();
    }
    
    private void updateView() {
        view.addRatioCalcListener(new RatioCalcListener());
        view.addSearchListener(new QuerySearchListener());
        view.addConvertFileListener(new ConvertFileListener());
        view.addQuerySearchListener(new QuerySearchListener());
        view.addRawToProfileDataListener(new RawToProfileDataListener());
        view.addRawToRegionDataListener(new RawToRegionDataListener());
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
        view.addUploadToListener(new UploadToListener());
        fileListAddMouseListener(view.getfileList());
        view.addProcessFeedbackListener(new ProcessFeedbackListener());
        view.addDeleteFromDatabaseListener(new DeleteFromDatabaseListener());
        view.setOngoingUploads(model.getOngoingUploads());
        view.addUploadSelectedFilesListener(new UploadSelectedFilesListener());
        view.addSpeciesSelectedListener(new SpeciesSelectedListener());
        view.addDeleteSelectedListener(new DeleteSelectedListener());
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
            ArrayList<FileData> allMarked = view.getAllMarkedFiles();
            String message;
            Boolean isConverted;
            if (view.isCorrectToProcess() && view.isRatioCorrectToProcess()) {
                if (!allMarked.isEmpty()) {
                    
                    for (FileData data : allMarked) {
                        
                        String fileName = data.filename;
                        String author = view.getUsername();
                        String parameters[] = new String[8];
                        
                        parameters[0] = view.getParameters()[0];
                        parameters[1] = "";
                        parameters[2] = view.getParameters()[2];
                        parameters[3] = view.getParameters()[3];
                        parameters[4] = view.getParameters()[4];
                        parameters[5] = view.getParameters()[5];
                        
                        if (view.useRatio()) {
                            parameters[6] = view.getRatioCalcParameters()[0]; // "single 4 0";
                            parameters[7] = view.getRatioCalcParameters()[1]; // "150 1 7 0 0";
                        } else {
                            parameters[6] = "";
                            parameters[7] = "";
                        }
                        
                        String expid = data.expId;
                        String genomeVersion = view.getParameters()[1];
                        String metadata = parameters[0] + " " + parameters[1]
                                + " " + parameters[2] + " " + parameters[3]
                                + " " + parameters[4] + " " + parameters[5]
                                + " " + parameters[6] + " " + parameters[7];
                        
                        isConverted = model.rawToProfile(expid, parameters,
                                metadata, genomeVersion, author);
                        
                        if (isConverted) {
                            message = "The server has started process on file: "
                                    + fileName
                                    + " from experiment: "
                                    + expid
                                    + "\n\n";
                            view.printToConsole(message);
                            
                        } else {
                            message = "WARNING - The server couldn't start processing on file: "
                                    + fileName
                                    + " from experiment: "
                                    + expid
                                    + "\n\n";
                            view.printToConsole(message);
                        }
                    }
                }
            } else {
                // TODO Popup window
                message = "Parameters are invalid!: \n";
                message = message.concat("Window size must be >= 0\n");
                message = message.concat("Step position must be >= 0\n");
                message = message.concat("Step size must be > 0\n");
                
                message = message
                        .concat("Ratio calculation Input reads cut-off must be >= 0\n");
                message = message
                        .concat("Ratio calculation Chromosomes must be >= 0\n");
                message = message
                        .concat("Ratio calculation Window size must be > 0\n");
                message = message
                        .concat("Ratio calculation Step position must be >= 0\n\n");
                
                view.printToConsole(message);
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
            ArrayList<FileData> selectedFiles = new ArrayList<>();
            for (ExperimentData experiment : selectedData) {
                for (FileData file : experiment.files) {
                    if (!selectedFiles.contains(file.filename)) {
                        selectedFiles.add(file);
                    }
                }
            }
            view.setProcessFileList(selectedFiles);
        }
    }
    
    class LoginListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }
        
        @Override
        public void run() {
            model.setGenomizerView(view);
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
                
                // If search results are null and the active panel is search
            } else if (view.getActiveSearchPanel() == ActiveSearchPanel.SEARCH) {
                JOptionPane.showMessageDialog(null, "No search results!",
                        "Search Warning", JOptionPane.WARNING_MESSAGE);
                
                // If search results are null and the active panel is table
            } else if (view.getActiveSearchPanel() == ActiveSearchPanel.TABLE) {
                // Go back to the query search
                view.getBackButton().doClick();
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
            model.logoutUser();
            model.resetModel();
            view.updateLogout();
            view.resetGUI();
            updateView();
            
        }
    }
    
    /**
     * Listener for when the download button in workspace is clicked. Opens a
     * DownloadWindow with the selected files.
     */
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
            ArrayList<FileData> selectedFiles = new ArrayList<>();
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
    
    /**
     * Listener for when the download button in the download window is clicked.
     * Opens a file chooser.
     */
    class DownloadFileListener implements ActionListener, Runnable {
        
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            
            new Thread(this).start();
        }
        
        @Override
        public void run() {
            
            DownloadWindow downloadWindow = view.getDownloadWindow();
            ArrayList<FileData> fileData = downloadWindow.getFiles();
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
                    String species = null;
                    boolean existingSpecies = false;
                    for (AnnotationDataValue adv : ed.getAnnotations()) {
                        if (adv.getName().equalsIgnoreCase("species")) {
                            species = adv.getValue();
                            existingSpecies = true;
                        }
                        
                    }
                    if (existingSpecies) {
                        uploadTab.addExistingExpPanel(ed);
                        GenomeReleaseData[] grd = model
                                .getSpeciesGenomeReleases(species);
                        view.setGenomeReleases(grd);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Missing species in experiment.", "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    }
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
            
            UploadToExistingExpPanel uploadToExistingExpPanel = view
                    .getUploadTab().getUploadToExistingExpPanel();
            uploadToExistingExpPanel.createUploadFileRow(files);
            uploadToExistingExpPanel.enableUploadButton();
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
            // String release = "wk1m";
            
            ExperimentData ed = view.getUploadTab()
                    .getUploadToExistingExpPanel().getExperiment();
            
            for (File f : files) {
                if (model.uploadFile(ed.getName(), f, types.get(f.getName()),
                        view.getUsername(), false, view.getGenomeVersion(f))) {
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
                if (selectedData.size() == 1) {
                    JOptionPane.showMessageDialog(null, "Added experiment \""
                            + selectedData.get(0).getName()
                            + "\" to the workspace.");
                } else {
                    JOptionPane.showMessageDialog(null, "Added experiments to"
                            + " the workspace.");
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
                // String release = "wk1m";
                // Test purpose
                for (AnnotationDataValue a : annotations) {
                    System.out.println(a.getName() + " " + a.getValue());
                }
                boolean created = model.addNewExperiment(expName, annotations);
                System.out.println(created);
                if (created) {
                    for (File f : files) {
                        view.disableSelectedRow(f);
                        System.out.println(f.getName());
                        System.out.println(view.getGenomeVersion(f));
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
    
    class UploadToListener implements ActionListener, Runnable {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }
        
        @Override
        public void run() {
            try {
                ExperimentData firstChosenExperiment = view
                        .getSelectedExperimentsInWorkspace().get(0);
                UploadTab ut = view.getUploadTab();
                view.getTabbedPane().setSelectedComponent(ut);
                ut.getExperimentNameField().setText(
                        firstChosenExperiment.getName());
                ut.getExistingExpButton().doClick();
            } catch (IndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(null,
                        "No experiment was selected.");
            }
        }
    }
    
    private void fileListAddMouseListener(JList fileList) {
        fileList.addMouseListener(new MouseAdapter() {
            String species = "";
            int count = 0;
            
            @Override
            public void mouseClicked(MouseEvent event) {
                JList list = (JList) event.getSource();
                // String specie = "";
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
                                .getSpeciesGenomeReleases(item.getSpecie());
                        if (view.getAllMarkedFiles().isEmpty()) {
                            view.setGenomeFileList(null);
                        } else {
                            view.setGenomeFileList(genome);
                        }
                        
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
            // view.getRatioCalcPopup().setDefaultRatioPar();
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
                DeleteDataWindow deleteWindow = new DeleteDataWindow(view);
                deleteWindow.setVisible(true);
                deleteWindow.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        abortDeletion = true;
                    }
                });
                i = 0;
                ArrayList<ExperimentData> selectedExps = view
                        .getSelectedExperimentsInWorkspace();
                ArrayList<ExperimentData> selectedData = view
                        .getSelectedDataInWorkspace();
                int size = selectedData.size() + selectedExps.size();
                int progress = 0;
                for (ExperimentData data : selectedData) {
                    for (FileData fileData : data.files) {
                        if (!abortDeletion) {
                            model.deleteFileFromExperiment(fileData.id);
                        }
                        i++;
                    }
                    progress++;
                    deleteWindow.updateProgress(progress, size);
                }
                for (ExperimentData data : selectedExps) {
                    if (!abortDeletion) {
                        model.deleteExperimentFromDatabase(data.name);
                    }
                    i++;
                    progress++;
                    deleteWindow.updateProgress(progress, size);
                }
                deleteWindow.dispose();
            }
            abortDeletion = false;
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
            view.getRatioCalcPopup().hideRatioWindow();
        }
        
    }
    
    /*
     * class CancelListener implements ActionListener, Runnable {
     * 
     * @Override public void actionPerformed(ActionEvent e) { new
     * Thread(this).start(); }
     * 
     * @Override public void run() {
     * 
     * System.out.println("CANCEL"); // view.setUnusedRatioPar();
     * view.getRatioCalcPopup().hideRatioWindow(); } }
     */
    
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
                for (AnnotationDataValue a : annotations) {
                    System.out.println(a.getName() + " " + a.getValue());
                }
                boolean created = model.addNewExperiment(expName, annotations);
                System.out.println(created);
                if (created) {
                    for (File f : files) {
                        System.out.println(f.getName());
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
            System.out.println("HEJ DANIEL");
        }
    }
    
    class DeleteSelectedListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }
        
        @Override
        public void run() {
            
            System.out.println("Delete");
            ArrayList<FileData> markedFiles = view.getAllMarkedFiles();
            ArrayList<ExperimentData> exData = view.getFileInfo();
            
            if (exData != null && markedFiles != null) {
                
                for (ExperimentData data : exData) {
                    data.files.removeAll(markedFiles);
                }
                view.setFileInfo(exData);
            }
        }
    }
}
