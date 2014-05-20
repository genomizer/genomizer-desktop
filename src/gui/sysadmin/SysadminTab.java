package gui.sysadmin;

import gui.sysadmin.annotationview.AddAnnotationPopup;
import gui.sysadmin.annotationview.AnnotationPopupListener;
import gui.sysadmin.annotationview.AnnotationsViewCreator;
import gui.sysadmin.annotationview.EditAnnotationPopup2;
import gui.sysadmin.annotationview.EditAnnotationPopupListener;
import gui.sysadmin.genomereleaseview.GenomeButtonListener;
import gui.sysadmin.genomereleaseview.GenomeReleaseViewCreator;
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

    private JTabbedPane              sysadminTabPane;
    private SysadminController       sysController;
    private AnnotationsViewCreator   annotationsView;
    private UsersViewCreator         usersView;
    private ProcessViewCreator       processView;
    private GenomeReleaseViewCreator genomeReleaseView;
    private AddAnnotationPopup pop;
    private EditAnnotationPopup2 editPopup;

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

    private JPanel buildGenomeReleaseView() {
        ActionListener listener = new GenomeButtonListener(this);
        GenomeTextFieldListener textListener = new GenomeTextFieldListener(genomeReleaseView);
        return genomeReleaseView.buildGenomeReleaseView(listener, textListener);
    }

    public void setController(SysadminController sysController) {
        this.sysController = sysController;
        this.sysController.setSysadminPanel(this);
        createSysadminTabs();

        createAnnotationListeners();

    }

    public void createAnnotationListeners() {
        annotationsView.addAnnotationListener(sysController
                .createAnnotationButtonListener());
    }

    public AnnotationsViewCreator getAnnotationsView() {
        return annotationsView;
    }

    public SysadminController getSysController() {
        return sysController;
    }

    public TableModel getAnnotationTableModel() {
        return annotationsView.getTableModel();
    }

    public void deleteAnnotation() {
        sysController.deleteAnnotation();

    }

    public JTable getAnnotationTable() {
        return annotationsView.getTable();
    }

    public TableModel getGenomeReleaseTableModel() {

        return genomeReleaseView.getTableModel();
    }

    public GenomeReleaseViewCreator getGenomeReleaseView() {
        return genomeReleaseView;
    }

    public void addAnnotationsPopup() {
        pop = new AddAnnotationPopup();
        pop.setBackground(Color.WHITE);
        ActionListener popupListener = new AnnotationPopupListener(this);
        pop.addAddAnnotationListener(popupListener);

        JFrame popupFrame = new JFrame("Add new Annotation");
        popupFrame.setLayout(new BorderLayout());
        popupFrame.add(pop, BorderLayout.CENTER);
        popupFrame.pack();
        popupFrame.setLocationRelativeTo(null);
        popupFrame.setSize(new Dimension(600, 600));
        popupFrame.setVisible(true);
    }

    public void editAnnotationPopup() {

        System.out.println("Skapar editAnnotationPopup...");
        editPopup = new EditAnnotationPopup2(annotationsView.getTable());
        if (editPopup.isEnabled()) {
            editPopup.setBackground(Color.WHITE);
            ActionListener editPopupListener = new EditAnnotationPopupListener(this);
            editPopup.addEditAnnotationListener(editPopupListener);

            JFrame popupFrame = new JFrame("Edit annotation");
            popupFrame.setLayout(new BorderLayout());
            popupFrame.add(editPopup, BorderLayout.CENTER);
            popupFrame.pack();
            popupFrame.setLocationRelativeTo(null);
            popupFrame.setSize(new Dimension(600, 600));
            popupFrame.setVisible(true);
        }
    }

    public EditAnnotationPopup2 getEditPopup() {
        return editPopup;
    }

    public AddAnnotationPopup getPop() {
        return pop;
    }
}
