package gui.processing;

import gui.CustomButtonFactory;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

    public CommandChooser( String[] commandNames, String expID ) {
        super();

        this.setBorder(BorderFactory.createTitledBorder("Choose Command"));

        this.commands = new JComboBox<String>(commandNames);
        this.commands.setPreferredSize(new Dimension(240,30));

        this.addCommandBoxButton = CustomButtonFactory
                .makeCustomButton(IconFactory.getPlusIcon(15, 15),
                        IconFactory.getPlusIcon(17, 17), 17, 25,
                        "Add new command");

        JTextField tf = new JTextField(expID);
        tf.setEditable(false);
        tf.setPreferredSize(new Dimension(240,30));

        this.add(new JLabel("<html><b>Experiment ID</b></html>"));
        this.add(tf);
        this.add(this.commands);
        this.add(addCommandBoxButton);

    }

    public String getSelectedCommand(){
        return (String) commands.getSelectedItem();
    }




}
