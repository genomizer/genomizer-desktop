package controller;

import gui.ConvertTab;
import gui.DeleteDataWindow;
import gui.ErrorDialog;
import gui.GUI;
import gui.UploadTab;
import gui.WorkspaceTab;

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
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import model.ErrorLogger;
import model.GenomizerModel;
import util.ExperimentData;
import util.FileData;
import util.RequestException;
import util.TreeTable;

import communication.DownloadHandler;

public class WorkspaceTabController {
    GUI view;
    GenomizerModel model;
    private final JFileChooser fileChooser;
    private boolean abortDeletion;
    private WorkspaceTab workspaceTab;
    private TreeTable treeTable;

    public WorkspaceTabController(GUI view, GenomizerModel model,
            JFileChooser fileChooser) {
        this.view = view;
        this.model = model;
        this.fileChooser = fileChooser;
        fileChooser.setApproveButtonText("Select");
        workspaceTab = view.getWorkSpaceTab();
        treeTable = workspaceTab.getTable();

        workspaceTab.addDownloadFileListener(DownloadFileListener());
        workspaceTab.addProcessFileListener(ProcessFileListener());
        workspaceTab.addUploadToListener(UploadToListener());

        workspaceTab.addConvertFileListener(ConvertFileListener());

        // view.addUploadToListener( UploadToListener());
        workspaceTab.addDeleteSelectedListener(DeleteFromDatabaseListener());
        treeTable.addTreeSelectionListener(SelectionListener());
        // workspaceTab.addListSelectionListener(SelectionListener());
        updateOngoingDownloadsPanel();
    }

    private TreeSelectionListener SelectionListener() {

        return new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent arg0) {
                System.out.println(treeTable.getNumberOfSelected());
                if (treeTable.getNumberOfSelected() == 1) {
                    workspaceTab.getProcessButton().setEnabled(true);
                    workspaceTab.getUploadToButton().setEnabled(true);
                } else {
                    workspaceTab.getProcessButton().setEnabled(false);
                    workspaceTab.getUploadToButton().setEnabled(false);
                }
                if (treeTable.getNumberOfSelected() > 0) {
                    workspaceTab.getRemoveButton().setEnabled(true);
                    workspaceTab.getDownloadButton().setEnabled(true);
                    workspaceTab.getConvertButton().setEnabled(true);
                    workspaceTab.getDeleteButton().setEnabled(true);
                } else {
                    workspaceTab.getRemoveButton().setEnabled(false);
                    workspaceTab.getDownloadButton().setEnabled(false);
                    workspaceTab.getConvertButton().setEnabled(false);
                    workspaceTab.getDeleteButton().setEnabled(false);
                }
            }
        };

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
                        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
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

                        view.getWorkSpaceTab().changeTab(
                                WorkspaceTab.DOWNLOADTABNUMBER);
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

                            try {
                                String filename = data.filename;
                                if (System.getProperty("os.name").contains(
                                        "Windows")) {
                                    String totalpath = directoryName + "/"
                                            + data.expId + "/" + data.type
                                            + "/" + filename;
                                    if (totalpath.length() > 259) {
                                        // c:\[256 chars]<NUL>, över det dampar
                                        // windows
                                        String pathOnly = directoryName + "/"
                                                + data.expId + "/" + data.type
                                                + "/";
                                        String minlength = data.filename
                                                .substring(data.filename
                                                        .lastIndexOf('.') - 1);
                                        if ((pathOnly.length() + minlength
                                                .length()) > 259) {
                                            new ErrorDialog(
                                                    "Error",
                                                    "too long path, download to higher folder",
                                                    "windows is limited to c:\\[256 chars]<end sign>, " +
                                                    "you're trying to download to a place so the " +
                                                    "total path would be over 260, change it")
                                                    .showDialog();
                                            return;
                                        } else {

                                            String extension = data.filename
                                                    .substring(data.filename
                                                            .lastIndexOf('.'));
                                            int allowedlength = 259
                                                    - pathOnly.length()
                                                    - extension.length();
                                            filename = filename
                                                    .substring(
                                                            0,
                                                            filename.lastIndexOf('.')
                                                                    + (259 - allowedlength));
                                            filename = filename + extension;
                                        }
                                    }
                                }

                                model.downloadFile(data.url, data.id,
                                        directoryName + "/" + data.expId + "/"
                                                + data.type + "/"
                                                + filename, data.filename);
                            } catch (RequestException e) {
                                new ErrorDialog("Couldn't download file", e)
                                        .showDialog();
                            }
                        }
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


                        view.getConvertTab().setCount(0);

                        view.getConvertTab().setConvertButtonDisabled();
                        view.getConvertTab().setDeleteButtonDisabled();
                        view.getConvertTab().setAllButtonsNotSelected();

                        view.setConvertFileList();
                    };
                }.start();
            }
        };
    }

    public ActionListener ProcessFileListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExperimentData selecteddata = workspaceTab.getTable()
                        .getSelectedExperiment();
                model.setProcessingExperiment(selecteddata);
                view.setProcessingTab( selecteddata.getName() );
            }
        };
    }

    public ActionListener UploadToListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //button only active if experiment is selected
                ExperimentData firstChosenExperiment = workspaceTab.getTable()
                        .getSelectedExperiment();
                UploadTab ut = view.getUploadTab();
                view.getTabbedPane().setSelectedComponent(ut);
                ut.getExperimentNameField().setText(
                        firstChosenExperiment.getName());
                ut.getExistingExpButton().doClick();
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
                                        try {
                                            model.deleteFileFromExperiment(fileData.id);
                                        } catch (RequestException e1) {
                                            new ErrorDialog(
                                                    "Couldn't delete file from experiment",
                                                    e1).showDialog();
                                        }
                                    }
                                    i++;
                                }
                                progress++;
                                deleteWindow.updateProgress(progress, size);
                            }
                            for (ExperimentData data : selectedExps) {
                                if (!abortDeletion) {
                                    try {
                                        model.deleteExperimentFromDatabase(data.name);
                                    } catch (RequestException e1) {
                                        new ErrorDialog(
                                                "Couldn't delete experiment",
                                                e1).showDialog();
                                    }
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
