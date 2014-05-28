package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import util.ExperimentData;
import util.TreeTable;

import communication.DownloadHandler;

public class WorkspaceTab extends JPanel {
    
    private static final long serialVersionUID = -7278768268151806081L;
    private TreeTable table;
    private JPanel buttonPanel;
    private JButton deleteButton, removeButton, downloadButton;
    private JButton uploadToButton, processButton, ongoingDownloadsButton;
    private final JTabbedPane tabbedPane;
    private JPanel ongoingDownloadsPanel;
    private JPanel bottomPanel;
    private JPanel filePanel;
    private CopyOnWriteArrayList<DownloadHandler> ongoingDownloads;
    
    public WorkspaceTab() {
        setLayout(new BorderLayout());
        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Workspace"));
        filePanel = new JPanel(new BorderLayout());
        table = new TreeTable();
        filePanel.add(table, BorderLayout.CENTER);
        bottomPanel = new JPanel(new BorderLayout());
        ongoingDownloadsPanel = new JPanel(new GridLayout(0, 1));
        buttonPanel.setLayout(new FlowLayout());
        createButtons();
        addToButtonPanel();
        tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
        tabbedPane.addTab("Workspace", filePanel);
        tabbedPane.addTab("Ongoing Downloads", bottomPanel);
        bottomPanel.add(ongoingDownloadsPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        updateOngoingDownloadsPanel();
        setVisible(true);
    }
    
    private void createButtons() {
        deleteButton = new JButton("Delete from database");
        deleteButton.setPreferredSize(new Dimension(190, 40));
        removeButton = new JButton("Remove from workspace");
        removeButton.setPreferredSize(new Dimension(190, 40));
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedData();
            }
        });
        downloadButton = new JButton("Download");
        downloadButton.setPreferredSize(new Dimension(150, 40));
        uploadToButton = new JButton("Upload to");
        uploadToButton.setPreferredSize(new Dimension(150, 40));
        processButton = new JButton("Process");
        processButton.setPreferredSize(new Dimension(150, 40));
    }
    
    public void setOngoingDownloads(
            CopyOnWriteArrayList<DownloadHandler> ongoingDownloads) {
        this.ongoingDownloads = ongoingDownloads;
    }
    
    private void addToButtonPanel() {
        buttonPanel.add(deleteButton);
        
        buttonPanel.add(Box.createHorizontalStrut(50));
        
        buttonPanel.add(removeButton);
        
        buttonPanel.add(Box.createHorizontalStrut(50));
        
        buttonPanel.add(downloadButton);
        
        buttonPanel.add(Box.createHorizontalStrut(50));
        
        buttonPanel.add(uploadToButton);
        
        buttonPanel.add(Box.createHorizontalStrut(50));
        
        buttonPanel.add(processButton);
        
    }
    
    private void updateOngoingDownloadsPanel() {
        final CopyOnWriteArrayList<DownloadHandler> completedDownloads = new CopyOnWriteArrayList<DownloadHandler>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean running = true;
                while (running) {
                    ongoingDownloadsPanel.removeAll();
                    if (ongoingDownloads != null) {
                        for (final DownloadHandler handler : ongoingDownloads) {
                            if (!handler.isFinished()
                                    && handler.getTotalSize() > 0) {
                                JPanel downloadPanel = new JPanel(
                                        new BorderLayout());
                                double speed = handler.getCurrentSpeed() / 1024 / 2014;
                                
                                JProgressBar progress = new JProgressBar(0,
                                        handler.getTotalSize());
                                progress.setValue(handler.getCurrentProgress());
                                progress.setStringPainted(true);
                                JButton stopButton = new JButton("X");
                                stopButton
                                        .addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(
                                                    ActionEvent e) {
                                                ongoingDownloads
                                                        .remove(handler);
                                            }
                                        });
                                downloadPanel
                                        .add(progress, BorderLayout.CENTER);
                                downloadPanel
                                        .add(stopButton, BorderLayout.EAST);
                                downloadPanel.add(
                                        new JLabel(handler.getFileName() + " ("
                                                + Math.round(speed * 100.0)
                                                / 100.0 + "MiB/s)"),
                                        BorderLayout.NORTH);
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
                        completedDownloadPanel.add(progress,
                                BorderLayout.CENTER);
                        completedDownloadPanel.add(stopButton,
                                BorderLayout.EAST);
                        completedDownloadPanel.add(
                                new JLabel(handler.getFileName() + " Completed"),
                                BorderLayout.NORTH);
                        ongoingDownloadsPanel.add(completedDownloadPanel);
                    }
                    ongoingDownloadsPanel.revalidate();
                    ongoingDownloadsPanel.repaint();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        running = false;
                    }
                }
            }
        }).start();
    }
    
    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    public void addDownloadFileListener(ActionListener listener) {
        downloadButton.addActionListener(listener);
    }
    
    public void addProcessFileListener(ActionListener listener) {
        processButton.addActionListener(listener);
    }
    
    public void addUploadToListener(ActionListener listener) {
        uploadToButton.addActionListener(listener);
    }
    
    public void addDeleteSelectedListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
    
    public void addExperimentsToTable(ArrayList<ExperimentData> newExperiments) {
        ArrayList<ExperimentData> expList = new ArrayList<>();
        if (table.getContent() != null) {
            expList.addAll(table.getContent());
        }
        for (ExperimentData newExperiment : newExperiments) {
            boolean alreadyInTable = false;
            for (ExperimentData existingExperiment : expList) {
                if (newExperiment.name.equals(existingExperiment.name)) {
                    alreadyInTable = true;
                    existingExperiment.addFiles(newExperiment.files);
                    break;
                }
            }
            if (!alreadyInTable) {
                expList.add(newExperiment);
            }
        }
        
        table.setContent(expList);
    }
    
    public ArrayList<ExperimentData> getSelectedData() {
        return table.getSelectedData();
    }
    
    public ArrayList<ExperimentData> getSelectedExperiments() {
        return table.getSelectedExperiments();
    }
    
    public synchronized void removeSelectedData() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                table.removeSelectedData();
            }
        });
    }
    
    public void changeTab(int tabIndex) {
        tabbedPane.setSelectedIndex(tabIndex);
    }
}
