package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JList;

import model.GenomizerModel;

import util.ExperimentData;
import util.FileData;
import util.GenomeReleaseData;
import util.ProcessFeedbackData;
import gui.CheckListItem;
import gui.GenomizerView;

public class ProcessTabController {
    GenomizerView view;
    GenomizerModel model;
    private boolean deletedProcessFiles = false;
    
    public ProcessTabController(GenomizerView view, GenomizerModel model) {
        this.view = view;
        this.model = model;
        view.addRawToProfileDataListener(RawToProfileDataListener());
        fileListAddMouseListener(view.getfileList());
        view.addProcessFeedbackListener(ProcessFeedbackListener());
        view.addDeleteSelectedListener(DeleteSelectedListener());
    }
    
    private void fileListAddMouseListener(JList fileList) {
        fileList.addMouseListener(new MouseAdapter() {
            String species = "";
            int count = 0;
            
            @Override
            public void mouseClicked(MouseEvent event) {
                JList list = (JList) event.getSource();
                
                if (deletedProcessFiles) {
                    species = "";
                    count = 0;
                }
                
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
                    deletedProcessFiles = false;
                    list.repaint(list.getCellBounds(index, index));
                }
            }
        });
    }
    
    /**
     * The listener to create profile data, Sends a request to the server for
     * every RAW-file that the user wants to create profile data.
     * 
     * @author c11ann
     */
    public ActionListener RawToProfileDataListener() {
        return new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        
                        view.setBowtieParameters();
                        ArrayList<FileData> allMarked = view
                                .getAllMarkedFiles();
                        String message;
                        Boolean isConverted;
                        Boolean allRaw = false;
                        
                        for (FileData raw : allMarked) {
                            allRaw = raw.type.equals("Raw");
                        }
                        
                        // DANIEL
                        if (allRaw) {
                            if (view.isCorrectToProcess()
                                    && view.isRatioCorrectToProcess()) {
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
                                            parameters[6] = view
                                                    .getRatioCalcParameters()[0]; // "single 4 0";
                                            parameters[7] = view
                                                    .getRatioCalcParameters()[1]; // "150 1 7 0 0";
                                        } else {
                                            parameters[6] = "";
                                            parameters[7] = "";
                                        }
                                        
                                        String expid = data.expId;
                                        String genomeVersion = view
                                                .getParameters()[1];
                                        String metadata = parameters[0] + " "
                                                + parameters[1] + " "
                                                + parameters[2] + " "
                                                + parameters[3] + " "
                                                + parameters[4] + " "
                                                + parameters[5] + " "
                                                + parameters[6] + " "
                                                + parameters[7];
                                        
                                        // Sends a request to create profile
                                        // data from raw
                                        // files.
                                        isConverted = model.rawToProfile(expid,
                                                parameters, metadata,
                                                genomeVersion, author);
                                        
                                        if (isConverted) {
                                            message = "The server has started process on file: "
                                                    + fileName
                                                    + " from experiment: "
                                                    + expid + "\n\n";
                                            view.printToConsole(message);
                                            
                                        } else {
                                            message = "WARNING - The server couldn't start processing on file: "
                                                    + fileName
                                                    + " from experiment: "
                                                    + expid + "\n\n";
                                            view.printToConsole(message);
                                        }
                                    }
                                }
                            }
                        } else {
                            message = "One or more selected files are not raw files! \n\n";
                            view.printToConsole(message);
                        }
                        
                    };
                }.start();
            }
        };
    }
    
    public ActionListener ProcessFeedbackListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        
                        ProcessFeedbackData[] processFeedbackData = model
                                .getProcessFeedback();
                        if (processFeedbackData != null
                                && processFeedbackData.length > 0) {
                            view.showProcessFeedback(processFeedbackData);
                        }
                        
                    };
                }.start();
            }
        };
    }
    
    public ActionListener DeleteSelectedListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        
                        ArrayList<FileData> markedFiles = view
                                .getAllMarkedFiles();
                        ArrayList<ExperimentData> exData = view.getFileInfo();
                        
                        if (exData != null && markedFiles != null) {
                            
                            for (ExperimentData data : exData) {
                                data.files.removeAll(markedFiles);
                            }
                            view.setFileInfo(exData);
                            deletedProcessFiles = true;
                        }
                        
                    };
                }.start();
            }
        };
    }
}
