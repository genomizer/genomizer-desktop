package util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.action.AbstractActionExt;
import org.jdesktop.swingx.table.ColumnControlButton;
import org.jdesktop.swingx.table.ColumnControlPopup;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

/**
 * Class the represents a treetable containing experiment and file data
 */
public class TreeTable extends JPanel {
    
    private JXTreeTable table;
    private ArrayList<String> headings;
    private ArrayList<ExperimentData> experiments;
    private ArrayList<Boolean> sortingOrders;
    private ArrayList<String> hiddenHeadings;
    private ArrayList<String> visibleHeadings;
    private ArrayList<JCheckBox> columnCheckBoxes;
    
    /**
     * Tree Table empty constructor
     */
    public TreeTable() {
        this.setLayout(new BorderLayout());
        initiateJXTreeTable();
    }
    
    /**
     * Tree Table constructor with input content
     * 
     * @param experimentData
     *            - the tree table content
     */
    public TreeTable(ArrayList<ExperimentData> experimentData) {
        this.setLayout(new BorderLayout());
        initiateJXTreeTable();
        setContent(experimentData);
    }
    
    /**
     * Method for initating the JXTreeTable
     */
    private void initiateJXTreeTable() {
        table = new JXTreeTable() {
            public boolean getScrollableTracksViewportWidth()
            {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
        table.setOpaque(true);
        
        table.setLeafIcon(null);
        table.setClosedIcon(null);
        table.setOpenIcon(null);
        /* Custom column control for hiding columns */
        ColumnControlButton controlButton = new ColumnControlButton(table) {
            @Override
            protected ColumnControlPopup createColumnControlPopup() {
                return (new NFColumnControlPopup());
            }
            
            class NFColumnControlPopup extends DefaultColumnControlPopup {
                @Override
                public void addVisibilityActionItems(
                        List<? extends AbstractActionExt> actions) {
                    if (!actions.isEmpty()) {
                        /* Hide all columns button */
                        JButton deselectButton = new JButton("Deselect All");
                        deselectButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                for (JCheckBox checkBox : columnCheckBoxes) {
                                    if (checkBox.isEnabled()
                                            && checkBox.isSelected()) {
                                        checkBox.setSelected(false);
                                    }
                                }
                            }
                        });
                        JButton selectButton = new JButton("Select All");
                        selectButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                for (JCheckBox checkBox : columnCheckBoxes) {
                                    if (checkBox.isEnabled()
                                            && !checkBox.isSelected()) {
                                        checkBox.setSelected(true);
                                    }
                                }
                            }
                        });
                        /* Add hide column checkboxes */
                        getPopupMenu().add(deselectButton);
                        getPopupMenu().add(selectButton);
                        for (JCheckBox checkBox : columnCheckBoxes) {
                            getPopupMenu().add(checkBox);
                        }
                    }
                }
                
                public void addAdditionalActionItems(
                        List<? extends Action> actions) {
                    /*
                     * Dummy method to prevent treetable from adding unwanted
                     * alternatives from controlPopup
                     */
                }
            }
            
        };
        table.setColumnControl(controlButton);
        table.setColumnControlVisible(true);
        table.setShowGrid(true, true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        /* Add a mouse listener to check for column sorting */
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1 && experiments != null) {
                    TableColumnModel cModel = table.getColumnModel();
                    int column = cModel.getColumnIndexAtX(e.getX());
                    sortData(column);
                    createTreeStructure();
                }
            }
        });
        /* Add a scroll pane */
        JScrollPane scrollPane = new JScrollPane(table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * set new content for the tree table
     * 
     * @param experimentData
     *            - new content
     */
    public void setContent(ArrayList<ExperimentData> experimentData) {
        
        /* Initiate the column sorting orders */
        sortingOrders = new ArrayList<Boolean>();
        /* If the input experiment data is not null or empty */
        if (experimentData != null && experimentData.size() > 0) {
            experiments = experimentData;
            /* Retreive the headings from the experiment data */
            int nrOfColumns = 2;
            headings = new ArrayList<String>();
            headings.add("ExpID");
            headings.add("Experiment Created By");
            for (int i = 0; i < experiments.size(); i++) {
                for (AnnotationDataValue annotation : experiments.get(i).annotations) {
                    if (!headings.contains(annotation.name)) {
                        headings.add(annotation.name);
                        nrOfColumns++;
                    }
                }
            }
            /* Initate the sorting orders as descending */
            for (int i = 0; i < nrOfColumns; i++) {
                sortingOrders.add(i, true);
            }
            
        }
        columnCheckBoxes = new ArrayList<JCheckBox>();
        for (final String heading : headings) {
            JCheckBox checkBox = new JCheckBox(heading);
            checkBox.setSelected(true);
            if (heading.equals("ExpID")) {
                checkBox.setEnabled(false);
            }
            checkBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.DESELECTED) {
                        hiddenHeadings.add(heading);
                    } else {
                        hiddenHeadings.remove(heading);
                    }
                    createTreeStructure();
                }
            });
            columnCheckBoxes.add(checkBox);
        }
        
        /* Create the tree structure */
        hiddenHeadings = new ArrayList<String>();
        createTreeStructure();
    }
    
    /**
     * Sort the treetable data by column index
     * 
     * @param sortByColumn
     *            - column index
     */
    private void sortData(final int sortByColumn) {
        
        /* update the sorting orders for the columns */
        for (int i = 0; i < sortingOrders.size(); i++) {
            if (i == sortByColumn) {
                sortingOrders.set(i, !sortingOrders.get(i));
            } else {
                sortingOrders.set(i, true);
            }
        }
        
        Collections.sort(experiments, new Comparator<ExperimentData>() {
            public int compare(ExperimentData a, ExperimentData b) {
                final Pattern PATTERN = Pattern.compile("(\\D*)(\\d*)");
                ArrayList<String> entry1 = a
                        .getAnnotationValueList(visibleHeadings);
                ArrayList<String> entry2 = b
                        .getAnnotationValueList(visibleHeadings);
                if (sortByColumn > entry1.size() - 1
                        || sortByColumn > entry2.size() - 1) {
                    return 1;
                } else if (sortByColumn < 0) {
                    return 1;
                }
                if ((entry1.get(sortByColumn) == null || entry1.get(
                        sortByColumn).equals(""))
                        && (entry2.get(sortByColumn) == null || entry2.get(
                                sortByColumn).equals(""))) {
                    return 0;
                } else if ((entry1.get(sortByColumn) == null || entry1.get(
                        sortByColumn).equals(""))
                        && !(entry2.get(sortByColumn) == null || entry2.get(
                                sortByColumn).equals(""))) {
                    return -1;
                } else if (!(entry1.get(sortByColumn) == null || entry1.get(
                        sortByColumn).equals(""))
                        && (entry2.get(sortByColumn) == null || entry2.get(
                                sortByColumn).equals(""))) {
                    return 1;
                }
                Matcher m1 = PATTERN.matcher(entry1.get(sortByColumn)
                        .toLowerCase());
                Matcher m2 = PATTERN.matcher(entry2.get(sortByColumn)
                        .toLowerCase());
                /* The only way find() could fail is at the end of a string */
                while (m1.find() && m2.find()) {
                    /*
                     * matcher.group(1) fetches any non-digits captured by the
                     * first parentheses in PATTERN.
                     */
                    int nonDigitCompare;
                    if (sortingOrders.get(sortByColumn)) {
                        nonDigitCompare = m2.group(1).compareTo(m1.group(1));
                    } else {
                        nonDigitCompare = m1.group(1).compareTo(m2.group(1));
                    }
                    if (0 != nonDigitCompare) {
                        return nonDigitCompare;
                    }
                    
                    /*
                     * matcher.group(2) fetches any digits captured by the
                     * second parentheses in PATTERN.
                     */
                    if (m1.group(2).isEmpty()) {
                        return m2.group(2).isEmpty() ? 0 : -1;
                    } else if (m2.group(2).isEmpty()) {
                        return +1;
                    }
                    
                    BigInteger n1 = new BigInteger(m1.group(2));
                    BigInteger n2 = new BigInteger(m2.group(2));
                    int numberCompare;
                    if (sortingOrders.get(sortByColumn)) {
                        numberCompare = n2.compareTo(n1);
                    } else {
                        numberCompare = n1.compareTo(n2);
                    }
                    if (0 != numberCompare) {
                        return numberCompare;
                    }
                }
                
                /*
                 * Handle if one string is a prefix of the other. Nothing comes
                 * before something.
                 */
                return m1.hitEnd() && m2.hitEnd() ? 0 : m1.hitEnd() ? -1 : +1;
            }
            
        });
        
    }
    
    /**
     * Return the selected data in the tree table
     * 
     * @return
     */
    public ArrayList<ExperimentData> getSelectedData() {
        /* Get the data that are selected in the table */
        int[] rows = table.getSelectedRows();
        ArrayList<ExperimentData> selectedExperiments = new ArrayList<ExperimentData>();
        /* For each selected row */
        for (int i = 0; i < rows.length; i++) {
            /* Get the node of the selected row */
            TreePath path = table.getPathForRow(rows[i]);
            Object nodeObject = path.getLastPathComponent();
            /* Check type of node */
            if (nodeObject instanceof ExperimentNode) {
                /* If experiment node */
                ExperimentNode expNode = (ExperimentNode) nodeObject;
                ExperimentData exp = expNode.getExperiment();
                ExperimentData newExp = new ExperimentData(exp.name,
                        exp.createdBy, (ArrayList<FileData>) exp.files.clone(),
                        (ArrayList<AnnotationDataValue>) exp.annotations
                                .clone());
                if (!selectedExperiments.contains(exp)) {
                    selectedExperiments.add(newExp);
                }
            } else if (nodeObject instanceof FileNode) {
                /* If file node */
                FileNode fileNode = (FileNode) nodeObject;
                ArrayList<FileData> newFile = new ArrayList<FileData>();
                newFile.add(fileNode.getFile());
                Object parentNode = fileNode.getParent().getParent();
                if (parentNode instanceof ExperimentNode) {
                    ExperimentNode expNode = (ExperimentNode) parentNode;
                    ExperimentData tempExp = expNode.getExperiment();
                    if (selectedExperiments.contains(tempExp)) {
                        int index = selectedExperiments.indexOf(tempExp);
                        if (!selectedExperiments.get(index).files
                                .contains(fileNode.getFile())) {
                            selectedExperiments.get(index).addFiles(newFile);
                        }
                    } else {
                        ExperimentData exp = new ExperimentData(tempExp.name,
                                tempExp.createdBy, newFile, tempExp.annotations);
                        selectedExperiments.add(exp);
                    }
                    
                }
            } else {
                /*
                 * If support node (file headings node or raw/region/profile
                 * node
                 */
                SupportNode node = (SupportNode) nodeObject;
                ArrayList<FileData> newFiles = new ArrayList<FileData>();
                for (int j = 0; j < node.getChildCount(); j++) {
                    if (node.getChildAt(j) instanceof FileNode) {
                        FileNode fileNode = (FileNode) node.getChildAt(j);
                        newFiles.add(fileNode.getFile());
                    }
                }
                ;
                Object parentNode = node.getParent();
                if (parentNode instanceof ExperimentNode) {
                    ExperimentNode expNode = (ExperimentNode) parentNode;
                    ExperimentData tempExp = expNode.getExperiment();
                    if (selectedExperiments.contains(tempExp)) {
                        int index = selectedExperiments.indexOf(tempExp);
                        for (FileData file : newFiles) {
                            if (!selectedExperiments.get(index).files
                                    .contains(file)) {
                                ArrayList<FileData> newFile = new ArrayList<FileData>();
                                newFile.add(file);
                                selectedExperiments.get(index)
                                        .addFiles(newFile);
                            }
                        }
                    } else {
                        ExperimentData exp = new ExperimentData(
                                tempExp.name,
                                tempExp.createdBy,
                                newFiles,
                                (ArrayList<AnnotationDataValue>) tempExp.annotations
                                        .clone());
                        selectedExperiments.add(exp);
                    }
                }
            }
        }
        return selectedExperiments;
    }
    
    /**
     * remove the currently selected files
     */
    public void removeSelectedData() {
        ArrayList<ExperimentData> selectedData = getSelectedData();
        ArrayList<FileData> selectedFiles = new ArrayList<FileData>();
        for (ExperimentData experiment : selectedData) {
            for (FileData file : experiment.files) {
                if (!selectedFiles.contains(file)) {
                    selectedFiles.add(file);
                }
            }
        }
        try {
            for (ExperimentData data : experiments) {
                for (FileData file : selectedFiles) {
                    if(data.files.contains(file)) {
                        System.out.println("removed file " + file.filename);
                        data.removeFile(file);
                    }
                }
            }
            for (ExperimentData data : (ArrayList<ExperimentData>) experiments.clone()) {
                if (data.files.size() == 0 && selectedData.contains(data)) {
                    System.out.println("removed " + data.name);
                    experiments.remove(data);
                }
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "No files to remove.");
        }
        createTreeStructure();
    }
    
    /**
     * Get the tree table content
     * 
     * @return
     */
    public ArrayList<ExperimentData> getContent() {
        return experiments;
    }
    
    /**
     * Update the visible headings in the table (with column order intact)
     */
    private void updateVisibleHeadings() {
        try {
            visibleHeadings = new ArrayList<String>();
            int columnCount = table.getColumnCount();
            if (columnCount > 0) {
                visibleHeadings = new ArrayList<String>();
                for (int i = 0; i < columnCount; i++) {
                    visibleHeadings.add(table.getColumnName(i));
                }
                for (String heading : headings) {
                    if (!visibleHeadings.contains(heading)
                            && !hiddenHeadings.contains(heading)) {
                        visibleHeadings.add(heading);
                    }
                }
                visibleHeadings.removeAll(hiddenHeadings);
                
            } else {
                visibleHeadings.addAll(headings);
            }
        } catch (NullPointerException e) {
        }
    }
    
    /**
     * Create the tree structure of the tree table
     */
    private void createTreeStructure() {
        /* Create the tree root */
        updateVisibleHeadings();
        SupportNode root = new SupportNode(new Object[] { "Root" });
        try {
            for (ExperimentData experiment : experiments) {
                /* Create experiment node and add to root */
                ExperimentNode experimentNode = new ExperimentNode(experiment,
                        visibleHeadings);
                root.add(experimentNode);
            }
            /* Create the model and add it to the table */
            DefaultTreeTableModel model = new DefaultTreeTableModel(root,
                    Arrays.asList(visibleHeadings
                            .toArray(new String[visibleHeadings.size()])));
            
            table.setTreeTableModel(model);
            table.packAll();
            repaint();
            revalidate();
            
        } catch (NullPointerException e) {
            
        }
    }
}
