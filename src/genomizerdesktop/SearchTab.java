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
	JLabel pubLabel = new JLabel("Publication ID");
	JTextField expID = new JTextField();
	JTextField pubID = new JTextField();

	String[] expStrings = { "0", "Female" };
	String[] expStrings1 = { "1", "Cat", "Dog", "Rabbit", "Pig5" };
	String[] expStrings2 = { "2", "Cat", "Dog", "Rabbit", "Pig4" };
	String[] expStrings3 = { "3", "Cat", "Dog", "Rabbit", "Pig3" };
	String[] expStrings4 = { "4", "Cat", "Dog", "Rabbit", "Pig2" };
	String[] expStrings5 = { "5", "Cat", "Dog", "Rabbit", "Pig1" };

	String[] pubStrings0 = { "0", "Cat", "Dog", "Rabbit", "Pig5" };
	String[] pubStrings1 = { "1", "Cat", "Dog", "Rabbit", "Pig5" };
	String[] pubStrings2 = { "2", "Cat", "Dog", "Rabbit", "Pig4" };
	String[] pubStrings3 = { "3", "Cat", "Dog", "Rabbit", "Pig3" };
	String[] pubStrings4 = { "4", "Cat", "Dog", "Rabbit", "Pig2" };
	String[] pubStrings5 = { "5", "Cat", "Dog", "Rabbit", "Pig1" };
	String[] pubStrings6 = { "6", "Cat", "Dog", "Rabbit", "Pig5" };
	String[] pubStrings7 = { "7", "Cat", "Dog", "Rabbit", "Pig4" };
	String[] pubStrings8 = { "8", "Cat", "Dog", "Rabbit", "Pig5" };

	JComboBox caseCheckBox = new JComboBox(expStrings);
	JComboBox wholeCheckBox = new JComboBox(expStrings1);
	JComboBox wrapCheckBox = new JComboBox(expStrings2);
	JComboBox backCheckBox = new JComboBox(expStrings3);
	JComboBox nCheckBox = new JComboBox(expStrings4);
	JComboBox mCheckBox = new JComboBox(expStrings5);

	JComboBox pubBox0 = new JComboBox(pubStrings0);
	JComboBox pubBox1 = new JComboBox(pubStrings1);
	JComboBox pubBox2 = new JComboBox(pubStrings2);
	JComboBox pubBox3 = new JComboBox(pubStrings3);
	JComboBox pubBox4 = new JComboBox(pubStrings4);
	JComboBox pubBox5 = new JComboBox(pubStrings5);
	JComboBox pubBox6 = new JComboBox(pubStrings6);
	JComboBox pubBox7 = new JComboBox(pubStrings7);
	JComboBox pubBox8 = new JComboBox(pubStrings8);

	JButton searchButton = new JButton("Search");

	gl.setHorizontalGroup(gl.createParallelGroup()
		.addComponent(searchLabel)
		.addGroup(gl.createSequentialGroup()
			.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(expLabel))
			.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(expID)
				.addGroup(gl.createSequentialGroup()
					.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(caseCheckBox)
						.addComponent(backCheckBox))
					.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(wholeCheckBox)
						.addComponent(nCheckBox))
					.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                		                .addComponent(wrapCheckBox)
                		                .addComponent(mCheckBox))))
                	.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                		.addComponent(pubLabel))
                	.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                		.addComponent(pubID)
                		.addGroup(gl.createSequentialGroup()
                			.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                				.addComponent(pubBox0)
                				.addComponent(pubBox1)
                				.addComponent(pubBox2))
                			.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                				.addComponent(pubBox3)
                				.addComponent(pubBox4)
                				.addComponent(pubBox5))
                			.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                				.addComponent(pubBox6)
                				.addComponent(pubBox7)
                				.addComponent(pubBox8))))
                	.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                		.addComponent(searchButton)))
		);
	gl.linkSize(SwingConstants.HORIZONTAL, searchButton);

	gl.setVerticalGroup(gl.createSequentialGroup()
		.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
    		        .addComponent(searchLabel))
   		        .addGroup(gl.createSequentialGroup()
   		        	.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
   		        		.addComponent(expLabel)
   		        		.addComponent(expID)
   		        		.addComponent(pubLabel)
   		        		.addComponent(pubID)
   		        		.addComponent(searchButton))
   		        	.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
   		        	.addGroup(gl.createSequentialGroup()
   		        		.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
   		        			.addComponent(caseCheckBox)
   		        			.addComponent(wholeCheckBox)
   		        			.addComponent(wrapCheckBox)
   		        			.addComponent(pubBox0)
                				.addComponent(pubBox3)
                				.addComponent(pubBox6))
   		        		.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                		                .addComponent(backCheckBox)
                		                .addComponent(nCheckBox)
                		                .addComponent(mCheckBox)
                		                .addComponent(pubBox1)
                				.addComponent(pubBox4)
                				.addComponent(pubBox7))
                			.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        			.addComponent(pubBox2)
                        			.addComponent(pubBox5)
                        			.addComponent(pubBox8)))))

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

