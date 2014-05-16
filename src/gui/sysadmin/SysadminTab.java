package gui.sysadmin;

import gui.sysadmin.annotationview.AnnotationsViewCreator;
import gui.sysadmin.genomereleaseview.GenomeButtonListener;
import gui.sysadmin.genomereleaseview.GenomeReleaseViewCreator;
import gui.sysadmin.processview.ProcessViewCreator;
import gui.sysadmin.strings.SysadminTabButtons;
import gui.sysadmin.usersview.UsersViewCreator;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

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

    /**
     * Create the panel.
     */
    public SysadminTab() {
        setLayout(new BorderLayout());
        this.annotationsView = new AnnotationsViewCreator();
        this.usersView = new UsersViewCreator();
        this.processView = new ProcessViewCreator();

        ActionListener listener = new GenomeButtonListener(this);
        this.genomeReleaseView = new GenomeReleaseViewCreator(listener);

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
        return genomeReleaseView.buildGenomeReleaseView();
    }

    public void setController(SysadminController sysController) {
        this.sysController = sysController;
        this.sysController.setSysadminPanel(this);
        createSysadminTabs();

        createAnnotationListeners();

    }

    public void addAnnotationsPopup() {
        /* TODO FIX THIS SHIT! */
        annotationsView.popup(createAnnotationPopupListener());

        // annotationsView.addPopup(createAnnotationPopupListener());
    }

    public void editAnnotationPopup() {

        annotationsView.editPopup(editAnnotationPopupListener());
    }

    public void createAnnotationListeners() {
        annotationsView.addAnnotationListener(sysController
                .createAnnotationButtonListener());
    }

    public ActionListener createAnnotationPopupListener() {
        return sysController.createAnnotationPopupListener();
    }

    public ActionListener editAnnotationPopupListener() {
        return sysController.createEditAnnotationPopupListener();
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
}
