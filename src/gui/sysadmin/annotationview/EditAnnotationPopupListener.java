package gui.sysadmin.annotationview;

import gui.sysadmin.SysadminController;
import gui.sysadmin.SysadminTab;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditAnnotationPopupListener implements ActionListener{

    private SysadminTab sysTab;
    private SysadminController sysController;

    public EditAnnotationPopupListener(SysadminTab sysTab) {
        this.sysTab = sysTab;
        this.sysController = sysTab.getSysController();
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Pressed a button in the Edit Annotation view");

	}

}
