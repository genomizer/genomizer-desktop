package util;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

public class TreeTable extends JPanel {

	private String[] headings;
	private String[][] data;
	private JXTreeTable table;
	private ExperimentData[] experiments;
	private ArrayList<Boolean> sortingOrders;
	private int nrOfColumns;


	public TreeTable()  {
		this.setLayout(new BorderLayout());
		initiateJXTreeTable();
	}

	public TreeTable(ExperimentData[] experimentData) {
		this.setLayout(new BorderLayout());
		initiateJXTreeTable();
		setContent(experimentData);
	}

	private void initiateJXTreeTable() {
		table = new JXTreeTable();
		table.setShowGrid(true, true);
		table.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1 && experiments != null) {
					TableColumnModel cModel = table.getColumnModel();
					int column = cModel.getColumnIndexAtX(e.getX());
					sortData(column);
					createTreeStructure();
					repaint();
					revalidate();
				}
			}
		});
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	public void setContent(ExperimentData[] experimentData) {
		if(experimentData != null && experimentData.length > 0) {
			experiments = experimentData;
			/*Set new content to the tree table*/
			// headings
			nrOfColumns = 2 + experimentData[0].annotations.length;
			headings = new String[nrOfColumns];
			headings[0] = "Experiment Name";
			headings[1] = "Experiment Created By";
			for (int i = 0; i < nrOfColumns-2; i++) {
				headings[2 + i] = experiments[0].annotations[i].name;
			}
			/*Create the data matrix from the experiment data*/
			data = new String[experiments.length][headings.length];
			for (int i = 0; i < experiments.length; i++) {
				data[i] = experiments[i].getAnnotationValueList();
			}
			/*Initate the sorting orders as descending*/
			sortingOrders = new ArrayList<Boolean>();
			for(int i=0; i<this.headings.length; i++) {
				sortingOrders.add(i, true);
			}
			createTreeStructure();
		}
	}

	private void sortData(final int sortByColumn) {
		data = getDataFromTreeTable();
		headings = getHeadingsFromTreeTable();
		/*update the sorting orders for the columns*/
		for(int i=0; i<sortingOrders.size(); i++) {
			if(i==sortByColumn) {
				sortingOrders.set(i, !sortingOrders.get(i));
			} else {
				sortingOrders.set(i, true);
			}
		}
		/*Sorting method, can sort strings with numbers correctly*/
		Arrays.sort(data, new Comparator<String[]>() {
			private final Pattern PATTERN = Pattern.compile("(\\D*)(\\d*)");
			@Override
			public int compare(final String[] entry1, final String[] entry2) {
				Matcher m1 = PATTERN.matcher(entry1[sortByColumn]);
				Matcher m2 = PATTERN.matcher(entry2[sortByColumn]);
				/*The only way find() could fail is at the end of a string*/
				while (m1.find() && m2.find()) {
					/*matcher.group(1) fetches any non-digits captured by the
                    first parentheses in PATTERN.*/
					int nonDigitCompare;
					if (sortingOrders.get(sortByColumn)) {
						nonDigitCompare = m2.group(1).compareTo(m1.group(1));
					} else {
						nonDigitCompare = m1.group(1).compareTo(m2.group(1));
					}
					if (0 != nonDigitCompare) {
						return nonDigitCompare;
					}

					/*matcher.group(2) fetches any digits captured by the
                    second parentheses in PATTERN.*/
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

				/*Handle if one string is a prefix of the other.
                 Nothing comes before something. */
				return m1.hitEnd() && m2.hitEnd() ? 0 :
					m1.hitEnd()                ? -1 : +1;
			}
		});
	}

	private String[][] getDataFromTreeTable() {
		/*get the data from the existing JXTreeTable (to keep column orders)*/
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
		/*get the headings from the existing JXTreeTable (to keep column orders)*/
		String[] headings = new String[table.getColumnCount()];
		for (int i = 0; i < table.getColumnCount(); i++) {
			headings[i] = table.getColumnName(i);
		}
		return headings;
	}

	private ExperimentData getExperimentFromData(String[] data) {
		/*Get the ExperimentData object corresponding to the input data*/
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
	public ExperimentData[] getContent() {
		return experiments;
	}

	public ArrayList<FileData> getSelectedFiles() {
		/*Get the files that are selected in the table*/
		ArrayList<FileData> files = new ArrayList<FileData>();
		int[] rows = table.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < experiments.length; j++) {
				ExperimentData expData = experiments[j];
				for (int k = 0; k < expData.files.length; k++) {
					String fileRow = expData.files[k].name + " (" + expData.files[k].size + ")";
					if (fileRow.equals(table.getValueAt(
							rows[i], 0))) {
						files.add(expData.files[k]);
					}
				}
			}
		}
		return files;
	}

	public ArrayList<ExperimentData> getSelectedFilesWithExperiments() {
		
		return null;
	}

	private void createTreeStructure() {
		/*Create the tree root*/
		Node root = new Node(new Object[] { "Root" });

		for (int i = 0; i < experiments.length; i++) {
			/*Create experiment node and add to root*/
			Node child = new Node(data[i]);
			root.add(child);
			/*Create raw files node*/
			Node rawFiles = new Node(new String[] { "Raw Files" });
			/*Create profile files node*/
			Node profileFiles = new Node(new String[] { "Profile Files" });
			/*Create region files node*/
			Node regionFiles = new Node(new String[] { "Region Files" });
			ExperimentData currentExperiment = getExperimentFromData(data[i]);
			FileData[] fileData = currentExperiment.files;
			/*Loop through all files in the current experiment and create nodes for them*/
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
			/*add the files nodes*/
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
		/*Create the model and add it to the table*/
		DefaultTreeTableModel model = new DefaultTreeTableModel(root, Arrays.asList(headings));
		table.setTreeTableModel(model);
		table.packAll();
	}
}