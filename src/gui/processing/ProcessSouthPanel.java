package gui.processing;

import gui.CustomButtonFactory;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ProcessSouthPanel extends JPanel {

    private JButton clearButton;
    private JButton startButton;



    public ProcessSouthPanel(){
        super();

        clearButton = new JButton("Clear");
        startButton = new JButton("Start Processing");

        this.add(clearButton);
        this.add(startButton);

    }

    public void addClearButtonListener( ActionListener l ){
        clearButton.addActionListener(l);
    }

    public void addStartButtonListener( ActionListener l ){
        startButton.addActionListener(l);
    }

}
