package controller;

import gui.ErrorDialog;
import gui.GUI;
import gui.processing.CommandScrollPane;
import gui.processing.ProcessCommand;
import gui.processing.ProcessTab;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import communication.ConnectionFactory;

import requests.Request;
import requests.RequestFactory;

import util.ProcessFeedbackData;
import util.RequestException;

import model.GenomizerModel;
import model.ProcessModel;

public class ProcessTabController {
    
    private ProcessTab tab;
    private ProcessModel model;
    private boolean deletedProcessFiles = false;
    
    public ProcessTabController(ProcessTab tab) {
        this.tab = tab;
        
        model = new ProcessModel();
        
        // processTab.addRawToProfileDataListener(RawToProfileDataListener());
        
        // fileListAddMouseListener(view.getProcessTab().getFileList());
        
        // processTab.addProcessFeedbackListener(ProcessFeedbackListener());
        
        // processTab.addDeleteSelectedListener(DeleteSelectedListener());
        
        // ChooseCommand
        tab.addChooserListener(chooserListener());
        
        // Clear command
        tab.addClearListener(clearListener());
        
        // Entries
        // Start Process
        tab.addProcessButtonListener(new ProcessButtonListener(tab
                .getScrollPane()));
        
        // Check Process
        tab.addFeedbackListener(processFeedbackListener());
        
        // Abort Process
        tab.addAbortProcessListener(abortProcessListener());
        
    }
    
