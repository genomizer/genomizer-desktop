package genomizerdesktop;




import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagLayout;

import javax.swing.*;

public class SearchTab {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // For test purpose
//    private JFrame frame;

    private JPanel mainPanel;
    private JPanel searchPanel;


    public SearchTab() {
	// For test purpose
//	frame = new JFrame("Genomizer");
//	frame.setSize(800, 800);
//	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	BorderLayout bl = new BorderLayout();
	mainPanel = new JPanel(bl);
//	frame.add(mainPanel);
	mainPanel.setBackground(Color.green);
	createSearchPanel();
	searchPanel.setBackground(Color.red);
	mainPanel.add(searchPanel, BorderLayout.NORTH);

//	frame.setVisible(true);
    }

    private void createSearchPanel() {
	searchPanel = new JPanel();
	GroupLayout gl = new GroupLayout(searchPanel);
	searchPanel.setLayout(gl);
	gl.setAutoCreateGaps(true);
	gl.setAutoCreateContainerGaps(true);


	JLabel label = new JLabel("Test");
	JTextField textField = new JTextField();

	JCheckBox caseCheckBox = new JCheckBox("TEST CASE");
	JCheckBox wholeCheckBox = new JCheckBox("TEST WHOLE");
	JCheckBox wrapCheckBox = new JCheckBox("TEST WRAP");
	JCheckBox backCheckBox = new JCheckBox("BACK TEST");
	JButton findButton = new JButton("Find");
	JButton cancelButton = new JButton("Cancel");

	gl.setHorizontalGroup(gl.createSequentialGroup()
		    .addComponent(label)
		    .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addComponent(textField)
		        .addGroup(gl.createSequentialGroup()
		            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addComponent(caseCheckBox)
		                .addComponent(wholeCheckBox))
		            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addComponent(wrapCheckBox)
		                .addComponent(backCheckBox))))
		    .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addComponent(findButton)
		        .addComponent(cancelButton))
		);
		gl.linkSize(SwingConstants.HORIZONTAL, findButton, cancelButton);

		gl.setVerticalGroup(gl.createSequentialGroup()
		    .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
		        .addComponent(label)
		        .addComponent(textField)
		        .addComponent(findButton))
		    .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(gl.createSequentialGroup()
		            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
		                .addComponent(caseCheckBox)
		                .addComponent(wrapCheckBox))
		            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
		                .addComponent(wholeCheckBox)
		                .addComponent(backCheckBox)))
		        .addComponent(cancelButton))
		);
    }

    public static void main(String args[]) {
	GUI g = new GUI();
//	g.setSearchTab(new SearchTab());
    }
}

