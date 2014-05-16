package controller;

import gui.CheckListItem;
import gui.DownloadWindow;
import gui.GenomizerView;
import gui.UploadTab;
import gui.sysadmin.SysadminController;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.GenomizerModel;
import util.*;

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
        view.addSelectFilesToUploadButtonListener(
                new SelectFilesToUploadButtonListener());
        view.setSysadminController(
                sysController = new SysadminController(model));
        view.addAddToExistingExpButtonListener(
                new AddToExistingExpButtonListener());
        view.addUploadToExperimentButtonListener(
                new UploadToExperimentButtonListener());
        view.addUpdateSearchAnnotationsListener(
                new updateSearchAnnotationsListener());
        view.addProcessFileListener(new ProcessFileListener());
        view.addSearchToWorkspaceListener(new SearchToWorkspaceListener());
        view.addNewExpButtonListener(new NewExpButtonListener());
        view.addSelectButtonListener(new SelectFilesToNewExpListener());
        view.addUploadButtonListener(new UploadNewExpListener());
        view.addAnalyzeSelectedListener(new AnalyzeSelectedListener());
        fileListAddMouseListener(view.getfileList());
        view.addRatioCalcListener(new RatioCalcListener());
        view.addProcessFeedbackListener(new ProcessFeedbackListener());
        view.setOngoingUploads(model.getOngoingUploads());
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
            int markedSize = allMarked.size();
            String message = null;
            Boolean isConverted = false;

            if (!allMarked.isEmpty()) {

                for (int i = 0; i < markedSize; i++) {

                    String fileName = allMarked.get(i).filename;
                    String fileID = allMarked.get(i).id;
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

                    String expid = allMarked.get(i).expId;
                    String genomeRelease = allMarked.get(i).grVersion;
                    String metadata = allMarked.get(i).metaData;

                    isConverted = model.rawToProfile(fileName, fileID, expid,
                            processtype, parameters, metadata, genomeRelease,
                            author);

                    if (isConverted.equals(true)) {
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
            String directoryName;
            if (ret == JFileChooser.APPROVE_OPTION) {
                directoryName = fileChooser.getSelectedFile().getAbsolutePath();
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
            try {
//                ExperimentData ed = model.retrieveExperiment(expID);
                ArrayList<FileData> f = new ArrayList<FileData>();
                ArrayList<AnnotationDataValue> adv = new ArrayList<>();
                adv.add(new AnnotationDataValue("0", "Species", "Cyborg"));
                adv.add(new AnnotationDataValue("1", "Sex", "Robot"));
                ExperimentData ed = new ExperimentData("Experiment 11", view.getUsername(), f, adv);
                uploadTab.addExistingExpPanel(ed);
//            uploadTab.repaint();
//            uploadTab.revalidate();
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Couldn't find experiment", "ERROR", JOptionPane.ERROR_MESSAGE);
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
            FileDialog fileDialog = new java.awt.FileDialog(
                    (java.awt.Frame) view);
            fileDialog.setMultipleMode(true);
            fileDialog.setVisible(true);
            File[] files = fileDialog.getFiles();
            String[] fileNames = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                fileNames[i] = files[i].getName();
            }
            view.selectFilesToExistingExp(files);
            view.getUploadTab().getUploadToExistingExpPanel()
                    .enableUploadButton(true);
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
            ArrayList<File> files = view.getFilesToUpload();
            HashMap<String, String> types = view.getFilesToUploadTypes();
            // Should be genome release from uploadTab
            String release = "rn5";
            // Test purpose
            for (AnnotationDataValue a : annotations) {
                System.out.println(a.getName() + " " + a.getValue());
            }
            // TODO: ändra till existerande experiment!
            boolean created = model.addNewExperiment(expName,
                    view.getUsername(), annotations);
            System.out.println(created);
            if (created) {
                for (File f : files) {
                    if (model.uploadFile(expName, f, types.get(f.getName()),
                            view.getUsername(), false, release)) {
                        view.deleteUploadFileRow(f);
                        JOptionPane.showMessageDialog(null,
                                "Upload of " + f.getName() + " complete",
                                "Done", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Upload of " + f.getName() + " not complete",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
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
            view.addToWorkspace(view.getSelectedDataInSearch());
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
            FileDialog fileDialog = new java.awt.FileDialog(
                    (java.awt.Frame) view);
            fileDialog.setMultipleMode(true);
            fileDialog.setVisible(true);
            File[] files = fileDialog.getFiles();
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
                        System.out.println(f.getName());
                        if (model.uploadFile(expName, f,
                                types.get(f.getName()), view.getUsername(),
                                false, release)) {
                            view.deleteUploadFileRow(f);
                            JOptionPane.showMessageDialog(null, "Upload of "
                                    + f.getName() + " complete", "Done",
                                    JOptionPane.PLAIN_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Upload of "
                                    + f.getName() + " not complete", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

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
            @Override
            public void mouseClicked(MouseEvent event) {
                JList list = (JList) event.getSource();

                if (list.getModel().getSize() > 0) {
                    int index = list.locationToIndex(event.getPoint());
                    CheckListItem item = (CheckListItem) list.getModel()
                            .getElementAt(index);

                    item.setSelected(!item.isSelected());

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
            view.setDefaultRatioPar();
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
            ProcessFeedbackData[] processFeedbackData = model.getProcessFeedback();
            if(processFeedbackData != null) {
            }
        }
    }


}
