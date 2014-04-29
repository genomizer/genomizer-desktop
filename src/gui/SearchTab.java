package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

public class SearchTab extends JPanel {

    /**
	 *
	 */
    private static final long serialVersionUID = -5281551884688417212L;

    private JPanel searchPanel;
    private JPanel resultPanel;

    private JLabel searchLabel;
    private JLabel expLabel;
    private JLabel pubLabel;
    private JLabel dataLabel;

    private JTextField expID;
    private JTextField pubID;

    private String[] expStrings0 = { "0", "Female" };
    private String[] expStrings1 = { "1", "Cat", "Dog", "Rabbit", "Pig5" };
    private String[] expStrings2 = { "2", "Cat", "Dog", "Rabbit", "Pig4" };
    private String[] expStrings3 = { "3", "Cat", "Dog", "Rabbit", "Pig3" };
    private String[] expStrings4 = { "4", "Cat", "Dog", "Rabbit", "Pig2" };
    private String[] expStrings5 = { "5", "Cat", "Dog", "Rabbit", "Pig1" };

    private String[] pubStrings0 = { "0", "Cat", "Dog", "Rabbit", "Pig5" };
    private String[] pubStrings1 = { "1", "Cat", "Dog", "Rabbit", "Pig5" };
    private String[] pubStrings2 = { "2", "Cat", "Dog", "Rabbit", "Pig4" };
    private String[] pubStrings3 = { "3", "Cat", "Dog", "Rabbit", "Pig3" };
    private String[] pubStrings4 = { "4", "Cat", "Dog", "Rabbit", "Pig2" };
    private String[] pubStrings5 = { "5", "Cat", "Dog", "Rabbit", "Pig1" };
    private String[] pubStrings6 = { "6", "Cat", "Dog", "Rabbit", "Pig5" };
    private String[] pubStrings7 = { "7", "Cat", "Dog", "Rabbit", "Pig4" };
    private String[] pubStrings8 = { "8", "Cat", "Dog", "Rabbit", "Pig5" };

    private JComboBox expBox0;
    private JComboBox expBox1;
    private JComboBox expBox2;
    private JComboBox expBox3;
    private JComboBox expBox4;
    private JComboBox expBox5;

    private JComboBox pubBox0;
    private JComboBox pubBox1;
    private JComboBox pubBox2;
    private JComboBox pubBox3;
    private JComboBox pubBox4;
    private JComboBox pubBox5;
    private JComboBox pubBox6;
    private JComboBox pubBox7;
    private JComboBox pubBox8;

    private JButton searchButton;

    private JCheckBox rawCheckBox;
    private JCheckBox regionCheckBox;
    private JCheckBox profileCheckBox;

    private JTable resultTable;

    private JScrollPane resultScroll;

    public SearchTab() {
	BorderLayout bl = new BorderLayout();
	searchPanel = new JPanel();
	resultPanel = new JPanel(new GridLayout());
	resultScroll = new JScrollPane();
	setLayout(bl);
	setBackground(Color.blue);
	createSearchPanel();
	searchPanel.setBackground(Color.white);
	add(searchPanel, BorderLayout.NORTH);
	createResultPanel();
	add(resultPanel, BorderLayout.CENTER);
	resultPanel.setBackground(Color.blue);
    }

    private void createSearchPanel() {
	GroupLayout gl = new GroupLayout(searchPanel);
	searchPanel.setLayout(gl);
	gl.setAutoCreateGaps(true);
	gl.setAutoCreateContainerGaps(true);

	initiateSearchOptions();

	gl.setHorizontalGroup(gl
		.createParallelGroup()
		.addComponent(searchLabel)
		.addGroup(
			gl.createSequentialGroup()
				.addGroup(
					gl.createParallelGroup(
						GroupLayout.Alignment.LEADING)
						.addComponent(expLabel))
				.addGroup(
					gl.createParallelGroup(
						GroupLayout.Alignment.LEADING)
						.addComponent(expID)
						.addGroup(
							gl.createSequentialGroup()
								.addGroup(
									gl.createParallelGroup(
										GroupLayout.Alignment.LEADING)
										.addComponent(
											expBox0)
										.addComponent(
											expBox3))
								.addGroup(
									gl.createParallelGroup(
										GroupLayout.Alignment.LEADING)
										.addComponent(
											expBox1)
										.addComponent(
											expBox4))
								.addGroup(
									gl.createParallelGroup(
										GroupLayout.Alignment.LEADING)
										.addComponent(
											expBox2)
										.addComponent(
											expBox5))))
				.addGroup(
					gl.createParallelGroup(
						GroupLayout.Alignment.LEADING)
						.addComponent(pubLabel))
				.addGroup(
					gl.createParallelGroup(
						GroupLayout.Alignment.LEADING)
						.addComponent(pubID)
						.addGroup(
							gl.createSequentialGroup()
								.addGroup(
									gl.createParallelGroup(
										GroupLayout.Alignment.LEADING)
										.addComponent(
											pubBox0)
										.addComponent(
											pubBox3)
										.addComponent(
											pubBox6))
								.addGroup(
									gl.createParallelGroup(
										GroupLayout.Alignment.LEADING)
										.addComponent(
											pubBox1)
										.addComponent(
											pubBox4)
										.addComponent(
											pubBox7))
								.addGroup(
									gl.createParallelGroup(
										GroupLayout.Alignment.LEADING)
										.addComponent(
											pubBox2)
										.addComponent(
											pubBox5)
										.addComponent(
											pubBox8))))
				.addGroup(
					gl.createParallelGroup(
						GroupLayout.Alignment.LEADING)
						.addComponent(searchButton)
						.addComponent(dataLabel)
						.addComponent(rawCheckBox)
						.addComponent(regionCheckBox)
						.addComponent(profileCheckBox))));

	gl.linkSize(SwingConstants.HORIZONTAL, searchButton);

	gl.setVerticalGroup(gl
		.createSequentialGroup()
		.addGroup(
			gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(searchLabel))
		.addGroup(
			gl.createSequentialGroup()
				.addGroup(
					gl.createParallelGroup(
						GroupLayout.Alignment.LEADING)
						.addComponent(expLabel)
						.addComponent(expID)
						.addComponent(pubLabel)
						.addComponent(pubID)
						.addComponent(searchButton))
				.addGroup(
					gl.createParallelGroup(
						GroupLayout.Alignment.LEADING)
						.addGroup(
							gl.createSequentialGroup()
								.addGroup(
									gl.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(
											expBox0)
										.addComponent(
											expBox1)
										.addComponent(
											expBox2)
										.addComponent(
											pubBox0)
										.addComponent(
											pubBox1)
										.addComponent(
											pubBox2)
										.addComponent(
											dataLabel))
								.addGroup(
									gl.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(
											expBox3)
										.addComponent(
											expBox4)
										.addComponent(
											expBox5)
										.addComponent(
											pubBox3)
										.addComponent(
											pubBox4)
										.addComponent(
											pubBox5)
										.addComponent(
											rawCheckBox))
								.addGroup(
									gl.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(
											pubBox6)
										.addComponent(
											pubBox7)
										.addComponent(
											pubBox8)
										.addComponent(
											regionCheckBox))
								.addGroup(
									gl.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(
											profileCheckBox)))))

	);
    }

