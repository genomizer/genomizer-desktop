package gui.processing;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import util.ExperimentData;
import controller.ProcessTabController;

public class ProcessTab  extends JPanel {
    private ProcessTabController processTabController;
    private ExperimentData selectedExperiment;

    public ProcessTab() {
        setPreferredSize(new Dimension(1225, 725));
        setMinimumSize(new Dimension(20000, 20000));
        this.setLayout(new BorderLayout());

        String[] s = {"RawToProf", "Ratio"};
        this.add(new CommandChooser(s,"123"),BorderLayout.NORTH);
    }

    public void setController(ProcessTabController processTabController) {
        this.processTabController = processTabController;
    }

    public void setSelectedExperiment(ExperimentData experimentData) {
        selectedExperiment = experimentData;
    }
}
