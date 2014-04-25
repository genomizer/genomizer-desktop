package genomizerdesktop;




import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagLayout;

import javax.swing.*;

public class SearchTab extends JPanel {

    private JPanel searchPanel;


    public SearchTab() {
	BorderLayout bl = new BorderLayout();
	setLayout(bl);
	setBackground(Color.green);
	createSearchPanel();
	searchPanel.setBackground(Color.red);
	add(searchPanel, BorderLayout.NORTH);
    }

    private void createSearchPanel() {
	searchPanel = new JPanel();
	GroupLayout gl = new GroupLayout(searchPanel);
	searchPanel.setLayout(gl);
	gl.setAutoCreateGaps(true);
	gl.setAutoCreateContainerGaps(true);


	JLabel searchLabel = new JLabel("Search");
	JLabel expLabel = new JLabel("Experiment ID");
	JTextField expID = new JTextField();

	String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };

	JComboBox caseCheckBox = new JComboBox(petStrings);
	JCheckBox wholeCheckBox = new JCheckBox("TEST WHOLE");
	JCheckBox wrapCheckBox = new JCheckBox("TEST WRAP");
	JCheckBox backCheckBox = new JCheckBox("BACK TEST");
	JCheckBox nCheckBox = new JCheckBox("TEST n");
	JCheckBox mCheckBox = new JCheckBox("m TEST");
	JButton searchButton = new JButton("Search");
//	JButton cancelButton = new JButton("Cancel");

	gl.setHorizontalGroup(gl.createSequentialGroup()
		    .addComponent(searchLabel)
		    .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addComponent(expID)
		        .addGroup(gl.createSequentialGroup()
		            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addComponent(caseCheckBox)
		                .addComponent(wholeCheckBox))
		            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addComponent(wrapCheckBox)
		                .addComponent(backCheckBox))
		            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addComponent(nCheckBox)
		                .addComponent(mCheckBox))))
		     .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
			     .addComponent(searchButton))
		);
	gl.linkSize(SwingConstants.HORIZONTAL, searchButton);

	gl.setVerticalGroup(gl.createSequentialGroup()
		.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
    			.addComponent(searchLabel)
    			.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
    		        .addGroup(gl.createSequentialGroup()
    		            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
    		        .addComponent(expID)
    		    .addComponent(searchButton))
    		    .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
    		        .addGroup(gl.createSequentialGroup()
    		            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
    		                .addComponent(caseCheckBox)
    		                .addComponent(wrapCheckBox)
    		                .addComponent(nCheckBox))
    		            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
    		                .addComponent(wholeCheckBox)
    		                .addComponent(backCheckBox)
    		                .addComponent(mCheckBox)))))))
    		);

//	gl.setHorizontalGroup(gl.createSequentialGroup()
//		    .addComponent(searchLabel)
//		    .addComponent(expLabel)
//		    .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
//		        .addComponent(expID)
//		        .addGroup(gl.createSequentialGroup()
//		            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
//		                .addComponent(caseCheckBox)
//		                .addComponent(wholeCheckBox))
//		            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
//		                .addComponent(wrapCheckBox)
//		                .addComponent(backCheckBox))))
//		    .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
//		        .addComponent(searchButton))
//		);
//		gl.linkSize(SwingConstants.HORIZONTAL, searchButton);
//
//		gl.setVerticalGroup(gl.createSequentialGroup()
//		    .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
//		        .addComponent(searchLabel)
//		        .addComponent(expLabel)
//		        .addComponent(expID)
//		        .addComponent(searchButton))
//		    .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
//		        .addGroup(gl.createSequentialGroup()
//		            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
//		                .addComponent(caseCheckBox)
//		                .addComponent(wrapCheckBox))
//		            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
//		                .addComponent(wholeCheckBox)
//		                .addComponent(backCheckBox))))
//		);
    }

    public static void main(String args[]) {
	GUI g = new GUI();
	SearchTab st = new SearchTab();
	g.setSearchPanel(st);
    }
}

