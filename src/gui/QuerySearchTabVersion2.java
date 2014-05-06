package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import util.AnnotationData;
import util.ExperimentData;
import util.FileData;

public class QuerySearchTabVersion2 extends JPanel {
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JPanel rowsPanel;
	private JPanel searchPanel;
	private JPanel resultsHeaderPanel;
	private JPanel filesHeaderPanel;
	private JButton clearButton;
	private JButton searchButton;
	private JButton workspaceButton;
	private JTextArea searchArea;
	private ArrayList<QueryBuilderRow> rowList;
	private JTable resultsTable;
	private JTable filesTable;
	private ExperimentData[] currentSearchResult;

	public QuerySearchTabVersion2() {
		setUpQuerySearchTab();
		setUpSearchHeader();
		setUpRowsPanel();
		setUpResultsTable();
		setUpFilesTable();
		setUpResultsHeaderPanel();
		setUpFilesHeaderPanel();
		showSearchView();
	}

	private void showSearchView() {
		topPanel.removeAll();
		bottomPanel.removeAll();
		topPanel.add(searchPanel);
		bottomPanel.add(rowsPanel, BorderLayout.NORTH);
		repaint();
		revalidate();
	}

	private void showResultsView() {
		topPanel.removeAll();
		bottomPanel.removeAll();
		topPanel.add(resultsHeaderPanel);
		bottomPanel.add(resultsTable.getTableHeader(), BorderLayout.NORTH);
		bottomPanel.add(resultsTable, BorderLayout.CENTER);
		repaint();
		revalidate();
	}

	private void showFilesView() {
		topPanel.removeAll();
		bottomPanel.removeAll();
		topPanel.add(filesHeaderPanel);
		bottomPanel.add(filesTable.getTableHeader(), BorderLayout.NORTH);
		bottomPanel.add(filesTable, BorderLayout.CENTER);
		repaint();
		revalidate();
	}

	private void setUpQuerySearchTab() {
		rowList = new ArrayList<QueryBuilderRow>();
		currentSearchResult = new ExperimentData[0];
		setBorder(BorderFactory
				.createTitledBorder("Genomizer Advanced Search Builder"));
		this.setLayout(new BorderLayout());
		bottomPanel = new JPanel(new BorderLayout());
		topPanel = new JPanel(new BorderLayout());
		JScrollPane bottomScroll = new JScrollPane(bottomPanel);
		bottomScroll.setBorder(BorderFactory.createEmptyBorder());
		add(topPanel, BorderLayout.NORTH);
		add(bottomScroll, BorderLayout.CENTER);
	}

	private void setUpResultsTable() {
		resultsTable = new JTable();
		resultsTable.setBackground(new Color(210, 210, 210));
		resultsTable.setBackground(new Color(210, 210, 210));
		resultsTable.setAutoCreateRowSorter(true);
		resultsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) { // check if a double click
					int row = resultsTable.getSelectedRow();
					int column = resultsTable.getColumnModel().getColumnIndex(
							"Experiment Name");
					System.out.println(row + " " + column);
					String expName = (String) resultsTable.getValueAt(row,
							column);
					for (int i = 0; i < currentSearchResult.length; i++) {
						if (currentSearchResult[i].name.equals(expName)) {
							filesTable
									.setModel(getFilesModel(currentSearchResult[i].files));
							showFilesView();
						}
					}
				}
			}
		});
	}

	private void setUpFilesTable() {
		filesTable = new JTable();
		filesTable.setBackground(new Color(210, 210, 210));
		filesTable.setBackground(new Color(210, 210, 210));
		filesTable.setAutoCreateRowSorter(true);
	}

	private void setUpSearchHeader() {
		searchPanel = new JPanel();
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
		JPanel searchWestPanel = new JPanel();
		searchWestPanel.add(searchArea);
		JScrollPane searchScroll = new JScrollPane(searchWestPanel);
		searchScroll.setSize(850, 20);
		searchPanel.add(searchScroll, BorderLayout.CENTER);
		JPanel searchEastPanel = new JPanel(new FlowLayout());
		searchEastPanel.add(clearButton);
		searchEastPanel.add(searchButton);
		searchPanel.add(searchEastPanel, BorderLayout.EAST);
	}

	private void setUpResultsHeaderPanel() {
		resultsHeaderPanel = new JPanel(new BorderLayout());
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showSearchView();
			}
		});
		resultsHeaderPanel.add(backButton, BorderLayout.WEST);
	}

	private void setUpFilesHeaderPanel() {
		filesHeaderPanel = new JPanel(new BorderLayout());
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showResultsView();
			}
		});
		workspaceButton = new JButton("Add selected files to workspace");
		filesHeaderPanel.add(workspaceButton, BorderLayout.EAST);
		filesHeaderPanel.add(backButton, BorderLayout.WEST);
	}

	private void setUpRowsPanel() {
		rowsPanel = new JPanel(new GridLayout(0, 1));
		clearSearchFields();
	}

	private DefaultTableModel getExperimentModel() {
		String[] columnNames = new String[2 + currentSearchResult[0].annotations.length];
		Object[][] data = new String[currentSearchResult.length][2 + currentSearchResult[0].annotations.length];
		columnNames[0] = "Experiment Name";
		columnNames[1] = "Experiment Creator";
		for (int i = 0; i < currentSearchResult.length; i++) {
			String experimentName = currentSearchResult[i].name;
			String experimentCreator = currentSearchResult[i].createdBy;
			data[i][0] = experimentName;
			data[i][1] = experimentCreator;
			AnnotationData[] annotations = currentSearchResult[i].annotations;
			for (int j = 0; j < annotations.length; j++) {
				columnNames[2 + j] = annotations[j].name;
				data[i][2 + j] = annotations[j].value;
			}
		}
		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		return model;
	}

	private DefaultTableModel getFilesModel(FileData[] files) {
		String[] columnNames = new String[4];
		Object[][] data = new String[files.length][4];
		columnNames[0] = "File Name";
		columnNames[1] = "Size";
		columnNames[2] = "Uploader";
		columnNames[3] = "Upload Date";
		for (int i = 0; i < files.length; i++) {
			data[i][0] = files[i].name + "." + files[i].type;
			data[i][1] = files[i].size;
			data[i][2] = files[i].uploadedBy;
			data[i][3] = files[i].date;
		}
		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		return model;
	}

	public void updateSearchResults(ExperimentData[] searchResults) {
		if (searchResults != null) {
			currentSearchResult = searchResults;
			if (searchResults.length > 0) {
				resultsTable.setModel(this.getExperimentModel());
			}
			showResultsView();
		}
	}

	private void clearSearchFields() {
		rowList.clear();
		addRow();
		searchArea.setText("Use the builder below to create your search");
		repaint();
		revalidate();
	}

	public void addRow() {
		rowList.add(new QueryBuilderRow(this));
		paintRows();
	}

	public void removeRow(QueryBuilderRow row) {
		if (rowList.contains(row)) {
			rowList.remove(row);
		}
		paintRows();
	}

	private void paintRows() {
		rowsPanel.removeAll();
		for (int i = 0; i < rowList.size(); i++) {
			QueryBuilderRow row = rowList.get(i);
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
		for (QueryBuilderRow row : rowList) {
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

	public void addWorkspaceButtonListener(ActionListener listener) {
		workspaceButton.addActionListener(listener);
	}

	public String getSearchString() {
		return searchArea.getText();
	}

}
