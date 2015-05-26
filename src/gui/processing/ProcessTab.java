package gui.processing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import util.Constants;
import util.ExperimentData;
import controller.ProcessTabController;

public class ProcessTab extends JPanel {

    private ProcessTabController processTabController;
    private ExperimentData selectedExperiment;
    private CommandChooser chooser;

    public ProcessTab() {
        setPreferredSize(new Dimension(1225, 725));
        setMinimumSize(new Dimension(20000, 20000));
        this.setLayout(new BorderLayout());

        this.chooser = new CommandChooser(Constants.commands,
                "<no selected experiment>");
        this.add(chooser, BorderLayout.NORTH);
    }

    public void setController(ProcessTabController processTabController) {
        this.processTabController = processTabController;
    }

    public void reset(ExperimentData experiment) {

        this.removeAll();

        this.chooser = new CommandChooser(Constants.commands,
                experiment.getName());
        this.add(chooser, BorderLayout.NORTH);

        this.revalidate();
        this.repaint();

    }

    public String getSelectedCommand() {
        return chooser.getSelectedCommand();
    }

    public void addCommand(String selectedCommand) {

        // Check for multiple similiar command

        // Add new command tab
        String []s = {"abc"};
        this.add( new RawToProfileCommandComponent("AF", s,  s));

    }

    public void addChooserListener(ActionListener chooserListener) {
        chooser.addChoiceListener(chooserListener);

    }
}
