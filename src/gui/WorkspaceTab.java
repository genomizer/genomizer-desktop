package gui;

import util.ExperimentData;
import util.IconFactory;
import util.TreeTable;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class WorkspaceTab extends JPanel {

    private static final long serialVersionUID = -7278768268151806081L;
    private TreeTable table;
    private JPanel buttonPanel, filePanel;
    private JButton deleteButton, removeButton, downloadButton;
    private JButton analyzeButton, browseButton, processButton;
    
    public WorkspaceTab() {
        setLayout(new BorderLayout());
        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory
                .createTitledBorder("Workspace"));
        filePanel = new JPanel(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(filePanel, BorderLayout.CENTER);

        buttonPanel.setLayout(new FlowLayout());

        //buttonPanel.setBackground(new Color(210, 210, 210));
        filePanel.setBackground(Color.white);

        createButtons();
        addToButtonPanel();
        buttonPanel.setVisible(true);
        table = new TreeTable();
        // table.setContent(ExperimentData.getExample());
        filePanel.add(table, BorderLayout.CENTER);
        setVisible(true);
    }

    private void createButtons() {
        removeButton = new JButton(IconFactory.getClearIcon(50,50));
        removeButton.setRolloverIcon(IconFactory.getClearHoverIcon(52,52));
        removeButton.setBorderPainted(true);
        removeButton.setContentAreaFilled(false);
        removeButton.setFocusable(true);
        removeButton.setFocusPainted(false);
        removeButton.setPreferredSize(new Dimension(52,52));
        removeButton.setToolTipText("Remove selected from workspace");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.removeSelectedData();
            }
        });
        downloadButton = new JButton(IconFactory.getDownloadIcon(45,45));
        downloadButton.setRolloverIcon(IconFactory.getDownloadHoverIcon(47,47));
        downloadButton.setBorderPainted(true);
        downloadButton.setContentAreaFilled(false);
        downloadButton.setFocusable(true);
        downloadButton.setFocusPainted(false);
        downloadButton.setPreferredSize(new Dimension(47,47));
        downloadButton.setToolTipText("Download selected");
        
        analyzeButton = new JButton(IconFactory.getAnalyzeIcon(50,50));
        analyzeButton.setRolloverIcon(IconFactory.getAnalyzeHoverIcon(52,52));
        analyzeButton.setBorderPainted(true);
        analyzeButton.setContentAreaFilled(false);
        analyzeButton.setFocusable(true);
        analyzeButton.setFocusPainted(false);
        analyzeButton.setPreferredSize(new Dimension(52,52));
        analyzeButton.setEnabled(false);
        analyzeButton.setToolTipText("Analyze selected");
        
        processButton = new JButton(IconFactory.getProcessIcon(50,50));
        processButton.setRolloverIcon(IconFactory.getProcessHoverIcon(52,52));
        processButton.setBorderPainted(true);
        processButton.setContentAreaFilled(false);
        processButton.setFocusable(true);
        processButton.setFocusPainted(false);
        processButton.setPreferredSize(new Dimension(52,52));
        processButton.setToolTipText("Process selected");
        //processButton.setEnabled(false);
    }

    private void addToButtonPanel() {
        buttonPanel.add(removeButton);

        buttonPanel.add(Box.createHorizontalStrut(50));
        
        buttonPanel.add(downloadButton);
        
        buttonPanel.add(Box.createHorizontalStrut(50));

        buttonPanel.add(analyzeButton);
        
        buttonPanel.add(Box.createHorizontalStrut(50));

        buttonPanel.add(processButton);
        
        
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

    public void addAnalyzeSelectedListener(ActionListener listener) {
        analyzeButton.addActionListener(listener);
    }

    private String[] concatArrays(String[] first, String[] second) {
        ArrayList<String> both = new ArrayList<String>(first.length
                + second.length);
        Collections.addAll(both, first);
        Collections.addAll(both, second);
        return both.toArray(new String[both.size()]);
    }

    public void addExperimentsToTable(
            ArrayList<ExperimentData> newExperiments) {
        ArrayList<ExperimentData> expList = new ArrayList<ExperimentData>();
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
        System.out.println("setting content");
        table.setContent(expList);
        table.repaint();
        table.revalidate();
    }

    public ArrayList<ExperimentData> getSelectedData() {
        return table.getSelectedData();
    }
}
