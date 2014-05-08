package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import util.AnnotationDataType;
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
    private JButton updateAnnotationsButton;
    private JButton addToWorkspaceButton;
    private JButton searchButton;
    private JButton downloadButton;
    private JTextArea searchArea;
    private ArrayList<QueryBuilderRow> rowList;
    private TreeTable resultsTable;
    private AnnotationDataType[] annotationTypes;

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
	bottomPanel.add(resultsTable, BorderLayout.CENTER);
	repaint();
	revalidate();
    }

    private void setUpQuerySearchTab() {
	updateAnnotationsButton = new JButton();
	annotationTypes = new AnnotationDataType[0];
	rowList = new ArrayList<QueryBuilderRow>();
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
	searchPanel.add(searchScroll);
	JPanel searchEastPanel = new JPanel(new FlowLayout());
	searchEastPanel.add(clearButton);
	searchEastPanel.add(searchButton);
	searchPanel.add(searchEastPanel);
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
	addToWorkspaceButton = new JButton("Add selected files to workspace");
	downloadButton = new JButton("Download Selected Files");
	resultsHeaderPanel.add(downloadButton, BorderLayout.EAST);
	resultsHeaderPanel.add(addToWorkspaceButton, BorderLayout.CENTER);
	resultsHeaderPanel.add(backButton, BorderLayout.WEST);
    }

    private void setUpRowsPanel() {
	rowsPanel = new JPanel(new GridLayout(0, 1));
	clearSearchFields();
    }

    public void updateSearchResults(ExperimentData[] searchResults) {
	updateAnnotationsButton.doClick();
	updateRows();
	paintRows();
	resultsTable.setContent(searchResults);
	showResultsView();
    }

    private void clearSearchFields() {
	updateAnnotationsButton.doClick();
	rowList.clear();
	addRow();
	searchArea.setText("Use the builder below to create your search");
	repaint();
	revalidate();
    }

    public void addRow() {
	rowList.add(new QueryBuilderRow(this, annotationTypes));
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

    private void updateRows() {
	for (int i = 0; i < rowList.size(); i++) {
	    rowList.get(i).setFieldBox(annotationTypes);
	}
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
	if (searchString.isEmpty()) {
	    searchArea.setText("Use the builder below to create your search");
	} else {
	    searchArea.setText(searchString);
	}
    }

    public void addSearchButtonListener(ActionListener listener) {
	searchButton.addActionListener(listener);
    }

    public void addAddToWorkspaceButtonListener(ActionListener listener) {
	addToWorkspaceButton.addActionListener(listener);
    }

    public void addDownloadButtonListener(ActionListener listener) {
	downloadButton.addActionListener(listener);
    }

    public void addUpdateAnnotationsListener(ActionListener listener) {
	updateAnnotationsButton.addActionListener(listener);
    }

    public void clickUpdateAnnotations() {
	updateAnnotationsButton.doClick();
    }

    public void setAnnotationTypes(AnnotationDataType[] annotationTypes) {
	this.annotationTypes = annotationTypes;
	updateRows();
	paintRows();
    }

    public FileData[] getSelectedFiles() {
	ArrayList<FileData> list = resultsTable.getSelectedFiles();
	for (FileData file : list) {
	    System.out.println(file.filename);
	}
	return list.toArray(new FileData[list.size()]);
    }

    public ExperimentData[] getSelectedFilesWithExperiments() {
	ArrayList<ExperimentData> list = resultsTable
		.getSelectedFilesWithExperiments();
	return list.toArray(new ExperimentData[list.size()]);
    }

    public ExperimentData[] getSelectedExperiments() {
	ArrayList<ExperimentData> list = resultsTable.getSelectedExperiments();
	return list.toArray(new ExperimentData[list.size()]);
    }

    public String getSearchString() {
	return searchArea.getText();
    }

}