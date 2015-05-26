package gui;

import gui.DownloadWindow;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.ErrorLogger;

import util.ExperimentData;
import util.FileData;

import communication.DownloadHandler;

// Removed from project because it didn't look like it was used!
// c12oor OO - 2015 05 05.


// FROM GUI!
///**
// * Sets the downloadWindow attribute of the GUI.
// *
// * @param downloadWindow
// *            The DownloadWindow to set the GUI's downloadWindow attribute
// *            to.
// */
//public void setDownloadWindow(DownloadWindow downloadWindow) {
//    this.downloadWindow = downloadWindow;
//}
//
///**
// * @return The GUI's downloadWindow.
// */
//public DownloadWindow getDownloadWindow() {
//    return downloadWindow;
//}

// FROM GENOMIZERVIEW!
//
//public void setDownloadWindow(DownloadWindow downloadWindow); // used commented
//
//public DownloadWindow getDownloadWindow(); // used commented

// FROM CONTROLLER!
///**
// * Listener for when the download button in workspace is clicked. Opens a
// * DownloadWindow with the selected files.
// *
// * TODO: separate view parts from Thread. Move to correct tab controller?
// * TODO: is this even used anywhere? -seems to only switch tabs now.
// */
//public ActionListener DownloadWindowListener() {
//    return new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            new Thread() {
//                @Override
//                public void run() {
//                    // Skicka med arraylist<FileData> för de filer som ska
//                    // nerladdas
//                    ArrayList<ExperimentData> selectedData = view
//                            .getWorkSpaceTab().getSelectedData();
//                    ArrayList<FileData> selectedFiles = new ArrayList<>();
//                    for (ExperimentData experiment : selectedData) {
//                        for (FileData file : experiment.files) {
//                            if (!selectedFiles.contains(file)) {
//                                selectedFiles.add(file);
//                            }
//                        }
//                    }
//                    DownloadWindow downloadWindow = new DownloadWindow(
//                            selectedFiles, model.getOngoingDownloads());
//                    view.setDownloadWindow(downloadWindow);
//                    downloadWindow.setVisible(true);
//                };
//            }.start();
//        }
//    };
//}

public class DownloadWindow extends JFrame {

    private static final long serialVersionUID = -7647204230941649167L;
    private JPanel mainPanel;
    private JPanel ongoingPanel;
    private JButton downloadButton;
    private ArrayList<FileData> files;
    private CopyOnWriteArrayList<DownloadHandler> ongoingDownloads;
    private boolean running;

    /**
     * Initiates a new DownloadWindow with the files it receives.
     *
     * @param files
     *            An ArrayList containing the FileData of the chosen files.
     */
    public DownloadWindow(ArrayList<FileData> files,
            CopyOnWriteArrayList<DownloadHandler> ongoingDownloads) {

        URL url = ClassLoader.getSystemResource("icons/logo.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        setIconImage(img);
        this.ongoingDownloads = ongoingDownloads;
        this.setLayout(new BorderLayout());
        this.files = files;
        // Gets the names of the files
        ArrayList<String> fileNames = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            fileNames.add(files.get(i).getName());
        }
        // Sets up the DownloadWindow using the filenames.
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                running = false;
            }
        });
        mainPanel = new JPanel(new BorderLayout());
        setUpTablePanel(fileNames);
        setUpOngoingPanel();
        add(mainPanel, BorderLayout.CENTER);
        updateProgress();
    }

    /**
     * Private method that sets up the DownloadWindow.
     *
     * @param data
     *            An ArrayList containing the Strings to set up the window with.
     */
    private void setUpTablePanel(ArrayList<String> data) {

        JPanel tablePanel = new JPanel(new BorderLayout(3, 3));
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        tablePanel.add(new JLabel("test"), BorderLayout.SOUTH);

        // Set up the JTable
        String[] headings = new String[] { "File Name" };
        String[][] content = new String[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            content[i][0] = data.get(i);
        }
        JTable table = new JTable(content, headings);

        // Add comboboxes to each row in the table.
        table.setRowHeight(30);
        table.setEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);

        downloadButton = new JButton("Download");
        JPanel flowSouth = new JPanel();
        flowSouth.add(downloadButton);
        tablePanel.add(flowSouth, BorderLayout.SOUTH);
        setTitle("Download Files");
        setSize(500, 500);
        setLocationRelativeTo(null);
    }

    private void setUpOngoingPanel() {
        ongoingPanel = new JPanel(new GridLayout(0, 1));
        mainPanel.add(ongoingPanel, BorderLayout.NORTH);
    }

    private void updateProgress() {
        // TODO: is this ever closed aswell? (Actually this is not private and
        // is changed when it is closed!)
        new Thread(new Runnable() {
            @Override
            public void run() {

                running = true;
                while (running) {
                    //System.out.println("Tick: " + this);

                    ongoingPanel.removeAll();
                    if (ongoingDownloads != null) {
                        for (final DownloadHandler handler : ongoingDownloads) {
                            if (!handler.isFinished()
                                    && handler.getTotalSize() > 0) {
                                JPanel south = new JPanel(new BorderLayout());
                                JPanel north = new JPanel(new BorderLayout());
                                double speed = handler.getCurrentSpeed() / 1024 / 2014;
                                north.add(new JLabel(handler.getFileName()
                                        + " (" + Math.round(speed * 100.0)
                                        / 100.0 + "MiB/s)"),
                                        BorderLayout.CENTER);
                                JProgressBar progress = new JProgressBar(0,
                                        handler.getTotalSize());
                                progress.setValue(handler.getCurrentProgress());
                                progress.setStringPainted(true);
                                south.add(progress, BorderLayout.CENTER);
                                JButton stopButton = new JButton("X");
                                stopButton
                                        .addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(
                                                    ActionEvent e) {
                                                handler.setFinished(true);
                                                ongoingDownloads
                                                        .remove(handler);
                                            }
                                        });
                                south.add(stopButton, BorderLayout.EAST);
                                ongoingPanel.add(north);
                                ongoingPanel.add(south);
                            } else {
                                ongoingDownloads.remove(handler);
                                JOptionPane.showMessageDialog(null,
                                        "Download complete");
                            }
                        }

                    }
                    revalidate();
                    repaint();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        ErrorLogger.log(e);
                        running = false;
                    }
                }
            }
        }).start();
    }

    /**
     * Adds a listener for pressing the download button.
     *
     * @param listener
     *            The listener to be added.
     */
    public void addDownloadFileListener(ActionListener listener) {
        downloadButton.addActionListener(listener);

        /*
         * Automatically click the download button when the listener has been
         * added to let the user choose where to save the files immediately. If
         * no files were selected, show a message dialog and close the
         * DownloadWindow. If downloads are ongoing, just display the progress
         * bars and let the user choose himself if he wants to download more
         * files.
         */
        setVisible(false);
        if (ongoingDownloads.size() == 0) {
            if (files.size() > 0) {
                downloadButton.doClick();
                setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "No files were selected.");
                dispose();
            }
        } else {
            setVisible(true);
        }
    }

    /**
     * @return files An ArrayList containing the FileData representing the
     *         files.
     */
    public ArrayList<FileData> getFiles() {
        return files;
    }
}
