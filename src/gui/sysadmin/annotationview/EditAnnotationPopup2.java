package gui.sysadmin.annotationview;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import util.AnnotationDataType;

public class EditAnnotationPopup2 extends JPanel {
    
    private JTable table;
    private AnnotationDataType annotation;
    
    public EditAnnotationPopup2(JTable table) {
        this.table = table;
        if (!setAnnotation()) {
            JOptionPane.showMessageDialog(null,
                    "Please select an annotation to edit");
            this.setEnabled(false);
        }
        this.setLayout(new BorderLayout());
        createAnnotationNamePanel();
        // createForcedPanel();
        createValuesPanel();
        
    }
    
    private void createValuesPanel() {
        
    }
    
    private void createAnnotationNamePanel() {
        JPanel annotationNamePanel = new JPanel();
        GroupLayout layout = new GroupLayout(annotationNamePanel);
        annotationNamePanel.setLayout(layout);
        
        JLabel name = new JLabel("Name: ");
        annotationNamePanel.add(name);
        
        JLabel nameLabel = new JLabel(annotation.name);
        Font newLabelFont = new Font(nameLabel.getFont().getName(), Font.BOLD,
                nameLabel.getFont().getSize());
        nameLabel.setFont(newLabelFont);
        annotationNamePanel.add(nameLabel);
        
        JButton rename = new JButton("Rename");
        rename.setMinimumSize(new Dimension(80, 10));
        annotationNamePanel.add(rename);
        
        JButton forced = new JButton("set Required");
        forced.setMinimumSize(new Dimension(80, 10));
        annotationNamePanel.add(forced);
        
        
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
                
                        .addComponent(rename).addComponent(forced)
                        );
        
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(rename).addComponent(forced)
                ));
        
        this.add(annotationNamePanel);
    }
    
    /**
     * Sets the local value for the annotation variable
     */
    private boolean setAnnotation() {
        if (table.getSelectedRow() != -1) {
            int row = table.getSelectedRow();
            row = table.convertRowIndexToModel(row);
            int col = 3;
            annotation = (AnnotationDataType) table.getModel().getValueAt(row,
                    col);
            return true;
        } else {
            System.out.println("You must select an annotation to edit");
            return false;
        }
    }

    public AnnotationDataType getAnnotation() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getNewAnnotationName() {
        // TODO Auto-generated method stub
        return null;
    }

    public Boolean getNewAnnotationForcedValue() {
        // TODO Auto-generated method stub
        return null;
    }

    public String[] getNewAnnotationCategories() {
        // TODO Auto-generated method stub
        return null;
    }
}
