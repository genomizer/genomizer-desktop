package controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import communication.DownloadHandler;

import util.ExperimentData;
import util.FileData;
import gui.ConvertTab;
import gui.DeleteDataWindow;
import gui.ErrorDialog;
import gui.GUI;
import gui.UploadTab;
import gui.WorkspaceTab;
import model.ErrorLogger;
import model.GenomizerModel;

public class WorkspaceTabController {
    GUI view;
    GenomizerModel model;
    private final JFileChooser fileChooser;
    private boolean abortDeletion;
    private WorkspaceTab workspaceTab;

    public WorkspaceTabController(GUI view, GenomizerModel model,
            JFileChooser fileChooser) {
        this.view = (GUI) view;
        this.model = model;
        this.fileChooser = fileChooser;

        workspaceTab = view.getWorkSpaceTab();
        workspaceTab.addDownloadFileListener(DownloadFileListener());
        workspaceTab.addProcessFileListener(ProcessFileListener());
        workspaceTab.addUploadToListener(UploadToListener());

        workspaceTab.addConvertFileListener(ConvertFileListener());

        // view.addUploadToListener( UploadToListener());
        workspaceTab.addDeleteSelectedListener(DeleteFromDatabaseListener());

        updateOngoingDownloadsPanel();
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

                        ArrayList<ExperimentData> expData = view
                                .getWorkSpaceTab().getSelectedData();
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
                            File theDir = new File(directoryName + "/"
                                    + data.expId + "/" + data.type);

                            if (!theDir.isDirectory()) {
                                if (!theDir.mkdirs()) {

                                    new ErrorDialog("Download Failure",
                                            "Couldn't Create Folders",
                                            "mkdirs FAILURE\nNot possible to create directory"
                                                    + theDir + "!")
                                            .showDialog();
                                }
                            }
                            // TODO L�gga filen i olika datatypsmappar?
                            model.downloadFile(data.url, data.id, directoryName
                                    + "/" + data.expId + "/" + data.type + "/"
                                    + data.filename, data.filename);
                        }
                        view.getWorkSpaceTab().changeTab(
                                WorkspaceTab.DOWNLOADTABNUMBER);
                    };
                }.start();
            }
        };
    }

    public ActionListener ConvertFileListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        // TODO Skicka in filedata arrayen
                        ArrayList<ExperimentData> selectedData = view
                                .getWorkSpaceTab().getSelectedData();
                        ArrayList<FileData> selectedFiles = new ArrayList<>();

                        for (ExperimentData experiment : selectedData) {
                            for (FileData file : experiment.files) {
                                if (!selectedFiles.contains(file)) {
                                    selectedFiles.add(file);
                                }
                            }
                        }

                        view.setConvertFileList(selectedFiles);
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
                                .getWorkSpaceTab().getSelectedData();
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
                            .getWorkSpaceTab().getSelectedExperiments().get(0);
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

    public ActionListener ConvertListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    ExperimentData firstChosenExperiment = view
                            .getWorkSpaceTab().getSelectedExperiments().get(0);
                    ConvertTab ct = view.getConvertTab();

                    view.getTabbedPane().setSelectedComponent(ct);

                    // ct.getExperimentNameField().setText(firstChosenExperiment.getName());
                    // ct.getExistingExpButton().doClick();
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
                                    .getWorkSpaceTab().getSelectedExperiments();
                            ArrayList<ExperimentData> selectedData = view
                                    .getWorkSpaceTab().getSelectedData();
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
                            view.getWorkSpaceTab().removeSelectedData();
                            view.getQuerySearchTab().refresh();
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
                        view.getUploadTab().getNewExpPanel()
                                .createUploadFileRow(files);
                        view.getUploadTab().getNewExpPanel()
                                .enableUploadButton(true);
                    };
                }.start();
            }
        };
    }

    /**
     * Method updating the current ongoing downloads.
     */
    private void updateOngoingDownloadsPanel() {
        final CopyOnWriteArrayList<DownloadHandler> completedDownloads = new CopyOnWriteArrayList<DownloadHandler>();

        Runnable task = new Runnable() {
            @Override
            public void run() {

                JPanel ongoingDownloadsPanel = workspaceTab
                        .getOngoingDownloadsPanel();
                final CopyOnWriteArrayList<DownloadHandler> ongoingDownloads = model
                        .getOngoingDownloads();

                ongoingDownloadsPanel.removeAll();
                if (ongoingDownloads != null) {
                    for (final DownloadHandler handler : ongoingDownloads) {
                        if (!handler.isFinished() && handler.getTotalSize() > 0) {
                            JPanel downloadPanel = new JPanel(
                                    new BorderLayout());
                            double speed = handler.getCurrentSpeed() / 1024.0 / 2014.0;

                            JProgressBar progress = new JProgressBar(0,
                                    handler.getTotalSize());
                            progress.setValue(handler.getCurrentProgress());
                            progress.setStringPainted(true);
                            JButton stopButton = new JButton("X");
                            stopButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    ongoingDownloads.remove(handler);
                                }
                            });
                            downloadPanel.add(progress, BorderLayout.CENTER);
                            downloadPanel.add(stopButton, BorderLayout.EAST);
                            downloadPanel.add(new JLabel(handler.getFileName()
                                    + " (" + Math.round(speed * 100.0) / 100.0
                                    + "MiB/s)"), BorderLayout.NORTH);
                            ongoingDownloadsPanel.add(downloadPanel);
                        } else {
                            if (handler.isFinished()) {
                                completedDownloads.add(handler);
                            }
                            ongoingDownloads.remove(handler);
                        }
                    }

                }
                for (final DownloadHandler handler : completedDownloads) {
                    JPanel completedDownloadPanel = new JPanel(
                            new BorderLayout());
                    JProgressBar progress = new JProgressBar(0, 100);
                    progress.setValue(100);
                    progress.setStringPainted(true);
                    JButton stopButton = new JButton("X");
                    stopButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            completedDownloads.remove(handler);
                        }
                    });
                    completedDownloadPanel.add(progress, BorderLayout.CENTER);
                    completedDownloadPanel.add(stopButton, BorderLayout.EAST);
                    completedDownloadPanel.add(new JLabel(
                            "<html><b>Completed: </b>" + handler.getFileName()
                                    + "</html>"), BorderLayout.NORTH);
                    ongoingDownloadsPanel.add(completedDownloadPanel);
                }
                ongoingDownloadsPanel.revalidate();
                ongoingDownloadsPanel.repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    ErrorLogger.log(e);
                }
            }
        };
        model.addTickingTask(task);
    }

}
