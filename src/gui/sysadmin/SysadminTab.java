package gui.sysadmin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.TabExpander;

import util.AnnotationDataTypes;

public class SysadminTab extends JPanel {

    private static final String[] buttonNames = new String[]{"Annotations",
            "Users", "TestMe"};
	private TableRowSorter<TableModel> rowSorter;
	private SysadminAnnotationPopup pop;
	private static final long serialVersionUID = 3718367832670081148L;
	private JButton addButton;
	private AnnotationTableModel tablemodel;

    /**
     * Create the panel.
     */
    public SysadminTab() {
        setLayout(new BorderLayout());
        buildMainPanel();
        buildSidePanel(buttonNames);

    }

    private void buildMainPanel() {

        JPanel mainPanel = new JPanel();
        JPanel searchPanel = new JPanel(new FlowLayout());
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel tabPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        buildTabPanel(tabPanel);
        buildSearchPanel(searchPanel);
        buildButtonPanel(buttonPanel);
        buildBottomPanel(bottomPanel, tabPanel, buttonPanel);

        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void buildBottomPanel(JPanel bottomPanel, JPanel tabPanel,
                                  JPanel buttonPanel) {
        bottomPanel.setBackground(new Color(255, 200, 200));
        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        bottomPanel.add(tabPanel, BorderLayout.CENTER);
    }

    private void buildTabPanel(JPanel tabPanel) {
        tabPanel.setBackground(new Color(255, 250, 250));
        tabPanel.setLayout(new GridLayout(1, 0));
        tabPanel.add(buildMockTable());

    }

    private void buildButtonPanel(JPanel buttonPanel) {
        buttonPanel.setBackground(new Color(215, 200, 200));
        buttonPanel.setLayout(new GridLayout(20, 1));
        JButton modifyButton = new JButton("Modify"); // TODO: load from a list
        addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        buttonPanel.add(modifyButton);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
    }

    private void buildSearchPanel(JPanel searchPanel) {
        JTextField searchField = buildSearchField();
        searchPanel.setLayout(new BorderLayout());
        JPanel paneception = new JPanel(new GridLayout(1, 1));

        JButton searchButton = new JButton("Search");

        // searchPanel.setPreferredSize(new Dimension(600, 40));
        searchPanel.setBackground(new Color(245, 245, 245));
        searchPanel.add(searchField, BorderLayout.CENTER);
        paneception.add(searchButton);
        searchPanel.add(paneception, BorderLayout.EAST);
    }

    private JTextField buildSearchField() {
        Dimension searchDim = new Dimension(500, 30);
        JTextField searchField = new JTextField("Search...");
        searchField.getDocument().addDocumentListener(
                new SearchDocumentListener(rowSorter, searchField));
        searchField.setPreferredSize(searchDim);
        return searchField;
    }

    private void buildSidePanel(String[] buttonNames) {

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, 1));

        for (int i = 0; i < buttonNames.length; i++) {

            sidePanel.add(new JButton(buttonNames[i]));

        }

        // sidePanel.add(new JButton("Annotations")); // TODO: load from a list
        // of
        // function
        add(sidePanel, BorderLayout.WEST);
    }

    private Component buildMockTable() {
        JPanel panel = new JPanel(new BorderLayout());

		tablemodel = new AnnotationTableModel();
		AnnotationTableModel tableModelAnnotationTableModel = (AnnotationTableModel) tablemodel;
		tableModelAnnotationTableModel.setAnnotations(new AnnotationDataTypes[] {});

		JTable table = new JTable(tablemodel);
		
		table.setPreferredSize(panel.getSize());

		table.setShowGrid(false);
		JTableHeader header = table.getTableHeader();		
		panel.add(header, BorderLayout.NORTH);
		panel.add(table, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(
                tablemodel);
        this.rowSorter = rowSorter;
        table.setRowSorter(rowSorter);

        return scroll;
    }

    private Component buildSearchTable() {

        JPanel panel = new JPanel(new BorderLayout());
        TableModel tableModel = null; // TODO: fix this add a new class?
        JTable table = new JTable(tableModel);
        table.setPreferredSize(panel.getSize());

        table.setShowGrid(false);

        JTableHeader header = table.getTableHeader();

        panel.add(header, BorderLayout.NORTH);
        panel.add(table, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(
                tableModel);

        this.rowSorter = rowSorter;

        table.setRowSorter(rowSorter);
        return null;
    }

    /**
     * Update the row filter regular expression from the expression in the text
     * box.
     */
    private void newFilter(TableRowSorter<TableModel> rowSorter,
                           JTextField filterText) {
        RowFilter<TableModel, Object> rf = null;
        // If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(filterText.getText(), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        rowSorter.setRowFilter(rf);
    }

    public void popup() {
        pop = new SysadminAnnotationPopup();
        pop.setBackground(Color.WHITE);
        JFrame popupFrame = new JFrame("Add new Annotation");
        popupFrame.setLayout(new BorderLayout());
        popupFrame.add(pop, BorderLayout.CENTER);
        popupFrame.pack();
        popupFrame.setLocationRelativeTo(null);
        popupFrame.setSize(new Dimension(600, 600));
        popupFrame.setVisible(true);
    }

    private class SearchDocumentListener implements DocumentListener {

        private TableRowSorter<TableModel> rowSorter;
        private JTextField filterText;

        public SearchDocumentListener(TableRowSorter<TableModel> rowSorter,
                                      JTextField filterText) {
            super();
            this.rowSorter = rowSorter;
            this.filterText = filterText;
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            newFilter(rowSorter, filterText);

        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            newFilter(rowSorter, filterText);

        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            newFilter(rowSorter, filterText);

        }

    }

    public void addAddAnnotationListener(ActionListener addAnnotationListener) {
        pop.addAddAnnotationListener(addAnnotationListener);
    }

    public void addAddPopupListener(ActionListener addPopupListener) {
        addButton.addActionListener(addPopupListener);
    }

    public String getNewAnnotationName() {
        return pop.getNewAnnotationName();
    }

    public String[] getNewAnnotationCategories() {
        return pop.getNewAnnotationCategories();
    }

    public boolean getNewAnnotationForcedValue() {
        return pop.getNewAnnotationForcedValue();
    }

    public void closePopup() {
        pop.closeWindow();

    }

	public AnnotationTableModel getTableModel() {
		return tablemodel;
	}

	public void setAnnotationTableData(AnnotationDataTypes[] annotations) {
		tablemodel.setAnnotations(annotations);
	}
}
