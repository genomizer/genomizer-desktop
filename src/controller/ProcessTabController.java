package controller;

import gui.ErrorDialog;
import gui.processing.CommandScrollPane;
import gui.processing.ProcessCommand;
import gui.processing.ProcessTab;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import util.ExperimentData;
import util.ProcessFeedbackData;
import util.RequestException;

import model.ProcessModel;

public class ProcessTabController {

    private ProcessTab tab;
    private ProcessModel model;

    public ProcessTabController(ProcessTab tab, ProcessModel model) {

        this.tab = tab;
        this.model = model;

        // ChooseCommand
        tab.addChooserListener(chooserListener());

        // Clear command
        tab.addClearListener(clearListener());

        // Entries
        // Start Process
        tab.addProcessButtonListener(processButtonListener());

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

                String [] files = model.getFileNames();
                String [] filenames = new String[files.length+1];

                for( int i = 0; i < files.length; i++ )
                    filenames[i+1] = files[i];
                filenames[0] = "";

                tab.addCommand(tab.getSelectedCommand(), filenames,
                        model.getGenomeReleases());
            }
        };
    }

    // TODO: refactor, move
    private ActionListener processButtonListener() {

        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                CommandScrollPane scrollPane = tab.getScrollPane();
                final ProcessCommand[] commandList = scrollPane
                        .getCommandList();
                final String pid = model.getSelectedExperimentName();

                new Thread() {
                    @Override
                    public void run() {
                        try {

                            model.startProcessing(pid, commandList);

                            SwingUtilities.invokeLater(new Runnable() {

                                public void run() {
                                    tab.clearCommands();
                                }
                            });

                        } catch (RequestException e) {

                            final RequestException e2 = e;
                            SwingUtilities.invokeLater(new Runnable() {

                                public void run() {
                                    new ErrorDialog(
                                            "Couldn't start processing!", e2)
                                            .showDialog();
                                }
                            });
                        }
                    }
                }.start();

            }
        };

    }

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

                            // Also update the feedback
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
}
