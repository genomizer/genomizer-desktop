package gui.sysadmin.annotationview;

import gui.sysadmin.strings.SysStrings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.DefaultListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.BadLocationException;

/**
 * This class builds the view shown when the "Annotations" tab is chosen.
 *
 * */
public class AnnotationsViewCreator {

    private TableRowSorter<TableModel> rowSorter;
    private JButton addButton;
    private JButton modifyButton;
    private JButton removeButton;
    private JTable table;
    private AnnotationTableModel tablemodel;
    private String searchBoxHint = "Search annotation by name...";

    public AnnotationsViewCreator() {

    }

    /**
     * Builds the annotations view itself using methods that build everything
     * else.
     *
     * @return the view as a JPanel
     */
    public JPanel buildAnnotationsView() {

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.RED);
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

        return mainPanel;
    }

    /***
     * Builds the bottomPanel
     *
     * @param bottomPanel
     *            the bottom panel itself.
     * @param tabPanel
     *            the tab panel holding the search table.
     * @param buttonPanel
     *            the panel for the main buttons for annotations.
     */
    private void buildBottomPanel(JPanel bottomPanel, JPanel tabPanel,
            JPanel buttonPanel) {
        bottomPanel.setBackground(new Color(255, 200, 200));
        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        bottomPanel.add(tabPanel, BorderLayout.CENTER);
    }

    /***
     * Builds the tabPanel
     *
     * @param tabPanel
     *            the panel that holds the search table.
     */
    private void buildTabPanel(JPanel tabPanel) {
        tabPanel.setBackground(new Color(255, 250, 250));
        tabPanel.setLayout(new GridLayout(1, 0));
        tabPanel.add(buildSearchTable());

    }

    /***
     * Builds the buttonPanel
     *
     * @param buttonPanel
     *            the panel to hold all main buttons for the annotations view.
     *
     */
    private void buildButtonPanel(JPanel buttonPanel) {

        JPanel containerPanel = new JPanel();

        GroupLayout layout = new GroupLayout(containerPanel);
        containerPanel.setLayout(layout);

        buttonPanel.setLayout(new BorderLayout());
        modifyButton = new JButton(SysStrings.ANNOTATIONS_MODIFY);
        addButton = new JButton(SysStrings.ANNOTATIONS_ADD);
        removeButton = new JButton(SysStrings.ANNOTATIONS_DELETE);

        modifyButton.setMinimumSize(new Dimension(80, 10));
        addButton.setMinimumSize(new Dimension(80, 10));
        removeButton.setMinimumSize(new Dimension(80, 10));

        /** Create a group so the buttons hold if the window is resized. */

        layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(modifyButton).addComponent(addButton)
                        .addComponent(removeButton)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(modifyButton).addComponent(addButton)
                .addComponent(removeButton));

        buttonPanel.add(containerPanel);
    }

    /***
     * Builds the searchPanel
     *
     * @param searchPanel
     */
    private void buildSearchPanel(JPanel searchPanel) {
        JTextField searchField = buildSearchField();
        searchPanel.setLayout(new BorderLayout());
        JPanel paneception = new JPanel(new GridLayout(1, 1));

        // searchPanel.setPreferredSize(new Dimension(600, 40));
        searchPanel.setBackground(new Color(245, 245, 245));
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(paneception, BorderLayout.EAST);
    }

    /***
     * Builds the searchField.
     *
     * @return the searchField
     *
     */
    private JTextField buildSearchField() {
        Dimension searchDim = new Dimension(500, 30);
        final JTextField searchField = new SearchTextField(searchBoxHint);
        searchField.setForeground(new Color(155,155,155));

        searchField.getDocument().addDocumentListener(
                new SearchDocumentListener(rowSorter, searchField));
        searchField.setPreferredSize(searchDim);
        return searchField;
    }

    /***
     * Builds the search table in a scroll pane.
     *
     * @return the panel holding the search table.
     */
    private Component buildSearchTable() {

        tablemodel = new AnnotationTableModel();
        table = new JTable(tablemodel);
        table.setShowGrid(false);
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(
                tablemodel);
        this.rowSorter = rowSorter;

        JScrollPane scroll = new JScrollPane(table);
        table.getTableHeader();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scroll, BorderLayout.CENTER);
        table.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        table.setRowSorter(rowSorter);
        return panel;
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

    /***
     * Gets the search table.
     *
     * @return the search table.
     */
    public JTable getTable() {
        return table;
    }

    /***
     * Sets the same listener to all the buttons.
     *
     * @param addAnnotationListener
     *            the listener.
     */
    public void addAnnotationListener(ActionListener addAnnotationListener) {
        addButton.addActionListener(addAnnotationListener);
        modifyButton.addActionListener(addAnnotationListener);
        removeButton.addActionListener(addAnnotationListener);
    }

    /***
     * Gets the table model for the search table
     *
     * @return the model for the search table
     */
    public TableModel getTableModel() {
        return tablemodel;
    }

    /***
     * A private class that filters the search table based on what the user
     * writes in the search field.
     */
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
            try {
                if (!(e.getDocument().getText(0, e.getLength())
                        .equals(searchBoxHint))) {
                    newFilter(rowSorter, filterText);
                }
            } catch (BadLocationException e1) {
                // TODO B�ttre s�tt att hantera detta? CF
                // Do nothing, exception should not happen and even if it does
                // nothing dangerous will occur
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            newFilter(rowSorter, filterText);

        }

    }

}
