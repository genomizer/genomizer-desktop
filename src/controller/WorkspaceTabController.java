package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import util.ExperimentData;
import util.FileData;
import gui.DeleteDataWindow;
import gui.GenomizerView;
import gui.UploadTab;
import model.ErrorLogger;
import model.GenomizerModel;

public class WorkspaceTabController {
    GenomizerView view;
    GenomizerModel model;
    private final JFileChooser fileChooser;
    private boolean abortDeletion;
    
    public WorkspaceTabController(GenomizerView view, GenomizerModel model,
            JFileChooser fileChooser) {
        this.view = view;
        this.model = model;
        this.fileChooser = fileChooser;
        view.addDownloadFileListener( DownloadFileListener());
        view.addProcessFileListener( ProcessFileListener());
        view.addUploadToListener( UploadToListener());
        view.addDeleteFromDatabaseListener( DeleteFromDatabaseListener());
        view.setOngoingDownloads(model.getOngoingDownloads());
    }
    
    /**
     * Listener for when the download button in the download window is clicked.
     * Opens a file chooser.
     */
    public ActionListener DownloadFileListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        
                        // DownloadWindow downloadWindow =
                        // view.getDownloadWindow();
                        ArrayList<ExperimentData> expData = view
                                .getSelectedDataInWorkspace();
                        ArrayList<FileData> fileData = new ArrayList<>();
                        for (ExperimentData data : expData) {
                            fileData.addAll(data.files);
                        }
                        if (fileData.isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "No files were selected.");
                            return;
                        }
                        fileChooser
                                .setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        int ret = fileChooser.showOpenDialog(new JPanel());
                        String directoryName = "";
                        if (ret == JFileChooser.APPROVE_OPTION) {
                            try {
                                directoryName = fileChooser.getSelectedFile()
                                        .getCanonicalPath();
                            } catch (IOException e) {
                                ErrorLogger.log(e);
                                e.printStackTrace();
                            }
                        } else {
                            return;
                        }
                        
                        for (FileData data : fileData) {
                            model.downloadFile(data.url, data.id, directoryName
                                    + "/" + data.filename, data.filename);
                        }
                        view.changeTabInWorkspace(1);
                    };
                }.start();
            }
        };
    }
    
    public ActionListener ProcessFileListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        // TODO Skicka in filedata arrayen
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
                        view.setProcessFileList(selectedFiles);
                    };
                }.start();
            }
        };
    }
    
    public ActionListener UploadToListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    ExperimentData firstChosenExperiment = view
                            .getSelectedExperimentsInWorkspace().get(0);
                    UploadTab ut = view.getUploadTab();
                    view.getTabbedPane().setSelectedComponent(ut);
                    ut.getExperimentNameField().setText(
                            firstChosenExperiment.getName());
                    ut.getExistingExpButton().doClick();
                } catch (IndexOutOfBoundsException ee) {
                    ErrorLogger.log(ee);
                    JOptionPane.showMessageDialog(null,
                            "No experiment was selected.");
                }
            }
        };
    }
    
    public ActionListener DeleteFromDatabaseListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        int i = -1;
                        if (JOptionPane
                                .showConfirmDialog(
                                        null,
                                        "Are you sure you want to delete the selected data from the database?",
                                        "Delete from database",
                                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            DeleteDataWindow deleteWindow = new DeleteDataWindow(
                                    view);
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
                            int size = selectedData.size()
                                    + selectedExps.size();
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
                            JOptionPane.showMessageDialog(null,
                                    "No data was selected", "Delete error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else if (i > 0) {
                            JOptionPane.showMessageDialog(null,
                                    "Selected data was removed from database",
                                    "Delete success",
                                    JOptionPane.INFORMATION_MESSAGE);
                            view.removeSelectedFromWorkspace();
                            view.refreshSearch();
                        }
                        
                    };
                }.start();
            }
        };
    }
    
    public ActionListener SelectFilesToNewExpListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        fileChooser
                                .setFileSelectionMode(JFileChooser.FILES_ONLY);
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
                    };
                }.start();
            }
        };
    }
    
}
