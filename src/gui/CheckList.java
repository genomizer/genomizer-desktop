package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class CheckList {
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create a list containing CheckListItem's

		JList list = new JList(new CheckListItem[] {
				new CheckListItem("apple"), new CheckListItem("orange"),
				new CheckListItem("mango"), new CheckListItem("paw paw"),
				new CheckListItem("banana") });

		// Use a CheckListRenderer (see below)
		// to renderer list cells

		list.setCellRenderer(new CheckListRenderer());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Add a mouse listener to handle changing selection

		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				JList list = (JList) event.getSource();

				// Get index of item clicked

				int index = list.locationToIndex(event.getPoint());
				CheckListItem item = (CheckListItem) list.getModel()
						.getElementAt(index);

				// Toggle selected state

				item.setSelected(!item.isSelected());

				// Repaint cell

				list.repaint(list.getCellBounds(index, index));
			}
		});

		frame.getContentPane().add(new JScrollPane(list));
		frame.pack();
		frame.setVisible(true);
	}
}