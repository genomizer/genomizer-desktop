package gui.sysadmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SysadminTab extends JPanel {

    private JTabbedPane sysadminTabPane;
    private SysadminController sysController;
    private AnnotationsViewCreator annotationsView;
    private JPanel usersView;

    /**
     * Create the panel.
     */
    public SysadminTab() {
        setLayout(new BorderLayout());
        this.annotationsView = new AnnotationsViewCreator();

    }

    private void createSysadminTabs() {

        sysadminTabPane = new JTabbedPane();
        SysadminSideTabsListener sysTabListener = new SysadminSideTabsListener(
                sysadminTabPane, this);

        sysadminTabPane.setTabPlacement(JTabbedPane.LEFT);
//        ButtonNames bNames = new ButtonNames();
//        String[] buttonNameStrings = bNames.getButtonNames();

        for (SysAdminButton button : SysAdminButton.values()) {
            switch (button) {
                case ANNOTATIONS:
                    sysadminTabPane.addTab(button.getValue(),
                            buildAnnotationsView());
                    break;
            }
        }

//        for (int i = 0; i < buttonNameStrings.length; i++) {
//
//            switch (buttonNameStrings[i]) {
//
//            case ButtonNames.ANNOTATIONS:
//                sysadminTabPane.addTab(buttonNameStrings[i],
//                        buildAnnotationsView());
//                break;
///*
//            case ButtonNames.USERS:
//                sysadminTabPane.addTab(buttonNameStrings[i], new JPanel());
//                break;
//
//            case ButtonNames.TEST:
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

        return annotationsView.buildAnnotationsView();
    }

    private JPanel buildUsersView() {

        return null;
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
        annotationsView.addAnnotationListener(SysadminController
                .createAnnotationButtonListener());
    }

    public ActionListener createAnnotationPopupListener() {
        return SysadminController.createAnnotationPopupListener();
    }

    public AnnotationsViewCreator getAnnotationsView() {
        return annotationsView;
    }

    public SysadminController getSysController() {
        return sysController;
    }
}
