package genomizerdesktop;

import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI implements GenomizerView {

	private JFrame frame;
	private JPanel panel;

    public GUI() {
	frame = new JFrame("Genomizer");
	frame.setSize(400, 800);

	panel = new JPanel();
	frame.add(panel);
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
    }

    public JFrame getFrame() {
	return frame;
    }
}
