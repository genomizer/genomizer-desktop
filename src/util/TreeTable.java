package util;

import java.awt.BorderLayout;
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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
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

    private static final long serialVersionUID = 6033511181052590303L;
    private JXTreeTable table;
    private ArrayList<String> headings;
    private ArrayList<ExperimentData> experiments;
    private HashMap<String, Boolean> sortingOrders;
    private CopyOnWriteArrayList<String> hiddenHeadings;
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
            private static final long serialVersionUID = -5027164951558722985L;

            public boolean getScrollableTracksViewportWidth() {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
        table.setOpaque(true);
        table.setLeafIcon(null);
        table.setClosedIcon(null);
        table.setOpenIcon(null);
        /* Custom column control for hiding columns */
        ColumnControlButton controlButton = new ColumnControlButton(table) {
            private static final long serialVersionUID = 1022478445883845370L;

            @Override
            protected ColumnControlPopup createColumnControlPopup() {
                return (new NFColumnControlPopup());
            }

            class NFColumnControlPopup extends DefaultColumnControlPopup {
                @Override
                public synchronized void addVisibilityActionItems(
                        List<? extends AbstractActionExt> actions) {
                    if (!actions.isEmpty()) {
                        JPopupMenu popupMenu = getPopupMenu();

                        /* Hide all columns button */
                        JButton deselectButton = new JButton(
                                "Hide all annotations");
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
                        JButton selectButton = new JButton(
                                "Show all annotations");
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

                        popupMenu.add(selectButton);
                        popupMenu.add(deselectButton);

                        // Add some space between the checkboxes and the
                        // buttons.
                        popupMenu.add(new JLabel("\n"));

                        /* Add hide column checkboxes */
                        for (JCheckBox checkBox : columnCheckBoxes) {
                            getPopupMenu().add(checkBox);
                        }

                        // Add some space between the checkboxes and the
                        // buttons.
                        popupMenu.add(new JLabel("\n"));

                        // Add expand all button with listener.
                        JButton expandAllButton = new JButton("Expand all");
                        expandAllButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                table.expandAll();
                            }
                        });
                        popupMenu.add(expandAllButton);

                        // Add collapse all button with listener.
                        JButton collapseAllButton = new JButton("Collapse all");
                        collapseAllButton
                                .addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(
                                            ActionEvent actionEvent) {
                                        table.collapseAll();
                                    }
                                });
                        popupMenu.add(collapseAllButton);

                        popupMenu.repaint();
                        popupMenu.revalidate();
                    }
                }

                public synchronized void addAdditionalActionItems(
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
    public synchronized void setContent(ArrayList<ExperimentData> experimentData) {

        /* Initiate the column sorting orders */
        sortingOrders = new HashMap<>();
        /* If the input experiment data is not null or empty */
        if (experimentData != null && experimentData.size() > 0) {
            experiments = experimentData;
            /* Retreive the headings from the experiment data */
            int nrOfColumns = 1;
            headings = new ArrayList<>();
            headings.add("ExpID");
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
                sortingOrders.put(headings.get(i), true);
            }

        }
        columnCheckBoxes = new ArrayList<>();
        for (final String heading : headings) {
            JCheckBox checkBox = new JCheckBox(heading);
            checkBox.setSelected(true);
            if (heading.equals("ExpID")) {
                checkBox.setEnabled(false);
            }
            checkBox.addItemListener(new ItemListener() {
                @Override
                public synchronized void itemStateChanged(ItemEvent e) {
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
        hiddenHeadings = new CopyOnWriteArrayList<>();
        createTreeStructure();
    }

    /**
     * Sort the treetable data by column index
     *
     * @param sortByColumn
     *            - column index
     */
    private synchronized void sortData(final int sortByColumn) {
        this.updateVisibleHeadings();
        /* update the sorting orders for the columns */
        final String heading = table.getColumnName(sortByColumn);
        for (String key : sortingOrders.keySet()) {
            if (key.equals(heading)) {
                sortingOrders.put(key, !sortingOrders.get(key));
            } else {
                sortingOrders.put(key, true);
            }
        }
        if (heading.equals("")) {
            return;
        }

        Collections.sort(experiments, new Comparator<ExperimentData>() {
            public synchronized int compare(ExperimentData a, ExperimentData b) {
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
                    if (sortingOrders.get(heading)) {
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
                    if (sortingOrders.get(heading)) {
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
    public synchronized ArrayList<ExperimentData> getSelectedData() {
        /* Get the data that are selected in the table */
        int[] rows = table.getSelectedRows();
        ArrayList<ExperimentData> selectedExperiments = new ArrayList<>();
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
                        (ArrayList<FileData>) exp.files.clone(),
                        (ArrayList<AnnotationDataValue>) exp.annotations
                                .clone());
                if (!selectedExperiments.contains(exp)) {
                    selectedExperiments.add(newExp);
                }
            } else if (nodeObject instanceof FileNode) {
                /* If file node */
                FileNode fileNode = (FileNode) nodeObject;
                ArrayList<FileData> newFile = new ArrayList<>();
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
                                newFile, tempExp.annotations);
                        selectedExperiments.add(exp);
                    }

                }
            } else {
                /*
                 * If support node (file headings node or raw/region/profile
                 * node
                 */
                SupportNode node = (SupportNode) nodeObject;
                ArrayList<FileData> newFiles = new ArrayList<>();
                for (int j = 0; j < node.getChildCount(); j++) {
                    if (node.getChildAt(j) instanceof FileNode) {
                        FileNode fileNode = (FileNode) node.getChildAt(j);
                        newFiles.add(fileNode.getFile());
                    }
                }
                Object parentNode = node.getParent();
                if (parentNode instanceof ExperimentNode) {
                    ExperimentNode expNode = (ExperimentNode) parentNode;
                    ExperimentData tempExp = expNode.getExperiment();
                    if (selectedExperiments.contains(tempExp)) {
                        int index = selectedExperiments.indexOf(tempExp);
                        for (FileData file : newFiles) {
                            if (!selectedExperiments.get(index).files
                                    .contains(file)) {
                                ArrayList<FileData> newFile = new ArrayList<>();
                                newFile.add(file);
                                selectedExperiments.get(index)
                                        .addFiles(newFile);
                            }
                        }
                    } else {
                        ExperimentData exp = new ExperimentData(
                                tempExp.name,
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

    public synchronized ArrayList<ExperimentData> getSelectedExperiments() {
        /* Get the data that are selected in the table */
        int[] rows = table.getSelectedRows();
        ArrayList<ExperimentData> selectedExperiments = new ArrayList<>();
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
                        (ArrayList<FileData>) exp.files.clone(),
                        (ArrayList<AnnotationDataValue>) exp.annotations
                                .clone());
                if (!selectedExperiments.contains(exp)) {
                    selectedExperiments.add(newExp);
                }
            }
        }
        return selectedExperiments;
    }

    /**
     * remove the currently selected files
     */
    public synchronized void removeSelectedData() {
        ArrayList<ExperimentData> selectedData = getSelectedData();
        ArrayList<FileData> selectedFiles = new ArrayList<>();
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
                    if (data.files.contains(file)) {
                        Thread.sleep(10);
                        data.removeFile(file);
                    }
                }
            }
            selectedData = getSelectedExperiments();
            for (ExperimentData data : selectedData) {
                experiments.remove(data);
                Thread.sleep(10);
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "No files to remove.");
        } catch (InterruptedException e) {
            e.printStackTrace();
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

    public void deselectTreeTable() {
        table.clearSelection();
    }

    /**
     * Update the visible headings in the table (with column order intact)
     */
    private synchronized void updateVisibleHeadings() {
        try {
            visibleHeadings = new ArrayList<>();
            int columnCount = table.getColumnCount();
            if (columnCount > 0) {
                visibleHeadings = new ArrayList<>();
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
            // TODO: Where does the Null come from ? (OO)
//            System.out.println("Couldn't update visible headings");
        }
    }

    /**
     * Create the tree structure of the tree table
     */
    private synchronized void createTreeStructure() {
        /* Create the tree root */
        updateVisibleHeadings();
        final SupportNode root = new SupportNode(new Object[] { "Root" });
        try {
            while (visibleHeadings.contains("")) {
                visibleHeadings.remove("");
            }
            while (visibleHeadings.size() < 3) {
                visibleHeadings.add("");
            }
            for (ExperimentData experiment : experiments) {
                /* Create experiment node and add to root */
                ExperimentNode experimentNode = new ExperimentNode(experiment,
                        visibleHeadings);
                root.add(experimentNode);
            }
            /* Create the model and add it to the table */
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    DefaultTreeTableModel model = new DefaultTreeTableModel(
                            root,
                            Arrays.asList(visibleHeadings
                                    .toArray(new String[visibleHeadings.size()])));
                    table.setTreeTableModel(model);
                    table.packAll();
                }
            });

        } catch (NullPointerException e) {
            // TODO: Where does the Null come from ? (OO)

        }
    }
}
