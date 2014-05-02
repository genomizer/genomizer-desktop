package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class QuerySearchTab extends JPanel {
    private JPanel searchPanel;
    private JPanel builderPanel;
    private JPanel rowsPanel;
    private JPanel searchEast;
    private JPanel searchWest;
    private JPanel resultPanel;
    private JScrollPane searchScroll;
    private JScrollPane resultScroll;
    private JButton clearButton;
    private JButton searchButton;
    private JTextArea searchArea;
    private JTable resultTable;
    private ArrayList<RowBuilder> rowList;
    private GridBagConstraints gbc;
    private JTextArea resultTest;
    private static final String[] logicOperators = { "AND", "NOT", "OR" };
    private static final String[] annotations = { "Uploader", "Date", "Sex",
	    "Species", "ExperimentID", "Value", "Name" };

    public QuerySearchTab() {
	this.setLayout(new BorderLayout());
	setPreferredSize(new Dimension(700, 700));
	createSearchPanel();
	add(searchPanel, BorderLayout.NORTH);
	createBuilderPanel();
	clearSearchFields();
	createResultPanel();
	add(resultScroll, BorderLayout.SOUTH);
    }

    private void createResultPanel() {
	resultPanel = new JPanel();
	resultScroll = new JScrollPane(resultPanel);
	// Test purpose
	resultTest = new JTextArea();
	resultPanel.add(resultTest);
    }

    public void addSearchResult(ArrayList<HashMap<String, String>> searchResults) {
	resultTest.setText("");
	int i = 0;
	String key;
	resultTest.append("searchResult: \n");
	for (HashMap<String, String> resultMap : searchResults) {
	    resultTest.append(i + "\n");
	    Iterator it = resultMap.keySet().iterator();
	    while (it.hasNext()) {
		key = (String) it.next();
		resultTest.append("key: " + key + " value: "
			+ resultMap.get(key) + "\n\n");
	    }
	    i++;
	}
    }

    private void createSearchPanel() {
	rowList = new ArrayList<RowBuilder>();
	setBorder(BorderFactory
		.createTitledBorder("Genomizer Advanced Search Builder"));
	// FlowLayout fl = new FlowLayout();
	BorderLayout bl = new BorderLayout();
	searchPanel = new JPanel(bl);
	searchButton = new JButton("Search");
	clearButton = new JButton("Clear");
	clearButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		clearSearchFields();
	    }
	});
	searchArea = new JTextArea(
		"Use the builder below to create your search");
	searchArea.setLineWrap(true);
	searchArea.setSize(850, 20);
	searchWest = new JPanel();
	searchWest.add(searchArea);
	searchScroll = new JScrollPane(searchWest);
	searchScroll.setSize(850, 20);
	// searchPanel.add(searchArea);
	searchPanel.add(searchScroll, BorderLayout.CENTER);
	searchEast = new JPanel(new FlowLayout());
	searchEast.add(clearButton);
	searchEast.add(searchButton);
	searchPanel.add(searchEast, BorderLayout.EAST);
	// searchPanel.add(clearButton);
	// searchPanel.add(searchButton);
    }

    private void createBuilderPanel() {
	builderPanel = new JPanel(new BorderLayout());
	rowsPanel = new JPanel(new GridLayout(0, 1));
	builderPanel.add(rowsPanel, BorderLayout.NORTH);
	JScrollPane scroll = new JScrollPane(builderPanel);
	add(scroll, BorderLayout.CENTER);
    }

    private void clearSearchFields() {
	rowList = new ArrayList<RowBuilder>();
	addRow();
	searchArea.setText("Use the builder below to create your search");
	builderPanel.repaint();
	builderPanel.revalidate();
	searchPanel.repaint();
	searchPanel.revalidate();
    }

    public void addRow() {
	rowList.add(new RowBuilder(this));
	showRows();
    }

    public void removeRow(RowBuilder row) {
	if (rowList.contains(row)) {
	    rowList.remove(row);
	}
	showRows();
    }

    private void showRows() {
	rowsPanel.removeAll();
	for (int i = 0; i < rowList.size(); i++) {
	    RowBuilder row = rowList.get(i);
	    if (i == 0 && i == (rowList.size() - 1)) {
		row.setAs(true, true);
	    } else if (i == 0 && i != (rowList.size() - 1)) {
		row.setAs(true, false);
	    } else if (i != 0 && i == (rowList.size() - 1)) {
		row.setAs(false, true);
	    } else {
		row.setAs(false, false);
	    }
	    rowsPanel.add(row);
	}
	rowsPanel.repaint();
	rowsPanel.revalidate();
    }

    public synchronized void updateSearchArea() {
	String searchString = "";
	int i = 0;
	for (RowBuilder row : rowList) {
	    if (!row.getText().isEmpty()) {
		String logic = "";
		String endParantesis = "";
		if (i == 0) {
		    logic = "";
		} else {
		    logic = row.getLogic() + " ";
		    searchString = "(" + searchString;
		    endParantesis = ") ";
		}
		String text = row.getText();
		String annotation = row.getAnnotation();
		searchString = searchString + endParantesis + logic + text
			+ "[" + annotation + "]";
		i++;
	    }
	}
	searchArea.setText(searchString);
    }

    public void addSearchButtonListener(ActionListener listener) {
	searchButton.addActionListener(listener);
    }

    public String getSearchString() {
	return searchArea.getText();
    }

    private class RowBuilder extends JPanel {
	private JComboBox annotationField;
	private JTextField textField;
	private JButton plusButton;
	private JButton minusButton;
	private JComboBox logicField;
	private QuerySearchTab parent;

	public RowBuilder(QuerySearchTab parent) {
	    this.parent = parent;
	    setLayout(new FlowLayout());
	    setPlusButton();
	    setMinusButton();
	    setLogicBox();
	    setFieldBox();
	    setTextField();
	}

	public void setAs(Boolean firstRow, Boolean lastRow) {
	    removeAll();

	    if (firstRow && lastRow) {
		add(Box.createHorizontalStrut(73));
		add(annotationField);
		add(textField);
		add(plusButton);
		add(Box.createHorizontalStrut(20));
	    } else if (firstRow && !lastRow) {
		add(Box.createHorizontalStrut(73));
		add(annotationField);
		add(textField);
		add(minusButton);
		add(Box.createHorizontalStrut(20));
	    } else if (!firstRow && !lastRow) {
		add(logicField);
		add(annotationField);
		add(textField);
		add(minusButton);
		add(Box.createHorizontalStrut(20));
	    } else {
		add(logicField);
		add(annotationField);
		add(textField);
		add(minusButton);
		add(plusButton);
	    }
	}

	private void setPlusButton() {
	    plusButton = new JButton();
	    ImageIcon plusIcon = new ImageIcon("src/icons/plus.png");
	    plusIcon = new ImageIcon(plusIcon.getImage().getScaledInstance(15,
		    15, BufferedImage.SCALE_SMOOTH));
	    plusButton.setBorderPainted(true);
	    plusButton.setContentAreaFilled(false);
	    plusButton.setPreferredSize(new Dimension(20, 20));
	    plusButton.setFocusable(true);
	    plusButton.setFocusPainted(false);
	    plusButton.setIcon(plusIcon);
	    plusButton.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent e) {
		    parent.addRow();
		}
	    });
	}

	private void setMinusButton() {
	    minusButton = new JButton();
	    ImageIcon minusIcon = new ImageIcon("src/icons/minus.png");
	    minusIcon = new ImageIcon(minusIcon.getImage().getScaledInstance(
		    15, 15, BufferedImage.SCALE_SMOOTH));
	    minusButton.setBorderPainted(true);
	    minusButton.setContentAreaFilled(false);
	    minusButton.setPreferredSize(new Dimension(20, 20));
	    minusButton.setFocusable(true);
	    minusButton.setFocusPainted(false);
	    minusButton.setIcon(minusIcon);
	    final RowBuilder row = this;
	    minusButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    parent.removeRow(row);
		    parent.updateSearchArea();
		}
	    });

	}

	private void setTextField() {
	    textField = new JTextField(50);
	    textField.getDocument().addDocumentListener(new DocumentListener() {
		public void changedUpdate(DocumentEvent e) {
		    parent.updateSearchArea();
		}

		public void removeUpdate(DocumentEvent e) {
		    parent.updateSearchArea();
		}

		public void insertUpdate(DocumentEvent e) {
		    parent.updateSearchArea();
		}
	    });
	}

	private void setFieldBox() {
	    annotationField = new JComboBox(annotations);
	    annotationField.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    parent.updateSearchArea();
		}
	    });
	}

	private void setLogicBox() {
	    logicField = new JComboBox(logicOperators);
	    logicField.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    parent.updateSearchArea();
		}
	    });
	}

	public String getText() {
	    return textField.getText();
	}

	public String getLogic() {
	    return (String) logicField.getSelectedItem();
	}

	public String getAnnotation() {
	    return (String) annotationField.getSelectedItem();
	}
    }
}
