package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class QuerySearchTab extends JPanel {
    private JPanel searchPanel;
    private JPanel builderPanel;
    private JTextArea searchArea;
    private JLabel advancedSearch;

    public QuerySearchTab() {
	createSearchPanel();
	BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);
	setLayout(bl);
	add(searchPanel);
	addRows(2);
    }

    private void createSearchPanel() {
	GridBagLayout gb = new GridBagLayout();
	searchPanel =  new JPanel(gb);
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.fill = GridBagConstraints.VERTICAL;
	gbc.anchor = GridBagConstraints.NORTH;
	gbc.gridx = 0;
	gbc.gridy = 0;
	searchArea = new JTextArea("Use the builder below to create your search");
	advancedSearch = new JLabel("Genomizer Advanced Search Builder");
	searchArea.setLineWrap(true);
	searchArea.setSize(1000,20);
	searchPanel.add(advancedSearch, gbc);
	gbc.gridy++;
	searchPanel.add(searchArea, gbc);
    }

    private void addRows(int nrOfRows) {
	GridBagLayout gb = new GridBagLayout();
	builderPanel =  new JPanel(gb);
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.fill = GridBagConstraints.VERTICAL;
	gbc.anchor = GridBagConstraints.FIRST_LINE_START;
	gbc.gridx = 0;
	gbc.gridy = 0;
	for(int i = 0 ; i < nrOfRows ; i++) {
	    boolean firstRow;
	    boolean lastRow;
	    if(i == 0) {
		firstRow = true;
	    } else {
		firstRow = false;
	    }
	    if(i == (nrOfRows - 1)) {
		lastRow = true;
	    } else {
		lastRow = false;
	    }
	    builderPanel.add(new RowBuilder(firstRow, lastRow), gbc);
	    gbc.gridy++;
	}
	add(builderPanel);
    }

    private class RowBuilder extends JPanel {
	private JComboBox field;
	private JTextField textField;
	private JButton plus;
	private JButton minus;
	private JComboBox logic;
	private boolean firstRow;
	private boolean lastRow;

	public RowBuilder(boolean firstRow, boolean lastRow) {
	    this.firstRow = firstRow;
	    this.lastRow = lastRow;
	    createRow();
	}

	private void createRow() {
	    FlowLayout fl = new FlowLayout();
	    setLayout(fl);
	    if(!firstRow) {
		setLogicBox();
		add(logic);
	    }
	    setFieldBox();
	    add(field);
	    textField = new JTextField(50);
	    add(textField);
	    minus = new JButton("Minus");
	    add(minus);
	    if(lastRow) {
		plus = new JButton("Plus");
		add(plus);
	    }

	}

	private void setFieldBox() {
	    String[] fields = {"Author", "Book"};
	    field = new JComboBox(fields);
	}

	private void setLogicBox() {
	    String[] logics = {"AND", "OR", "NOT"};
	    logic = new JComboBox(logics);
	}
    }
}
