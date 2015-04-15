package gui;

import javax.swing.*;
import java.awt.*;

public class AnalyzeTab extends JPanel {

    private static final long serialVersionUID = 9115232261189442160L;

    private JPanel northWestPanel, northEastPanel;
    private JPanel southWestPanel, southEastPanel;
    private JLabel filesLabel, overlapLabel, regionAnalysisLabel;
    private JLabel profileAnalysisLabel;
  //  private JCheckBoxList list;

    // TODO: WTF What is all these outcommented list-things? OO
    public AnalyzeTab() {

        setLayout(new BorderLayout());
        JPanel northPanel = new JPanel(new BorderLayout());
        JPanel southPanel = new JPanel(new BorderLayout());
        northWestPanel = new JPanel(new BorderLayout());
        northEastPanel = new JPanel(new BorderLayout());
        southWestPanel = new JPanel(new BorderLayout());
        southEastPanel = new JPanel(new BorderLayout());

        add(northPanel, BorderLayout.NORTH);
        northPanel.add(northWestPanel, BorderLayout.WEST);
        northPanel.add(northEastPanel, BorderLayout.EAST);
        add(southPanel, BorderLayout.SOUTH);
        southPanel.add(southWestPanel, BorderLayout.WEST);
        southPanel.add(southEastPanel, BorderLayout.EAST);

        createLabels();

        DefaultListModel listModel = new DefaultListModel();

     //   list = new JCheckBoxList();
    //    list.setModel(listModel);
    //    list.setPreferredSize(new Dimension(300, 400));

        int numberOfJobs;

        for (numberOfJobs = 0; numberOfJobs < 12; numberOfJobs++) {
            listModel
                    .add(numberOfJobs, new JCheckBox("Protein223_A5_2014.WIG"));

        }

      //  JScrollPane scrollPane = new JScrollPane(list);

        northWestPanel.add(filesLabel, BorderLayout.NORTH);
     //   northWestPanel.add(scrollPane, BorderLayout.SOUTH);
        northEastPanel.add(overlapLabel, BorderLayout.NORTH);
        southWestPanel.add(regionAnalysisLabel, BorderLayout.NORTH);
        southEastPanel.add(profileAnalysisLabel, BorderLayout.NORTH);
        southEastPanel.add(new JLabel("Number of jobs currently in queue: "
                        + numberOfJobs + " est. time until empty: N/A)."),
                BorderLayout.SOUTH
        );
    }

    private void createLabels() {
        filesLabel = new JLabel("Files");
        filesLabel.setBackground(Color.CYAN);
        filesLabel.setOpaque(true);

        overlapLabel = new JLabel("Overlap");
        overlapLabel.setBackground(Color.CYAN);
        overlapLabel.setOpaque(true);

        regionAnalysisLabel = new JLabel("Region analysis");
        regionAnalysisLabel.setBackground(Color.CYAN);
        regionAnalysisLabel.setOpaque(true);

        profileAnalysisLabel = new JLabel("Profile analysis");
        profileAnalysisLabel.setBackground(Color.CYAN);
        profileAnalysisLabel.setOpaque(true);
    }
}
