package util;

import java.util.ArrayList;

import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

/**
 * A custom node implementation (For TreeTable).
 */
public class ExperimentNode extends AbstractMutableTreeTableNode implements
        Comparable {
    
    private ExperimentData experiment;
    private String[]       fileHeaders = new String[] {
            "<html><b>File Name</html></b>", "<html><b>Date Added</html></b>",
            "<html><b>Uploaded By</html></b>" };
    
    public ExperimentNode(ExperimentData experiment, ArrayList<String> headings) {
        
        super(experiment.getAnnotationValueList(headings).toArray());
        this.experiment = experiment;
        ArrayList<FileData> files = experiment.files;
        SupportNode rawNode = new SupportNode(new Object[] { "Raw Files" });
        rawNode.add(new SupportNode(fileHeaders));
        SupportNode profileNode = new SupportNode(
                new Object[] { "Profile Files" });
        profileNode.add(new SupportNode(fileHeaders));
        SupportNode regionNode = new SupportNode(
                new Object[] { "Region Files" });
        regionNode.add(new SupportNode(fileHeaders));
        for (FileData fileData : files) {
            FileNode fileNode = new FileNode(fileData);
            if (fileData.type.equals("Raw")) {
                rawNode.add(fileNode);
            } else if (fileData.type.equals("Profile")) {
                profileNode.add(fileNode);
            } else if (fileData.type.equals("Region")) {
                regionNode.add(fileNode);
            }
        }
        if (rawNode.getChildCount() > 1) {
            add(rawNode);
        }
        if (regionNode.getChildCount() > 1) {
            add(regionNode);
        }
        if (profileNode.getChildCount() > 1) {
            add(profileNode);
        }
    }
    
    @Override
    public Object getValueAt(int columnIndex) {
        return getData()[columnIndex];
    }
    
    @Override
    public int getColumnCount() {
        return getData().length;
    }
    
    public Object[] getData() {
        return (Object[]) super.getUserObject();
    }
    
    public ExperimentData getExperiment() {
        return experiment;
    }
    
    @Override
    public int compareTo(Object o) {
        ExperimentNode node = (ExperimentNode) o;
        if (node.getExperiment().equals(experiment)) {
            return 0;
        }
        return -1;
    }
    
}
