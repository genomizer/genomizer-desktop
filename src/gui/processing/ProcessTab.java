package gui.processing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import util.ExperimentData;
import util.ProcessFeedbackData;
import controller.ProcessTabController;

@SuppressWarnings("serial")
public class ProcessTab extends JPanel {

    private ProcessTabController processTabController;
    private ExperimentData selectedExperiment;
    private CommandChooser chooser;
    private CommandScrollPane scrollPane;
    private ProcessInfoPanel infoPanel;
    private ProcessSouthPanel southPanel;

    public ProcessTab() {
        setPreferredSize(new Dimension(1225, 725));
        setMinimumSize(new Dimension(20000, 20000));
        this.setLayout(new BorderLayout());

        this.chooser = new CommandChooser(CommandStrings.COMMAND_NAMES);
        this.add(chooser, BorderLayout.NORTH);

        this.scrollPane = new CommandScrollPane();
        this.add(scrollPane, BorderLayout.CENTER);

        this.infoPanel = new ProcessInfoPanel();
        this.add(infoPanel, BorderLayout.EAST);

        this.southPanel = new ProcessSouthPanel();
        this.add(southPanel, BorderLayout.SOUTH);

    }

    public void setController(ProcessTabController processTabController) {
        this.processTabController = processTabController;
    }

    public void reset(String experimentName) {

        this.scrollPane.empty();

        this.chooser.setExperiment(experimentName);

        this.revalidate();
        this.repaint();

    }

    public String getSelectedCommand() {
        return chooser.getSelectedCommand();
    }

    public void addCommand(String selectedCommand, String[] fileNames,  String[] genomeReleases ) {

        CommandComponent commandComponent = null;

        if (selectedCommand .equalsIgnoreCase(RawToProfileCommandComponent.COMMAND_NAME)) {

            commandComponent = new RawToProfileCommandComponent(fileNames, genomeReleases);

        } else if (selectedCommand.equalsIgnoreCase(RatioCommandComponent.COMMAND_NAME)) {

            commandComponent = new RatioCommandComponent(fileNames);

        } else if (selectedCommand.equalsIgnoreCase(SmoothingCommandComponent.COMMAND_NAME)) {

            commandComponent = new SmoothingCommandComponent(fileNames);

        } else if (selectedCommand.equalsIgnoreCase(StepCommandComponent.COMMAND_NAME)) {

            commandComponent = new StepCommandComponent(fileNames);

        } else if (commandComponent == null) {

            return;
        }

        this.scrollPane.addCommandComponent(commandComponent);
        this.revalidate();
        this.repaint();

    }

    public void addChooserListener(ActionListener chooserListener) {
        chooser.addChoiceListener(chooserListener);

    }

    public void addClearListener(ActionListener clearListener) {
        southPanel.addClearButtonListener(clearListener);

    }

    public void clearCommands() {
        this.scrollPane.empty();

    }

    public void showProcessFeedback(ProcessFeedbackData[] processFeedbackData) {
        infoPanel.showProcessFeedback(processFeedbackData);
    }

    public void addFeedbackListener(ActionListener processFeedbackListener) {
        infoPanel.addProcessFeedbackListener(processFeedbackListener);
    }

    public void addAbortProcessListener(ActionListener abortListener) {
        infoPanel.addAbortProcessListener(abortListener);
    }

    public ProcessFeedbackData getSelectedProcessFeedback() {
        return infoPanel.getSelectedProcess();
    }

    public CommandScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public void addProcessButtonListener(ActionListener processButtonListener) {
        this.southPanel.addStartButtonListener(processButtonListener);

    }

}
