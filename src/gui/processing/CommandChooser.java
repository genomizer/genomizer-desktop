package gui.processing;

import gui.CustomButtonFactory;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.IconFactory;

public class CommandChooser extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -6950495327883207465L;

    private JComboBox<String> commands;
    private JButton addCommandBoxButton;
    private JTextField expName;

    private final String defaultString =  "<no selected experiment>";

    public CommandChooser(String[] commandNames ) {
        super();

        this.setBorder(BorderFactory.createTitledBorder("Choose Command"));

        this.commands = new JComboBox<String>(commandNames);
        this.commands.setPreferredSize(new Dimension(240, 30));
        this.commands.setEnabled(false);

        this.addCommandBoxButton = CustomButtonFactory.makeCustomButton(
                IconFactory.getPlusIcon(20, 20),
                IconFactory.getPlusIcon(24, 24), 25, 25, "Add new command");
        this.addCommandBoxButton.setEnabled(false);

        expName = new JTextField( defaultString );
        expName.setEditable(false);
        expName.setPreferredSize(new Dimension(240, 30));

        this.add(new JLabel("<html><b>Experiment ID</b></html>"));
        this.add(expName);
        this.add(this.commands);
        this.add(addCommandBoxButton);

    }

    public String getSelectedCommand() {
        return (String) commands.getSelectedItem();
    }

    public void addChoiceListener(ActionListener chooserListener) {
        addCommandBoxButton.addActionListener(chooserListener);

    }

    public void setExperiment(String name) {
        expName.setText(name);
        this.addCommandBoxButton.setEnabled(true);
        this.commands.setEnabled(true);
    }

    public void resetChooser(){
        this.commands.setEnabled(false);
        this.expName.setEditable(false);
        this.expName.setText(defaultString);
    }

}
