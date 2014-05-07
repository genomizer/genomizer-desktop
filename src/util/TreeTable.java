package util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

public class TreeTable {

    private String[] headings;
    private String[][] data;
    private Node root;
    private DefaultTreeTableModel model;
    private JXTreeTable table;
    private ExperimentData[] experiments;
    private int sortByColumn;
    private ArrayList<Boolean> descs;
    private static final Pattern PATTERN = Pattern.compile("(\\D*)(\\d*)");


    public TreeTable() {
        this.headings = new String[0];
        experiments = new ExperimentData[0];
        descs = new ArrayList<Boolean>();
    }

    public void setContent(String[] headings, ExperimentData[] content) {
        table = null;
        this.headings = headings;
        this.experiments = content;
        data = new String[experiments.length][headings.length];
        for (int i = 0; i < experiments.length; i++) {
            data[i] = experiments[i].getAnnotationValueList();
        }

        descs = new ArrayList<Boolean>();
        for(int i=0; i<this.headings.length; i++) {
            descs.add(i, true);
        }
    }

    private String[][] sortMatrix(String[][] matrix, final int sortByColumn) {

        for(int i=0; i<descs.size(); i++) {
            if(i==sortByColumn) {
                descs.set(i, !descs.get(i));

            } else {
                descs.set(i, true);
            }
        }

        Arrays.sort(matrix, new Comparator<String[]>() {
            @Override
            public int compare(final String[] entry1, final String[] entry2) {
                Matcher m1 = PATTERN.matcher(entry1[sortByColumn]);
                Matcher m2 = PATTERN.matcher(entry2[sortByColumn]);

                // The only way find() could fail is at the end of a string
                while (m1.find() && m2.find()) {
                    // matcher.group(1) fetches any non-digits captured by the
                    // first parentheses in PATTERN.
                    int nonDigitCompare;
                    if (descs.get(sortByColumn)) {
                        nonDigitCompare = m2.group(1).compareTo(m1.group(1));
                    } else {
                        nonDigitCompare = m1.group(1).compareTo(m2.group(1));
                    }
                    if (0 != nonDigitCompare) {
                        return nonDigitCompare;
                    }

                    // matcher.group(2) fetches any digits captured by the
                    // second parentheses in PATTERN.
                    if (m1.group(2).isEmpty()) {
                        return m2.group(2).isEmpty() ? 0 : -1;
                    } else if (m2.group(2).isEmpty()) {
                        return +1;
                    }

                    BigInteger n1 = new BigInteger(m1.group(2));
                    BigInteger n2 = new BigInteger(m2.group(2));
                    int numberCompare;
                    if (descs.get(sortByColumn)) {
                        numberCompare = n2.compareTo(n1);
                    } else {
                        numberCompare = n1.compareTo(n2);
                    }
                    if (0 != numberCompare) {
                        return numberCompare;
                    }
                }

                // Handle if one string is a prefix of the other.
                // Nothing comes before something.
                return m1.hitEnd() && m2.hitEnd() ? 0 :
                        m1.hitEnd()                ? -1 : +1;
            }
        });
        return matrix;
    }

    public void setSorting(int sortByColumn) {
        this.sortByColumn = sortByColumn;
    }

    private String[][] getContentFromTreeTable() {
        table.collapseAll();
        String[][] experimentContent = new String[table.getRowCount()][table
                .getColumnCount()];
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                experimentContent[i][j] = (String) table.getValueAt(i, j);
            }
        }
        return experimentContent;
    }

    private String[] getHeadingsFromTreeTable() {
        String[] headings = new String[table.getColumnCount()];
        for (int i = 0; i < table.getColumnCount(); i++) {
            headings[i] = table.getColumnName(i);
        }
        return headings;
    }

    private ExperimentData getExperimentFromData(String[] data) {
        int mainIndex = 0;
        for (int i = 0; i < headings.length; i++) {
            if (headings[i].equals("Experiment Name")) {
                mainIndex = i;
            }
        }
        for (int i = 0; i < experiments.length; i++) {
            if (experiments[i].name.equals(data[mainIndex])) {
                return experiments[i];
            }
        }
        return null;
    }

    public ArrayList<FileData> getSelectedFiles() {
        ArrayList<FileData> files = new ArrayList<FileData>();
        int[] rows = table.getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < experiments.length; j++) {
                for (int k = 0; k < experiments[j].files.length; k++) {
                    String fileRow = experiments[j].files[k].name + " (" + experiments[j].files[k].size + ")";
                    if (fileRow.equals(table.getValueAt(
                            rows[i], 0))) {
                        files.add(experiments[j].files[k]);
                    }
                }
            }
        }
        return files;
    }

    public JXTreeTable getTreeTable() {
        root = new Node(new Object[] { "Root" });
        // If the table has been initated
        if (table != null) {
            data = getContentFromTreeTable();
            headings = getHeadingsFromTreeTable();
        }
        // sort the data
        data = sortMatrix(data, sortByColumn);

        for (int i = 0; i < experiments.length; i++) {
            Node child = new Node(data[i]);
            Node rawFiles = new Node(new String[] { "Raw Files" });
            Node profileFiles = new Node(new String[] { "Profile Files" });
            Node regionFiles = new Node(new String[] { "Region Files" });
            root.add(child);
            ExperimentData currentExperiment = getExperimentFromData(data[i]);
            FileData[] fileData = currentExperiment.files;
            for (int j = 0; j < fileData.length; j++) {
                FileData currentFile = fileData[j];
                Object[] rowContent = { currentFile.name + " (" + currentFile.size + ")" };
                if (currentFile.type.equals("raw")) {
                    rawFiles.add(new Node(rowContent));
                } else if (currentFile.type.equals("region")) {
                    regionFiles.add(new Node(rowContent));
                } else if (currentFile.type.equals("profile")) {
                    profileFiles.add(new Node(rowContent));
                }
            }
            if (rawFiles.getChildCount() != 0) {
                child.add(rawFiles);
            }
            if (regionFiles.getChildCount() != 0) {
                child.add(regionFiles);
            }
            if (profileFiles.getChildCount() != 0) {
                child.add(profileFiles);
            }
        }
        model = new DefaultTreeTableModel(root, Arrays.asList(headings));
        table = new JXTreeTable(model);
        table.setShowGrid(true, true);
        table.packAll();
        return table;
    }
}