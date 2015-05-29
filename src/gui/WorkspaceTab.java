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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.ErrorLogger;
import model.User;

import util.ExperimentData;
import util.TreeTable;

import communication.DownloadHandler;
import controller.WorkspaceTabController;

/**
 * A class representing a workspace tab in a view part of an application used by
 * genome researchers. This class allows the user to delete files from the
 * database, download files, upload files to current experiment, process
 * experiment and remove files from the workspace.
 *
 * @author
 */
public class WorkspaceTab extends JPanel {

    private static final long serialVersionUID = -7278768268151806081L;

    public static final int DOWNLOADTABNUMBER = 1;

    private TreeTable table;
    private JPanel buttonPanel;
    private JButton deleteButton, removeButton, downloadButton;
    private JButton uploadToButton, processButton, ongoingDownloadsButton,
            convertButton;
    private JTabbedPane tabbedPane;
    private JPanel ongoingDownloadsPanel;
    private JPanel bottomPanel;
    private JPanel filePanel;
    private JScrollPane bottomScroll;

    /**
     * Constructor creating the workspace tab.
     */
    public WorkspaceTab() {
        setLayout(new BorderLayout());
        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Workspace"));
        filePanel = new JPanel(new BorderLayout());
        table = new TreeTable();
        filePanel.add(table, BorderLayout.CENTER);
        bottomPanel = new JPanel(new BorderLayout());
        bottomScroll = new JScrollPane(bottomPanel);
        bottomScroll.setBorder(BorderFactory.createEmptyBorder());
        ongoingDownloadsPanel = new JPanel(new GridLayout(0, 1));
        buttonPanel.setLayout(new FlowLayout());
        createButtons();
        addToButtonPanel();
        bottomPanel.add(ongoingDownloadsPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.NORTH);
        setTabbedPane();
        setVisible(true);
    }

    private void setTabbedPane() {
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.addTab("Workspace", filePanel);
        tabbedPane.addTab("Downloads", bottomScroll);
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                boolean b = false;
                if (tabbedPane.getSelectedIndex() == 0) {
                    b = true;
                }
                deleteButton.setEnabled(b);
                removeButton.setEnabled(b);
                processButton.setEnabled(b);
                convertButton.setEnabled(b);
                uploadToButton.setEnabled(b);
                downloadButton.setEnabled(b);

            }
        });
        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * A method creating the buttons of the workspace tab.
     */
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
        convertButton = new JButton("Convert");
        convertButton.setPreferredSize(new Dimension(150, 40));
    }

    /**
     * Method adding the buttons to the button panel.
     */
    private void addToButtonPanel() {



            buttonPanel.add(deleteButton);

            buttonPanel.add(Box.createHorizontalStrut(10));


        buttonPanel.add(removeButton);

        buttonPanel.add(Box.createHorizontalStrut(10));

        buttonPanel.add(downloadButton);

        buttonPanel.add(Box.createHorizontalStrut(10));


            buttonPanel.add(uploadToButton);

            buttonPanel.add(Box.createHorizontalStrut(10));

            buttonPanel.add(processButton);

            buttonPanel.add(Box.createHorizontalStrut(10));

            buttonPanel.add(convertButton);

    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            return null;
        }
    }

    /**
     * Method adding a listener to the "downloadButton" button.
     *
     * @see controller.WorkspaceTabController#DownloadFileListener()
     * @param listener
     *            The listener to start downloading files.
     */
    public void addDownloadFileListener(ActionListener listener) {
        downloadButton.addActionListener(listener);
    }

    /**
     * Method adding a listener to the "processButton" button.
     *
     * @see controller.WorkspaceTabController#ProcessFileListener()
     * @param listener
     *            The listener to start processing experiment.
     */
    public void addProcessFileListener(ActionListener listener) {
        processButton.addActionListener(listener);
    }

    /**
     * Method adding a listener to the "processButton" button.
     *
     * @see controller.WorkspaceTabController#ProcessFileListener()
     * @param listener
     *            The listener to start processing experiment.
     */
    public void addConvertFileListener(ActionListener listener) {
        convertButton.addActionListener(listener);
    }

    /**
     * Method adding a listener to the "uploadButton" button. OR Method adding a
     * listener to the analyze selected button.
     *
     * @param listener
     *            The listener to start uploading files to a current experiment.
     *            OR The listener
     */
    public void addUploadToListener(ActionListener listener) {
        uploadToButton.addActionListener(listener);
    }

    /**
     * Method adding a listener to the "deleteButton" button.
     *
     * @see controller.WorkspaceTabController#DeleteFromDatabaseListener()
     * @param listener
     *            The listener to delete an experiment from the database.
     */
    public void addDeleteSelectedListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    /**
     * Method adding experiments to the workspace tab.<br>
     * OR <br>
     * Adds the provided ExperimentDatas to the workspaceTab.
     *
     * @param newExperiments
     *            An array with experiments to be added.<br>
     *            OR.<br>
     *            The ArrayList of ExperimentData to be added.
     */
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

    /**
     * Method returning the data of selected experiment(s).
     *
     * @return an array with data of the current selected experiment(s).<br>
     *         OR<br>
     *         The selected data in the workspace in the form of an arrayList
     *         containing the ExperimentData.
     */

    public ArrayList<ExperimentData> getSelectedData() {
        return table.getSelectedData();
    }

    /**
     * Method returning the selected experiment(s).
     *
     * @return an array with the current selected experiment(s).
     */
    public ArrayList<ExperimentData> getSelectedExperiments() {
        return table.getSelectedExperiments();
    }

    /**
     * Method removing the selected data.
     */
    public synchronized void removeSelectedData() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                table.removeSelectedData();
            }
        });
    }

    /**
     * Method changing the shown tab.
     *
     * @param tabIndex
     *            The index of the tab to be shown.
     */
    public void changeTab(int tabIndex) {
        tabbedPane.setSelectedIndex(tabIndex);
    }

    public JPanel getOngoingDownloadsPanel() {
        return ongoingDownloadsPanel;
    }
}
