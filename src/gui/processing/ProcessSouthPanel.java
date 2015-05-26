package gui.processing;

import gui.CustomButtonFactory;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ProcessSouthPanel extends JPanel {

    private JButton clearButton;
    private JButton startButton;



    public ProcessSouthPanel(){
        super();

        this.setPreferredSize(new Dimension(300, 30));

        clearButton = new JButton("Clear");
        startButton = new JButton("Start Processing");

        this.add(clearButton);
        this.add(startButton);

    }

}