    private void initiateSearchOptions() {
	searchLabel = new JLabel("Search");
	expLabel = new JLabel("Experiment ID");
	pubLabel = new JLabel("Publication ID");
	dataLabel = new JLabel("Data to display");

	expID = new JTextField();
	pubID = new JTextField();

	expBox0 = new JComboBox(expStrings0);
	expBox1 = new JComboBox(expStrings1);
	expBox2 = new JComboBox(expStrings2);
	expBox3 = new JComboBox(expStrings3);
	expBox4 = new JComboBox(expStrings4);
	expBox5 = new JComboBox(expStrings5);

	pubBox0 = new JComboBox(pubStrings0);
	pubBox1 = new JComboBox(pubStrings1);
	pubBox2 = new JComboBox(pubStrings2);
	pubBox3 = new JComboBox(pubStrings3);
	pubBox4 = new JComboBox(pubStrings4);
	pubBox5 = new JComboBox(pubStrings5);
	pubBox6 = new JComboBox(pubStrings6);
	pubBox7 = new JComboBox(pubStrings7);
	pubBox8 = new JComboBox(pubStrings8);

	searchButton = new JButton("Search");

	rawCheckBox = new JCheckBox("Raw");
	regionCheckBox = new JCheckBox("Region");
	profileCheckBox = new JCheckBox("Profile");

    }

    private void createResultPanel() {
	initiateTable();
	resultPanel.add(resultTable.getTableHeader());
	resultScroll = new JScrollPane(resultTable);
	resultPanel.add(resultScroll);
    }

    public void initiateTable() {
	String[] columnNames = { "Experiment ID", "Publication ID", "Species",
		"Sex" };
	Object[][] data = { { "0", "1", "Human", "F" },
		{ "1", "1", "Human", "M" }, { "2", "1", "Human", "M" },
		{ "3", "1", "Humanoid", "F" }, { "4", "none", "Human", "M" } };
	resultTable = new JTable(data, columnNames);
	resultTable.setBackground(Color.blue);
	resultTable.setForeground(Color.white);
	resultTable.setFont(new Font("Arial", Font.PLAIN, 24));
	TableColumn column = new TableColumn();
	column = null;
	for (int i = 0; i < columnNames.length; i++) {
	    column = resultTable.getColumnModel().getColumn(i);
	    column.setPreferredWidth(200);
	}

	/*
	 * Försökte göra så man kunde expanda i tabellen med hjälp av en
	 * JXTreeTable,
	 * men fick inte till det. Om nån annan vill försöka så varsågod:
	 *
 	List<String> columnNames = new ArrayList<String>();
	columnNames.add("Experiment ID");
	columnNames.add("Publication ID");
	columnNames.add("Species");
	columnNames.add("Sex");

	Object[][] data = { { "0", "1", "Human", "F" },
		{ "1", "1", "Human", "M" }, { "2", "1", "Human", "M" },
		{ "3", "1", "Humanoid", "F" }, { "4", "none", "Human", "M" } };

    TreeTableModel treeTableModel = new DefaultTreeTableModel(
    		new DefaultMutableTreeTableNode(), columnNames);
	JXTreeTable resultTable = new JXTreeTable(treeTableModel);
	JScrollPane scrollpane = new JScrollPane(resultTable);

	for(int i=0; i<data.length; i++) {

		treeTableModel.insertNodeInto();
	}

//	resultTable = new JTable(data, columnNames);

	resultTable.setBackground(Color.blue);
	resultTable.setForeground(Color.white);
	resultTable.setFont(new Font("Arial", Font.PLAIN, 24));
	TableColumn column = new TableColumn();
	column = null;
	for (int i = 0; i < columnNames.size(); i++) {
	    column = resultTable.getColumnModel().getColumn(i);
	    column.setPreferredWidth(200);
	}
	 */
    }

    public static void main(String args[]) {
	GUI g = new GUI();
	SearchTab st = new SearchTab();
	UploadTab ut = new UploadTab();
	WorkspaceTab wt = new WorkspaceTab();
	g.setSearchTab(st);
	g.setUploadTab(ut);
	g.setWorkspaceTab(wt);
	g.setVisible(true);
    }
}
