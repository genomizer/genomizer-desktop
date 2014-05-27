package gui.sysadmin;

import gui.sysadmin.annotationview.AddAnnotationPopup;
import gui.sysadmin.annotationview.AddAnnotationPopupListener;
import gui.sysadmin.annotationview.AnnotationsViewCreator;
import gui.sysadmin.annotationview.EditAnnotationPopup2;
import gui.sysadmin.annotationview.EditAnnotationPopupListener;
import gui.sysadmin.genomereleaseview.GenomeButtonListener;
import gui.sysadmin.genomereleaseview.GenomeReleaseViewCreator;
import gui.sysadmin.genomereleaseview.GenomeTableListener;
import gui.sysadmin.genomereleaseview.GenomeTextFieldListener;
import gui.sysadmin.processview.ProcessViewCreator;
import gui.sysadmin.strings.SysadminTabButtons;
import gui.sysadmin.usersview.UsersViewCreator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class SysadminTab extends JPanel {
    
    private JTabbedPane sysadminTabPane;
    private SysadminController sysController;
    private AnnotationsViewCreator annotationsView;
    private UsersViewCreator usersView;
    private ProcessViewCreator processView;
    private GenomeReleaseViewCreator genomeReleaseView;
    private AddAnnotationPopup pop;
    private EditAnnotationPopup2 editPopup;
    private JFrame editFrame;
    private JFrame newAnnotationFrame;
    
    /**
     * Create the panel.
     */
    public SysadminTab() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory
                .createTitledBorder("System administrator tools"));
        this.annotationsView = new AnnotationsViewCreator();
        this.usersView = new UsersViewCreator();
        this.processView = new ProcessViewCreator();
        this.genomeReleaseView = new GenomeReleaseViewCreator();
        
    }
    
    /**
     * Creates the tabs within the program's admin tab
     */
    private void createSysadminTabs() {
        
        sysadminTabPane = new JTabbedPane();
        sysadminTabPane.addChangeListener(new SysadminTabChangeListener(
                sysController));
        
        sysadminTabPane.setTabPlacement(JTabbedPane.LEFT);
        // SysStrings bNames = new SysStrings();
        // String[] buttonNameStrings = bNames.getButtonNames();
        
        for (SysadminTabButtons button : SysadminTabButtons.values()) {
            switch (button) {
                case ANNOTATIONS:
                    sysadminTabPane.addTab(button.getValue(),
                            buildAnnotationsView());
                    break;
                case GENOMES:
                    sysadminTabPane.addTab(button.getValue(),
                            buildGenomeReleaseView());
                    break;
            }
        }
        add(sysadminTabPane);
    }
    
    /**
     * @return a JPanel with the annotations view
     */
    private JPanel buildAnnotationsView() {
        JPanel panel = annotationsView.buildAnnotationsView();
        return panel;
    }
    
    private JPanel buildUsersView() {
        
        return usersView.buildUsersView();
    }
    
    private JPanel buildProcessView() {
        return processView.buildProcessView();
    }
    
    /**
     * @return a JPanel with the genome release view, connected to its listeners
     */
    private JPanel buildGenomeReleaseView() {
        ActionListener listener = new GenomeButtonListener(this);
        GenomeTextFieldListener textListener = new GenomeTextFieldListener(
                genomeReleaseView);
        GenomeTableListener tableListener = new GenomeTableListener(this);
        return genomeReleaseView.buildGenomeReleaseView(listener, textListener,
                tableListener, tableListener);
    }
    
    /**
     * Sets the controller connected to the SysadminTab
     * 
     * @param sysController
     *            is the controller to be connected
     */
    public void setController(SysadminController sysController) {
        this.sysController = sysController;
        this.sysController.setSysadminPanel(this);
        createSysadminTabs();
        
        createAnnotationListeners();
        
    }
    
    /**
     * Adds AnnotationButtonListener to the annotation view
     */
    public void createAnnotationListeners() {
        annotationsView.addAnnotationListener(sysController
                .createAnnotationButtonListener());
    }
    
    /**
     * @return the AnnotationsViewCreate connected to the SysadminTab
     */
    public AnnotationsViewCreator getAnnotationsView() {
        return annotationsView;
    }
    
    /**
     * @return the controller connected to the SysadminTab
     */
    public SysadminController getSysController() {
        return sysController;
    }
    
    /**
     * @return the table model for the annotations table
     */
    public TableModel getAnnotationTableModel() {
        return annotationsView.getTableModel();
    }
    
    /**
     * Messages the controller to remove an annotation
     */
    public void deleteAnnotation() {
        sysController.deleteAnnotation();
    }
    
    /**
     * @return the annotation table in the annotation view
     */
    public JTable getAnnotationTable() {
        return annotationsView.getTable();
    }
    
    /**
     * @return the table model of the genome release view
     */
    public TableModel getGenomeReleaseTableModel() {
        
        return genomeReleaseView.getTableModel();
    }
    
    /**
     * @return the GenomeReleaseViewCreator connected to the SysadminTab
     */
    public GenomeReleaseViewCreator getGenomeReleaseView() {
        return genomeReleaseView;
    }
    
    /**
     * Creates a popup window in which the user is able to add new annotations.
     * Also assigns its frame to the newAnnotationFrame field.
     */
    public void addAnnotationsPopup() {
        pop = new AddAnnotationPopup();
        pop.setBackground(Color.WHITE);
        ActionListener popupListener = new AddAnnotationPopupListener(this);
        pop.addAddAnnotationListener(popupListener);
        
        JFrame popupFrame = new JFrame("Add new Annotation");
        popupFrame.setLayout(new BorderLayout());
        popupFrame.add(pop, BorderLayout.CENTER);
        popupFrame.pack();
        popupFrame.setLocationRelativeTo(null);
        popupFrame.setSize(new Dimension(600, 600));
        popupFrame.setVisible(true);
        newAnnotationFrame = popupFrame;
    }
    
    /**
     * Creates a popup window in which the user is able to edit annotations.
     * Also assigns its frame to the editFrame field.
     */
    public void editAnnotationPopup() {
        
        System.out.println("Skapar editAnnotationPopup...");
        editPopup = new EditAnnotationPopup2(annotationsView.getTable());
        if (editPopup.isEnabled()) {
            ActionListener editPopupListener = new EditAnnotationPopupListener(
                    this);
            editPopup.addEditAnnotationListener(editPopupListener);
            
            JFrame popupFrame = new JFrame("Edit annotation");
            popupFrame.add(editPopup, BorderLayout.CENTER);
            popupFrame.pack();
            popupFrame.setLocationRelativeTo(null);
            popupFrame.setResizable(false);
            popupFrame.setSize(new Dimension(600, 600));
            popupFrame.setVisible(true);
            editFrame = popupFrame;
        }
    }
    
    /**
     * @return the frame of the edit popup
     */
    public EditAnnotationPopup2 getEditPopup() {
        return editPopup;
    }
    
    /**
     * @return the frame of the edit popup
     */
    public JFrame getEditFrame() {
        return editFrame;
    }
    
    /**
     * @return the frame of the new annotation popup
     */
    public JFrame getNewAnnotationFrame() {
        return newAnnotationFrame;
    }
    
    /**
     * @return the frame of the new annotation popup
     */
    public AddAnnotationPopup getPop() {
        return pop;
    }
}
