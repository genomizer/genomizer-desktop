package gui.sysadmin;

import gui.sysadmin.annotationview.AnnotationsViewCreator;
import gui.sysadmin.processview.ProcessViewCreator;
import gui.sysadmin.strings.SysadminTabButtons;
import gui.sysadmin.usersview.UsersViewCreator;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import util.AnnotationDataType;

public class SysadminTab extends JPanel {

    private JTabbedPane sysadminTabPane;
    private SysadminController sysController;
    private AnnotationsViewCreator annotationsView;
    private UsersViewCreator usersView;
    private ProcessViewCreator processView;

    /**
     * Create the panel.
     */
    public SysadminTab() {
        setLayout(new BorderLayout());
        this.annotationsView = new AnnotationsViewCreator();
        this.usersView = new UsersViewCreator();
        this.processView = new ProcessViewCreator();

    }

    private void createSysadminTabs() {

        sysadminTabPane = new JTabbedPane();

        sysadminTabPane.setTabPlacement(JTabbedPane.LEFT);
//        SysStrings bNames = new SysStrings();
//        String[] buttonNameStrings = bNames.getButtonNames();

        for (SysadminTabButtons button : SysadminTabButtons.values()) {
            switch (button) {
                case ANNOTATIONS:
                    sysadminTabPane.addTab(button.getValue(),
                            buildAnnotationsView());
                    break;
                case USERS:
                    sysadminTabPane.addTab(button.getValue(), buildUsersView());
                    break;
                case PROCESS:
                    sysadminTabPane.addTab(button.getValue(), buildProcessView());
                    break;
            }
        }

//        for (int i = 0; i < buttonNameStrings.length; i++) {
//
//            switch (buttonNameStrings[i]) {
//
//            case SysStrings.ANNOTATIONS:
//                sysadminTabPane.addTab(buttonNameStrings[i],
//                        buildAnnotationsView());
//                break;
///*
//            case SysStrings.USERS:
//                sysadminTabPane.addTab(buttonNameStrings[i], new JPanel());
//                break;
//
//            case SysStrings.TEST:
//                sysadminTabPane.addTab(buttonNameStrings[i], new JPanel());
//                break;
//*/
//            }
//
//        }

        System.out.println("HEHEHE " + sysadminTabPane.getTitleAt(0));

        add(sysadminTabPane);
    }

    private JPanel buildAnnotationsView() {
    	JPanel panel = annotationsView.buildAnnotationsView();
        sysController.updateAnnotationTable();
    	return panel;
    }

    private JPanel buildUsersView() {

        return usersView.buildUsersView();
    }

    private JPanel buildProcessView(){
        return processView.buildProcessView();
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

    }

    public void createAnnotationListeners() {
        annotationsView.addAnnotationListener(sysController
                .createAnnotationButtonListener());
    }

    public ActionListener createAnnotationPopupListener() {
        return sysController.createAnnotationPopupListener();
    }

    public AnnotationsViewCreator getAnnotationsView() {
        return annotationsView;
    }

    public SysadminController getSysController() {
        return sysController;
    }

    public TableModel getAnnotationTableModel(){
    	return annotationsView.getTableModel();
    }

	public void deleteAnnotation() {
		sysController.deleteAnnotation();
		
	}

	public JTable getAnnotationTable() {
		return annotationsView.getTable();
	}
}