    private ActionListener clearListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab.clearCommands();
            }
        };
    }
    
    private ActionListener chooserListener() {
        return new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                tab.addCommand(tab.getSelectedCommand());
            }
        };
    }
    
    // TODO: refactor, move
    private class ProcessButtonListener implements ActionListener {
        
        private CommandScrollPane scrollPane;
        
        public ProcessButtonListener(CommandScrollPane scrollPane) {
            this.scrollPane = scrollPane;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            ProcessCommand[] commandList = scrollPane.getCommandList();
            System.out.println(RequestFactory.makeProcessCommandRequest("0",
                    commandList).toJson());
            
        }
        
    }
    
    //
    // private void fileListAddMouseListener(JList<?> fileList) {
    // fileList.addMouseListener(new MouseAdapter() {
    // String species = "";
    // int count = 0;
    //
    // @Override
    // public void mouseClicked(MouseEvent event) {
    // JList<?> list = (JList<?>) event.getSource();
    //
    //
    // if (deletedProcessFiles) {
    // species = "";
    // count = 0;
    // }
    //
    // if (list.getModel().getSize() > 0) {
    // int index = list.locationToIndex(event.getPoint());
    //
    // CheckListItem item = (CheckListItem) list.getModel()
    // .getElementAt(index);
    // if (count == 0) {
    // species = "";
    // }
    // if (species.equals("") && count == 0) {
    // species = item.getSpecie();
    // }
    // if (item.getSpecie().equals(species)) {
    //
    // item.setSelected(!item.isSelected());
    //
    // GenomeReleaseData[] genome = model
    // .getSpeciesGenomeReleases(item.getSpecie());
    // if (view.getProcessTab().getAllMarkedFiles().isEmpty()) {
    // view.getProcessTab().setGenomeFileList(null);
    // } else {
    // view.getProcessTab().setGenomeFileList(genome);
    // }
    //
    // if (item.isSelected()) {
    // count++;
    // } else {
    // count--;
    // }
    // }
    // deletedProcessFiles = false;
    // list.repaint(list.getCellBounds(index, index));
    // }
    // }
    // });
    // }
    
    /**
     * The listener to create profile data, Sends a request to the server for
     * every RAW-file that the user wants to create profile data.
     * 
     * @author c11ann
     */
    // public ActionListener RawToProfileDataListener() {
    // return new ActionListener() {
    //
    // @Override
    // public void actionPerformed(ActionEvent e) {
    // new Thread() {
    // @Override
    // public void run() {
    //
    // ArrayList<FileData> allMarked = view.getProcessTab()
    // .getAllMarkedFiles();
    // String message;
    // Boolean allRaw = false;
    //
    // for (FileData raw : allMarked) {
    // allRaw = raw.type.equals("Raw");
    // }
    //
    // // DANIEL
    // if (allRaw) {
    // if (view.isCorrectToProcess()
    // && view.isRatioCorrectToProcess()) {
    // if (!allMarked.isEmpty()) {
    //
    // for (FileData data : allMarked) {
    //
    // String fileName = data.filename;
    // String author = view.getLoginWindow()
    // .getUsernameInput();
    // String parameters[] = new String[8];
    // String[] processParameters = view
    // .getProcessTab()
    // .getRegularParameters();
    // parameters[0] = processParameters[0];
    // parameters[1] = "";
    // parameters[2] = processParameters[2];
    // parameters[3] = processParameters[3];
    // parameters[4] = processParameters[4];
    // parameters[5] = processParameters[5];
    //
    // if (view.getProcessTab().useRatio()) {
    // String[] ratioParameters = view
    // .getRatioCalcPopup()
    // .getRatioCalcParameters();
    // parameters[6] = ratioParameters[0]; // "single 4 0";
    // parameters[7] = ratioParameters[1]; // "150 1 7 0 0";
    // } else {
    // parameters[6] = "";
    // parameters[7] = "";
    // }
    //
    // String expid = data.expId;
    // String genomeVersion = processParameters[1];
    // String metadata = parameters[0] + " "
    // + parameters[1] + " "
    // + parameters[2] + " "
    // + parameters[3] + " "
    // + parameters[4] + " "
    // + parameters[5] + " "
    // + parameters[6] + " "
    // + parameters[7];
    //
    // // Sends a request to create profile
    // // data from raw
    // // files.
    // try {
    // model.rawToProfile(expid,
    // parameters, metadata,
    // genomeVersion, author);
    // message = "The server has started process on file: "
    // + fileName
    // + " from experiment: "
    // + expid + "\n\n";
    // view.getProcessTab()
    // .printToConsole(message);
    // } catch (RequestException e) {
    // message = "WARNING - The server couldn't start processing on file: "
    // + fileName
    // + " from experiment: "
    // + expid + "\n\n";
    // view.getProcessTab()
    // .printToConsole(message);
    // new ErrorDialog("Data process failed", e).showDialog();
    // }
    // }
    // }
    // }
    // } else {
    // message = "One or more selected files are not raw files! \n\n";
    // view.getProcessTab().printToConsole(message);
    // }
    //
    // };
    // }.start();
    // }
    // };
    // }
    
    public ActionListener processFeedbackListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        final ProcessFeedbackData[] processFeedbackData = model
                                .getProcessFeedback();
                        if (processFeedbackData != null
                                && processFeedbackData.length > 0) {
                            
                            SwingUtilities.invokeLater(new Runnable() {
                                
                                @Override
                                public void run() {
                                    
                                    tab.showProcessFeedback(processFeedbackData);
                                }
                            });
                        }
                        
                    };
                }.start();
            }
        };
    }
    
    public ActionListener abortProcessListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO skicka request till server att avbryta processen som
                // �r markerad
                
                final ProcessFeedbackData data = tab
                        .getSelectedProcessFeedback();
                
                if (data == null) {
                    new ErrorDialog(
                            "Invalid selection",
                            "Make sure you have selected your process correctly",
                            "Select a single process, and make sure the selection is above or at the 'Process ID' value.")
                            .showDialog();
                    return;
                }
                
                new Thread() {
                    @Override
                    public void run() {
                        
                        try {
                            model.abortProcess(data.PID);
                        } catch (RequestException e) {
                            
                            final RequestException e2 = e;
                            SwingUtilities.invokeLater(new Runnable() {
                                
                                public void run() {
                                    new ErrorDialog("Couldn't abort process!",
                                            e2).showDialog();
                                }
                            });
                        }
                    }
                    
                }.start();
                
            }
        };
    }
    // public ActionListener DeleteSelectedListener() {
    // return new ActionListener() {
    // @Override
    // public void actionPerformed(ActionEvent e) {
    // new Thread() {
    // @Override
    // public void run() {
    //
    // ArrayList<FileData> markedFiles = view.getProcessTab()
    // .getAllMarkedFiles();
    //
    // ArrayList<ExperimentData> exData = view.getProcessTab()
    // .getFileInfo();
    //
    // if (exData != null && markedFiles != null) {
    //
    // for (ExperimentData data : exData) {
    // data.files.removeAll(markedFiles);
    // }
    // view.getProcessTab().setFileInfo(exData);
    // deletedProcessFiles = true;
    // }
    //
    // };
    // }.start();
    // }
    // };
    // }
}
