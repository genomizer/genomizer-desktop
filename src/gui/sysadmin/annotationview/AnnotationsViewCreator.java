package gui.sysadmin.annotationview;

import gui.sysadmin.strings.SysStrings;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AnnotationsViewCreator {
    
    private static final long          serialVersionUID = 3718367832670081148L;
    private TableRowSorter<TableModel> rowSorter;
    private SysadminAnnotationPopup    pop;
    private EditAnnotationPopup        editPopup;
    private JButton                    addButton;
    private JButton                    modifyButton;
    private JButton                    removeButton;
    private JTable                     table;
    private AnnotationTableModel       tablemodel;
    
    public AnnotationsViewCreator() {
        
    }
    
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
        
        // add(mainPanel, BorderLayout.CENTER);
        
        return mainPanel;
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
        tabPanel.add(buildSearchTable());
        
    }
    
    private void buildButtonPanel(JPanel buttonPanel) {
        buttonPanel.setBackground(new Color(215, 200, 200));
        buttonPanel.setLayout(new GridLayout(20, 1));
        modifyButton = new JButton(SysStrings.ANNOTATIONS_MODIFY); // TODO:
        // load
        // from
        // a
        // list
        addButton = new JButton(SysStrings.ANNOTATIONS_ADD);
        removeButton = new JButton(SysStrings.ANNOTATIONS_DELETE);
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
        final JTextField searchField = new JTextField("Search...");
        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (searchField.getText().equalsIgnoreCase("search...")) {
                    searchField.setText("");
                }
            }
        });
        
        searchField.getDocument().addDocumentListener(
                new SearchDocumentListener(rowSorter, searchField));
        searchField.setPreferredSize(searchDim);
        return searchField;
    }
    
    private Component buildSearchTable() {
        
        tablemodel = new AnnotationTableModel();
        table = new JTable(tablemodel);
        table.setShowGrid(false);
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(
                tablemodel);
        this.rowSorter = rowSorter;
        
        JScrollPane scroll = new JScrollPane(table);
        // scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        // table.setPreferredSize(scroll.getSize());
        JTableHeader header = table.getTableHeader();
        
        JPanel panel = new JPanel(new BorderLayout());
        // panel.add(header, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        // panel.add(table);
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
    
    public void popup(ActionListener popupListener) {
        pop = new SysadminAnnotationPopup();
        pop.setBackground(Color.WHITE);
        
        pop.addAddAnnotationListener(popupListener);
        
        JFrame popupFrame = new JFrame("Add new Annotation");
        popupFrame.setLayout(new BorderLayout());
        popupFrame.add(pop, BorderLayout.CENTER);
        popupFrame.pack();
        popupFrame.setLocationRelativeTo(null);
        popupFrame.setSize(new Dimension(600, 600));
        popupFrame.setVisible(true);
    }
    
    public void editPopup(ActionListener editPopupListener) {
        System.out.println("Skapar editAnnotationPopup...");
        editPopup = new EditAnnotationPopup(table);
        editPopup.setBackground(Color.WHITE);
        
        JFrame popupFrame = new JFrame("Edit annotation");
        popupFrame.setLayout(new BorderLayout());
        popupFrame.add(editPopup, BorderLayout.CENTER);
        popupFrame.pack();
        popupFrame.setLocationRelativeTo(null);
        popupFrame.setSize(new Dimension(600, 600));
        popupFrame.setVisible(true);
        
    }
    
    public JTable getTable() {
        return table;
    }
    
    public void addAnnotationListener(ActionListener addAnnotationListener) {
        addButton.addActionListener(addAnnotationListener);
        modifyButton.addActionListener(addAnnotationListener);
        removeButton.addActionListener(addAnnotationListener);
    }
    
    public void closePopup() {
        
    }
    
    public SysadminAnnotationPopup getPop() {
        return pop;
    }
    
    public EditAnnotationPopup getEditPopup() {
        return editPopup;
    }
    
    public TableModel getTableModel() {
        return tablemodel;
    }
    
    private class SearchDocumentListener implements DocumentListener {
        
        private TableRowSorter<TableModel> rowSorter;
        private JTextField                 filterText;
        
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
    
}
