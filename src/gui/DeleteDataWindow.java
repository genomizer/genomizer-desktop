package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Class for loginwindow. Presents a window that prompts for a username,
 * password and server with port. User authorization
 */
public class DeleteDataWindow extends JFrame {
    
    private static final long serialVersionUID = -150623476066679412L;
    private JProgressBar progressBar;
    
    /**
     * Constructor creating the login window and adding listeners.
     */
    public DeleteDataWindow(final GenomizerView parent) {
        setTitle("Deleting data");
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200, 60);
        setResizable(false);
        this.setLocationRelativeTo(parent.getFrame());
        
        placeComponents();
    }
    
    /**
     * Sets the layout and looks to the login window
     */
    private void placeComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        mainPanel.add(progressBar);
        add(mainPanel);
    }
    
    public void updateProgress(int progress, int total) {
        progressBar.setMinimum(0);
        progressBar.setMaximum(total);
        progressBar.setValue(progress);
    }
    
}
