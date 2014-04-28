package genomizerdesktop;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class AnalyzeTab extends JPanel {

	private static final long serialVersionUID = 9115232261189442160L;

	private JPanel northPanel, northWestPanel, southPanel;
	private JLabel filesLabel, overlapLabel, regionAnalysisLabel;
	private JLabel profileAnalysisLabel;
	private JList list;

	public AnalyzeTab() {

		setLayout(new BorderLayout());
		northPanel = new JPanel(new BorderLayout());
		northWestPanel = new JPanel(new BorderLayout());
		southPanel = new JPanel(new BorderLayout());
		add(northPanel, BorderLayout.NORTH);
		northPanel.add(northWestPanel, BorderLayout.WEST);
		add(southPanel, BorderLayout.SOUTH);

		filesLabel = new JLabel("Files");
		overlapLabel = new JLabel("Overlap");
		regionAnalysisLabel = new JLabel("Region analysis");
		profileAnalysisLabel = new JLabel("Profile analysis");
		
		DefaultListModel listModel = new DefaultListModel();
		
		for(int i=0; i<10; i++) {
			listModel.addElement("Protein223_A5_2014.WIG");
		}
		
		list = new JList(listModel);
		list.setPreferredSize(new Dimension(300,400));
		
		JScrollPane scrollPane = new JScrollPane(list);

		northWestPanel.add(filesLabel, BorderLayout.NORTH);
		northWestPanel.add(scrollPane, BorderLayout.SOUTH);
		northPanel.add(overlapLabel, BorderLayout.EAST);
		southPanel.add(regionAnalysisLabel, BorderLayout.WEST);
		southPanel.add(profileAnalysisLabel, BorderLayout.EAST);
	}
}
