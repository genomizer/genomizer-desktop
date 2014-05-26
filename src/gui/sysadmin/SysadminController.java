package gui.sysadmin;

import gui.sysadmin.annotationview.AddAnnotationPopup;
import gui.sysadmin.annotationview.AnnotationButtonsListener;
import gui.sysadmin.annotationview.AnnotationTableModel;
import gui.sysadmin.annotationview.EditAnnotationPopup2;
import gui.sysadmin.genomereleaseview.GenomeReleaseViewCreator;
import gui.sysadmin.genomereleaseview.GenomereleaseTableModel;

import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.GenomizerModel;
import util.AnnotationDataType;
import util.GenomeReleaseData;

/**
 * The controller for the admin part of the program.
 */
public class SysadminController {
    
    private SysadminTab sysTab;
    private GenomizerModel model;
    
    public SysadminController() {
        
    }
    
    /**
     * Constructs a controller for the admin part of the program
     * 
     * @param model
     *            is the model with which the controller communicates
     */
    public SysadminController(GenomizerModel model) {
        this.model = model;
    }
    
    /**
     * Creates a listener for the buttons in the sysadmin tab.
     * 
     * @return a new AnnotationButtonsListener
     */
    public ActionListener createAnnotationButtonListener() {
        return new AnnotationButtonsListener(sysTab);
    }
    
    /**
     * Sets the tab which the controller is connected to
     * 
     * @param sysTab
     */
    public void setSysadminPanel(SysadminTab sysTab) {
        
        this.sysTab = sysTab;
        
    }
    
    /**
     * Sends a message to the model to make a new annotation.
     */
    public void sendNewAnnotation() {
        AddAnnotationPopup popup = sysTab.getPop();
        try {
            model.addNewAnnotation(popup.getNewAnnotationName(),
                    popup.getNewAnnotationCategories(),
                    popup.getNewAnnotationForcedValue());
            updateAnnotationTable();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    /**
     * @return the list of current annotations from the database
     */
    public util.AnnotationDataType[] getAnnotations() {
        return model.getAnnotations();
    }
    
    /**
     * @return a string array with the values of the "species"-annotation.
     */
    public String[] getSpecies() {
        
        AnnotationDataType[] annotations = model.getAnnotations();
        
        for (AnnotationDataType a : annotations) {
            
            if (a.getName().equals("Species")) {
                
                System.out.println("FOUND SPECIES!");
                return a.getValues();
            }
        }
        
        return null;
    }
    
    /**
     * Removes the annotation currently highlighted in the annotation table. If
     * no annotation is selected, an error message will be shown.
     */
    public void deleteAnnotation() {
        
        if (sysTab.getAnnotationTable().getSelectedRow() != -1) {
            int row = sysTab.getAnnotationTable().getSelectedRow();
            row = sysTab.getAnnotationTable().convertRowIndexToModel(row);
            int col = 3;
            AnnotationDataType annotation = (AnnotationDataType) sysTab
                    .getAnnotationTable().getModel().getValueAt(row, col);
            
            try {
                if (JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete the "
                                + annotation.name + " annotation?",
                        "Remove annotation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (model.deleteAnnotation(annotation.name)) {
                        JOptionPane.showMessageDialog(null, annotation.name
                                + " has been removed!");
                        SwingUtilities.invokeLater(new Runnable() {
                            
                            @Override
                            public void run() {
                                updateAnnotationTable();
                            }
                        });
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Could not remove annotation");
                    }
                }
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } else {
            System.out.println("Please select an annotation to delete");
        }
    }
    
    public util.GenomeReleaseData[] getGenomeReleases() {
        
        GenomeReleaseData[] grdarray = null;
        
        try {
            grdarray = model.getGenomeReleases();
            if (!(grdarray == null)) {
                if (grdarray.length == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Could not get genomereleases!");
                }
            }
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return grdarray;
        
    }
    
    public void deleteGenomeRelease(String version, String specie) {
        
        if (model.deleteGenomeRelease(specie, version)) {
            setGenomeReleaseTable();
        }
    }
    
    /**
     * Updates the table model of the table containing the current annotations.
     */
    public void updateAnnotationTable() {
        AnnotationTableModel tableModel = (AnnotationTableModel) sysTab
                .getAnnotationsView().getTableModel();
        tableModel.setAnnotations(getAnnotations());
    }
    
    public void setGenomeReleaseTable() {
        
        GenomereleaseTableModel tableModel = (GenomereleaseTableModel) sysTab
                .getGenomeReleaseTableModel();
        
        tableModel.setGenomeReleases(getGenomeReleases());
    }
    
    public void sendNewGenomeRelease() {
        GenomeReleaseViewCreator gr = sysTab.getGenomeReleaseView();
        model.uploadGenomeReleaseFile(gr.getFileText(), gr.getSpeciesText(),
                gr.getVersionText());
        setGenomeReleaseTable();
    }
    
    public void clearAddGenomeText() {
        GenomeReleaseViewCreator gr = sysTab.getGenomeReleaseView();
        gr.clearTextFields();
    }
    
    /**
     * Sends a message to the model to rename an annotation
     * 
     * @param oldName
     *            is the annotation to be renamed
     * @param newName
     *            is the new name
     * @return true if successfully renamed, otherwise false
     */
    public boolean renameAnnotationField(String oldName, String newName) {
        return (model.renameAnnotationField(oldName, newName));
    }
    
    /**
     * Sends a message to the model to rename an annotation value
     * 
     * @param name
     *            is the name of the annotation
     * @param oldValue
     *            is the name of the annotation value to be renamed
     * @param newValue
     *            is the new name for the value
     * @return true if successfully renamed, otherwise false
     */
    public boolean renameAnnotationValue(String name, String oldValue,
            String newValue) {
        return model.renameAnnotationValue(name, oldValue, newValue);
        
    }
    
    /**
     * Sends a message to the model to remove an annotation value
     * 
     * @param annotationName
     *            is the name of the annotation containing the value
     * @param annotationValue
     *            is the value to be removed
     * @return true if successfully removed, otherwise false
     */
    public boolean removeAnnotationValue(String annotationName,
            String annotationValue) {
        return model.removeAnnotationValue(annotationName, annotationValue);
        
    }
    
    /**
     * Adds a value to an annotation
     * 
     * @param annotationName
     *            the name of the annotation
     * @param valueName
     *            the name of the new value
     * @return true if successfully created, otherwise false
     */
    public boolean addAnnotationValue(String annotationName, String valueName) {
        return model.addNewAnnotationValue(annotationName, valueName);
    }
    
    /**
     * @return the SysadminTab connected to the controller
     */
    public SysadminTab getSysTab() {
        return sysTab;
    }
    
    public boolean addGenomRelease() {
        return model.addGenomeRelease();
    }
}
