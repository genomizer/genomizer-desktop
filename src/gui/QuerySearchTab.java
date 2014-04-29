package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class QuerySearchTab extends JPanel {
	private JPanel searchPanel;
	private JPanel builderPanel;
	private JButton clearButton;
	private JTextArea searchArea;
	private ArrayList<RowBuilder> rowList;
	private GridBagConstraints gbc;

	public QuerySearchTab() {
		createSearchPanel();
		add(searchPanel);
		createBuilderPanel();
		clearSearchFields();
	}

	private void createSearchPanel() {
		rowList = new ArrayList<RowBuilder>();
		setBorder(BorderFactory
				.createTitledBorder("Genomizer Advanced Search Builder"));
		FlowLayout fl = new FlowLayout();
		searchPanel = new JPanel(fl);
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
		searchArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		searchArea.setSize(1000, 20);
		searchPanel.add(searchArea);
		searchPanel.add(clearButton);
	}

	private void createBuilderPanel() {
		GridBagLayout gb = new GridBagLayout();
		builderPanel = new JPanel(gb);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridy = 0;
		gbc.gridx = 0;
		add(builderPanel);
	}

	private void clearSearchFields() {

		rowList = new ArrayList<RowBuilder>();
		addRow();
		addRow();
		searchArea.setText("Use the builder below to create your search");
		builderPanel.repaint();
		builderPanel.revalidate();
	}

	public void addRow() {
			rowList.add(new RowBuilder(this));
			showRows();
	}

	public void removeRow(RowBuilder row) {
		if (rowList.contains(row)) {
			rowList.remove(row);
		}
		showRows();
	}

	private void showRows() {
		builderPanel.removeAll();
		gbc.gridy = 0;
		for (int i = 0; i < rowList.size(); i++) {
			RowBuilder row = rowList.get(i);
			if (i == 0 && i == (rowList.size() - 1)) {
				System.out.println("both");
				row.setAs(true, true);
			} else if (i == 0 && i != (rowList.size() - 1)) {
				row.setAs(true, false);
			} else if (i != 0 && i == (rowList.size() - 1)) {
				row.setAs(false, true);
			} else {
				row.setAs(false, false);
			}
			builderPanel.add(row, gbc);
			gbc.gridy++;
		}
		builderPanel.repaint();
		builderPanel.revalidate();
	}

	public synchronized void updateSearchArea() {
		String searchString = "";
		int i = 0;
		for (RowBuilder row : rowList) {
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
		if(i != 0) {
			searchArea.setText(searchString);
		}
	}


	private class RowBuilder extends JPanel {
		private JComboBox<String> annotationField;
		private JTextField textField;
		private JButton plusButton;
		private JButton minusButton;
		private JComboBox<String> logicField;
		private QuerySearchTab parent;

		public RowBuilder(QuerySearchTab parent) {
			this.parent = parent;
			FlowLayout fl = new FlowLayout();
			setLayout(fl);
			setPlusButton();
			setMinus();
			setLogicBox();
			setFieldBox();
			setTextField();
		}

		public void setAs(Boolean firstRow, Boolean lastRow) {
			removeAll();
			if (firstRow) {
				add(Box.createHorizontalStrut(47));
			} else {
				add(logicField);
			}
			add(annotationField);
			add(textField);
			if (!firstRow) {
				add(minusButton);
			}
			if (lastRow) {
				add(plusButton);
			}
		}

		private void setPlusButton() {
			plusButton = new JButton();
			ImageIcon plusIcon = new ImageIcon("resources/plus.png");
			plusIcon = new ImageIcon(plusIcon.getImage()
					.getScaledInstance(15, 15,
							BufferedImage.SCALE_SMOOTH));
			plusButton.setBorderPainted(true);
			plusButton.setContentAreaFilled(false);
			plusButton.setPreferredSize(new Dimension(20, 20));
			plusButton.setFocusable(true);
			plusButton.setFocusPainted(false);
			plusButton.setIcon(plusIcon);
			plusButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					parent.addRow();
				}
			});
		}

		private void setMinus() {
			minusButton = new JButton();
			ImageIcon minusIcon = new ImageIcon("resources/minus.png");
			minusIcon = new ImageIcon(minusIcon.getImage()
					.getScaledInstance(15, 15,
							BufferedImage.SCALE_SMOOTH));
			minusButton.setBorderPainted(true);
			minusButton.setContentAreaFilled(false);
			minusButton.setPreferredSize(new Dimension(20, 20));
			minusButton.setFocusable(true);
			minusButton.setFocusPainted(false);
			minusButton.setIcon(minusIcon);
			final RowBuilder row = this;
			minusButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					parent.removeRow(row);
					parent.updateSearchArea();
				}
			});

		}

		private void setTextField() {
			textField = new JTextField(50);
			textField.getDocument().addDocumentListener(new DocumentListener() {
				public void changedUpdate(DocumentEvent e) {
					parent.updateSearchArea();
				}
				public void removeUpdate(DocumentEvent e) {
					parent.updateSearchArea();
				}
				public void insertUpdate(DocumentEvent e) {
					parent.updateSearchArea();
				}
			});
		}

		private void setFieldBox() {
			String[] fields = { "Author", "Book" };
			annotationField = new JComboBox<String>(fields);
			annotationField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					parent.updateSearchArea();
				}
			});
		}

		private void setLogicBox() {
			String[] logics = { "AND", "OR", "NOT" };
			logicField = new JComboBox<String>(logics);
			logicField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					parent.updateSearchArea();
				}
			});
		}

		public String getText() {
			return textField.getText();
		}

		public String getLogic() {
			return (String) logicField.getSelectedItem();
		}

		public String getAnnotation() {
			return (String) annotationField.getSelectedItem();
		}
	}
}
