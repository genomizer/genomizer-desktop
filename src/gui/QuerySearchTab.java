package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import util.ExperimentData;
import util.FileData;
import util.TreeTable;

public class QuerySearchTab extends JPanel {
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JPanel rowsPanel;
	private JPanel searchPanel;
	private JPanel resultsHeaderPanel;
	private JButton clearButton;
	private JButton searchButton;
	private JButton workspaceButton;
	private JTextArea searchArea;
	private ArrayList<QueryBuilderRow> rowList;
	private TreeTable resultsTable;
	private ExperimentData[] currentSearchResult;
	
	public QuerySearchTab() {
		setUpQuerySearchTab();
		setUpSearchHeader();
		setUpRowsPanel();
		setUpResultsTable();
		setUpResultsHeaderPanel();
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
		bottomPanel.add(new JScrollPane(resultsTable.getTreeTable()), BorderLayout.CENTER);
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
		resultsTable = new TreeTable();
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
		resultsHeaderPanel.add(backButton,BorderLayout.WEST);
	}


	private void setUpRowsPanel() {
		rowsPanel = new JPanel(new GridLayout(0, 1));
		clearSearchFields();
	}

	public void updateSearchResults(ExperimentData[] searchResults) {
		String[] headings = null;
		List<String[]> content = new ArrayList<String[]>();
		if(searchResults != null) {
			currentSearchResult = searchResults;
			if(searchResults.length > 0) {
				//headings
				int nrOfColumns = 2+searchResults[0].annotations.length;
				headings = new String[nrOfColumns];
				headings[0] = "Experiment Name";
				headings[1] = "Experiment Created By";
				for(int i=0; i<searchResults[0].annotations.length; i++) {
					ExperimentData expData = searchResults[i];
					headings[2+i] = expData.annotations[i].name;
				}
				//content
				for(int i=0; i<searchResults.length; i++) {
					ExperimentData expData = searchResults[i];
					String[] rowContent = new String[nrOfColumns];
					rowContent[0] = expData.name;
					rowContent[1] = expData.createdBy;
					for(int j=0; j<expData.annotations.length; j++) {
						rowContent[2+j] =  expData.annotations[j].value;
					}
					content.add(rowContent);
					FileData[] files = expData.files;
					for(int j=0; j<files.length; j++) {
						String[] fileContent = new String[1];
						fileContent[0] = expData.files[j].name + "." + expData.files[j].type;
						content.add(fileContent);
					}
				}
			}
			resultsTable.setContent(headings, content);
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
