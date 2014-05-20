package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import util.TreeTable;

/**
 * Class representing the search tab of the gui
 */
public class QuerySearchTab extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = -5171748087481537247L;
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
    
    /**
     * Create a query search tab
     */
    public QuerySearchTab() {
        setUpQuerySearchTab();
        setUpSearchHeader();
        setUpRowsPanel();
        setUpResultsTable();
        setUpResultsHeaderPanel();
        showSearchView();
        clearSearchFields();
    }
    
    /**
     * Show the search view of the tab
     */
    private void showSearchView() {
        removeAll();
        JScrollPane bottomScroll = new JScrollPane(bottomPanel);
        bottomScroll.setBorder(BorderFactory.createEmptyBorder());
        add(topPanel, BorderLayout.NORTH);
        add(bottomScroll, BorderLayout.CENTER);
        topPanel.removeAll();
        bottomPanel.removeAll();
        topPanel.add(searchPanel);
        bottomPanel.add(rowsPanel, BorderLayout.NORTH);
        repaint();
        revalidate();
    }
    
    /**
     * Show the results view of the tab
     */
    private void showResultsView() {
        removeAll();
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
        topPanel.removeAll();
        bottomPanel.removeAll();
        topPanel.add(resultsHeaderPanel);
        bottomPanel.add(resultsTable, BorderLayout.CENTER);
        repaint();
        revalidate();
    }
    
    /**
     * Set up the query search tab foundation
     */
    private void setUpQuerySearchTab() {
        updateAnnotationsButton = new JButton();
        annotationTypes = new AnnotationDataType[0];
        rowList = new ArrayList<QueryBuilderRow>();
        this.setLayout(new BorderLayout());
        bottomPanel = new JPanel(new BorderLayout());
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory
                .createTitledBorder("Genomizer Advanced Search Builder"));
    }
    
    /**
     * Set up the results tree table
     */
    private void setUpResultsTable() {
        resultsTable = new TreeTable();
    }
    
    /**
     * Set up the search view header
     */
    private void setUpSearchHeader() {
        searchPanel = new JPanel(new FlowLayout());
        
        searchButton = new JButton("Search");
        // searchButton = CustomButtonFactory.makeCustomButton(
        // IconFactory.getSearchIcon(28, 28),
        // IconFactory.getSearchHoverIcon(30, 30), 30, 30,
        // "Search for data");
        
        // clearButton = CustomButtonFactory.makeCustomButton(
        // IconFactory.getClearIcon(35, 35),
        // IconFactory.getClearHoverIcon(37, 37), 37, 37,
        // "Clear search fields");
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
        JScrollPane searchScroll = new JScrollPane(searchArea);
        searchScroll.setPreferredSize(new Dimension(800, 35));
        searchPanel.add(searchScroll);
        searchPanel.add(searchButton);
        // searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(clearButton);
    }
    
    /**
     * Set up the results view header
     */
    private void setUpResultsHeaderPanel() {
        resultsHeaderPanel = new JPanel(new BorderLayout());
        // JButton backButton = CustomButtonFactory.makeCustomButton(
        // IconFactory.getBackIcon(25, 25),
        // IconFactory.getBackHoverIcon(27, 27), 25, 25,
        // "Back to search view");
        final JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSearchView();
            }
        });
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
        // addToWorkspaceButton = CustomButtonFactory.makeCustomButton(
        // IconFactory.getAddToWorkspaceIcon(50, 34),
        // IconFactory.getAddToWorkspaceHoverIcon(52, 36), 52, 36,
        // "Add selected to workspace");
        addToWorkspaceButton = new JButton("Add to workspace");
        downloadButton = new JButton("Download Selected Files");
        // resultsHeaderPanel.add(downloadButton, BorderLayout.EAST);
        resultsHeaderPanel.add(addToWorkspaceButton, BorderLayout.EAST);
        resultsHeaderPanel.add(backButton, BorderLayout.WEST);
        JPanel refreshPanel = new JPanel();
        resultsHeaderPanel.add(refreshPanel);
        refreshPanel.add(refreshButton);
    }

    private void refresh() {
        // backButton.doClick();
        searchButton.doClick();
    }

    /**
     * Set up the rows panel (containing query builder rows
     */
    private void setUpRowsPanel() {
        rowsPanel = new JPanel(new GridLayout(0, 1));
    }
    
    /**
     * Update the search results and switch to results view
     * 
     * @param searchResults
     */
    public void updateSearchResults(ArrayList<ExperimentData> searchResults) {
        // updateAnnotationsButton.doClick();
        // updateRows();
        paintRows();
        resultsTable = new TreeTable();
        resultsTable.setContent(searchResults);
        showResultsView();
    }
    
    /**
     * Clear the search fields of the tab (including all query builder rows and
     * the search text area
     */
    public void clearSearchFields() {
        updateAnnotationsButton.doClick();
        rowList.clear();
        addRow();
        searchArea.setText("Use the builder below to create your search");
        revalidate();
        repaint();
    }
    
    /**
     * Add a new row to the query builder
     */
    public void addRow() {
        rowList.add(new QueryBuilderRow(this, annotationTypes));
        paintRows();
    }
    
    /**
     * Remove a row from the query builder
     * 
     * @param row
     */
    public void removeRow(QueryBuilderRow row) {
        if (rowList.contains(row)) {
            rowList.remove(row);
        }
        paintRows();
    }
    
    /**
     * Paint the query builder rows in the rows panel
     */
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
        rowsPanel.revalidate();
        rowsPanel.repaint();
        updateSearchArea();
    }
    
    /**
     * Update all query builder rows with annotation information
     */
    private void updateRows() {
        for (int i = 0; i < rowList.size(); i++) {
            rowList.get(i).setAnnotationBox(annotationTypes);
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
    
    public ArrayList<ExperimentData> getSelectedData() {
        return resultsTable.getSelectedData();
        
    }
    
    public String getSearchString() {
        return searchArea.getText();
    }
    
}
