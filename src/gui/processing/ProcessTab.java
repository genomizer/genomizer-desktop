package gui.processing;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import util.Constants;

import util.ExperimentData;
import controller.ProcessTabController;

public class ProcessTab extends JPanel {

    private ProcessTabController processTabController;
    private ExperimentData selectedExperiment;

    public ProcessTab() {
        setPreferredSize(new Dimension(1225, 725));
        setMinimumSize(new Dimension(20000, 20000));
        this.setLayout(new BorderLayout());

        this.add(new CommandChooser(Constants.commands,
                "<no selected experiment>"), BorderLayout.NORTH);
    }

    public void setController(ProcessTabController processTabController) {
        this.processTabController = processTabController;
    }

    public void setSelectedExperiment(ExperimentData experimentData) {
        selectedExperiment = experimentData;
    }

    public void reset(ExperimentData experiment) {

        this.removeAll();
        this.add(new CommandChooser(Constants.commands, experiment.getName()),
                BorderLayout.NORTH);

        this.revalidate();
        this.repaint();

    }
}
