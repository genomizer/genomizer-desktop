package genomizerdesktop;




import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;

public class SearchTab extends JPanel {

    /**
	 *
	 */
	private static final long serialVersionUID = -5281551884688417212L;
	private JPanel searchPanel;


    public SearchTab() {
	BorderLayout bl = new BorderLayout();
	setLayout(bl);
	setBackground(Color.blue);
	createSearchPanel();
	searchPanel.setBackground(Color.white);
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

	String[] sexStrings = { "Male", "Female" };
	String[] petStrings1 = { "whole", "Cat", "Dog", "Rabbit", "Pig5" };
	String[] petStrings2 = { "n", "Cat", "Dog", "Rabbit", "Pig4" };
	String[] petStrings3 = { "back", "Cat", "Dog", "Rabbit", "Pig3" };
	String[] petStrings4 = { "m", "Cat", "Dog", "Rabbit", "Pig2" };
	String[] petStrings5 = { "wrap", "Cat", "Dog", "Rabbit", "Pig1" };

	JComboBox caseCheckBox = new JComboBox(sexStrings);
	JComboBox wholeCheckBox = new JComboBox(petStrings1);
	JComboBox wrapCheckBox = new JComboBox(petStrings5);
	JComboBox backCheckBox = new JComboBox(petStrings3);
	JComboBox nCheckBox = new JComboBox(petStrings2);
	JComboBox mCheckBox = new JComboBox(petStrings4);
	JButton searchButton = new JButton("Search");
//	JButton cancelButton = new JButton("Cancel");

	gl.setHorizontalGroup(gl.createParallelGroup()
		.addComponent(searchLabel)
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
		                .addComponent(mCheckBox)))
		 .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
		        	.addComponent(searchButton))
		);
	gl.linkSize(SwingConstants.HORIZONTAL, searchButton);

	gl.setVerticalGroup(gl.createSequentialGroup()
		.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
    		        .addComponent(searchLabel))
    		    .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
   		        .addGroup(gl.createSequentialGroup()
   	    		     .addComponent(expID)
   	    		     .addComponent(searchButton)
    		            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
    		                .addComponent(caseCheckBox)
    		                .addComponent(wrapCheckBox)
    		                .addComponent(nCheckBox))
    		            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
    		                .addComponent(wholeCheckBox)
    		                .addComponent(backCheckBox)
    		                .addComponent(mCheckBox))))
    		);
    }

    public static void main(String args[]) {
	GUI g = new GUI();
	SearchTab st = new SearchTab();
	UploadTab ut = new UploadTab();
	WorkspaceTab wt = new WorkspaceTab();
	g.setSearchTab(st);
	g.setUploadTab(ut);
	g.setWorkspaceTab(wt);
    }
}

