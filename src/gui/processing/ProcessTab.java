package gui.processing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import util.Constants;
import util.ExperimentData;
import util.FileData;
import util.ProcessFeedbackData;
import controller.ProcessTabController;

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

        this.chooser = new CommandChooser(Constants.commands);
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

    public void reset(ExperimentData experiment) {

        this.scrollPane.empty();

        this.chooser.setExperiment(experiment.getName());
        this.selectedExperiment = experiment;

        this.revalidate();
        this.repaint();

    }

    public String getSelectedCommand() {
        return chooser.getSelectedCommand();
    }

    public void addCommand(String selectedCommand) {

        System.out.println("ADD NEW COMMAND: " + selectedCommand);

        // Check for multiple similiar command

        // Add new command tabs
        String[] fileNames = getFileNames();
        String[] genomeReleases = getGenomeReleases();
        this.scrollPane.addCommandComponent(new RawToProfileCommandComponent(fileNames, genomeReleases));
        this.revalidate();
        this.repaint();

    }

    private String[] getGenomeReleases() {

        ArrayList<String> genomeReleaseList = new ArrayList<String>();
        Iterator<FileData> fileIterator = selectedExperiment.files.iterator();
        for(int i = 0; fileIterator.hasNext(); i++) {
            String genomeRelease = fileIterator.next().grVersion;
            if(!genomeReleaseList.contains(genomeRelease)) {
                genomeReleaseList.add(genomeRelease);
            }
        }
        String[] genomeReleases = new String[genomeReleaseList.size()];
        genomeReleases = (String[]) genomeReleaseList.toArray(genomeReleases);
        return genomeReleases;
    }

    private String[] getFileNames() {

        String[] fileNames = new String[selectedExperiment.files.size()];
        Iterator<FileData> fileIterator = selectedExperiment.files.iterator();
        for(int i = 0; fileIterator.hasNext(); i++) {
            fileNames[i] = fileIterator.next().filename;
        }
        return fileNames;
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

    public String getSelectedExperiment() {
        return selectedExperiment.getName();
    }
}
