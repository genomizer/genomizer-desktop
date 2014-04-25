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
	SearchTab st = new SearchTab();
	g.setSearchTab(st);
    }
}

