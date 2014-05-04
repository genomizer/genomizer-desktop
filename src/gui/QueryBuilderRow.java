package gui;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class QueryBuilderRow extends JPanel {
		private JComboBox annotationField;
		private JTextField textField;
		private JButton plusButton;
		private JButton minusButton;
		private JComboBox logicField;
		private QuerySearchTab parent;
		private static final String[] logicOperators = { "AND", "NOT", "OR" };
		private static final String[] annotations = { "Uploader", "Date", "Sex",
			"Species", "ExperimentID", "Value", "Name" };

		public QueryBuilderRow(QuerySearchTab parent) {
			this.parent = parent;
			setLayout(new FlowLayout());
			setPlusButton();
			setMinusButton();
			setLogicBox();
			setFieldBox();
			setTextField();
		}

		public void setAs(Boolean firstRow, Boolean lastRow) {
			removeAll();

			if (firstRow && lastRow) {
				add(Box.createHorizontalStrut(73));
				add(annotationField);
				add(textField);
				add(plusButton);
				add(Box.createHorizontalStrut(20));
			} else if (firstRow && !lastRow) {
				add(Box.createHorizontalStrut(73));
				add(annotationField);
				add(textField);
				add(minusButton);
				add(Box.createHorizontalStrut(20));
			} else if (!firstRow && !lastRow) {
				add(logicField);
				add(annotationField);
				add(textField);
				add(minusButton);
				add(Box.createHorizontalStrut(20));
			} else {
				add(logicField);
				add(annotationField);
				add(textField);
				add(minusButton);
				add(plusButton);
			}
		}

		private void setPlusButton() {
			plusButton = new JButton();

			URL imageUrl = getClass().getResource("/icons/plus.png");
			ImageIcon plusIcon = new ImageIcon(imageUrl);
			plusIcon = new ImageIcon(plusIcon.getImage().getScaledInstance(15,
					15, BufferedImage.SCALE_SMOOTH));
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

		private void setMinusButton() {
			minusButton = new JButton();
			URL imageUrl = getClass().getResource("/icons/minus.png");
			ImageIcon minusIcon = new ImageIcon(imageUrl);
			minusIcon = new ImageIcon(minusIcon.getImage().getScaledInstance(
					15, 15, BufferedImage.SCALE_SMOOTH));
			minusButton.setBorderPainted(true);
			minusButton.setContentAreaFilled(false);
			minusButton.setPreferredSize(new Dimension(20, 20));
			minusButton.setFocusable(true);
			minusButton.setFocusPainted(false);
			minusButton.setIcon(minusIcon);
			final QueryBuilderRow row = this;
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
			annotationField = new JComboBox(annotations);
			annotationField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					parent.updateSearchArea();
				}
			});
		}

		private void setLogicBox() {
			logicField = new JComboBox(logicOperators);
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