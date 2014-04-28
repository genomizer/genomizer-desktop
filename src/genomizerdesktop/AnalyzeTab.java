package genomizerdesktop;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class AnalyzeTab extends JPanel {

	private static final long serialVersionUID = 9115232261189442160L;

	private JPanel northPanel, southPanel;
	private JLabel filesLabel, overlapLabel, regionAnalysisLabel;
	private JLabel profileAnalysisLabel;

	public AnalyzeTab() {

		setLayout(new BorderLayout());
		northPanel = new JPanel(new BorderLayout());
		southPanel = new JPanel(new BorderLayout());
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);

		filesLabel = new JLabel("Files");
		overlapLabel = new JLabel("Overlap");
		regionAnalysisLabel = new JLabel("Region analysis");
		profileAnalysisLabel = new JLabel("Profile analysis");

		northPanel.add(filesLabel, BorderLayout.WEST);
		northPanel.add(overlapLabel, BorderLayout.EAST);
		southPanel.add(regionAnalysisLabel, BorderLayout.WEST);
		southPanel.add(profileAnalysisLabel, BorderLayout.EAST);
	}
}
