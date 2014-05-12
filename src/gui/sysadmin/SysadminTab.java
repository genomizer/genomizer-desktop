package gui.sysadmin;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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
        ButtonNames bNames = new ButtonNames();
        String[] buttonNameStrings = bNames.getButtonNames();

        for (int i = 0; i < buttonNameStrings.length; i++) {

            switch (buttonNameStrings[i]) {

            case ButtonNames.ANNOTATIONS:
                sysadminTabPane.addTab(buttonNameStrings[i],
                        buildAnnotationsView());
                break;

            case ButtonNames.USERS:
                sysadminTabPane.addTab(buttonNameStrings[i], new JPanel());
                break;

            case ButtonNames.TEST:
                sysadminTabPane.addTab(buttonNameStrings[i], new JPanel());
                break;

            }

        }
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
