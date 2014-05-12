package gui.sysadmin;

import model.GenomizerModel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class SysadminController extends Observable {

	private SysadminTab sysTab;
	private GenomizerModel model;

	public SysadminController(Observer observer) {

		this.addObserver(observer);

	}

	public SysadminController(GenomizerModel model) {
		this.model = model;
	}

	public ActionListener createAnnotationButtonListener() {
		return new AnnotationButtonsListener(sysTab);
	}

	public ActionListener createAnnotationPopupListener() {
		return new AnnotationPopupListener(sysTab);
	}

	/* You need me */
	public void setSysadminPanel(SysadminTab sysTab) {

		this.sysTab = sysTab;

	}

	public void sendNewAnnotation() {

		SysadminAnnotationPopup popup = sysTab.getAnnotationsView().getPop();
		try {
			model.addNewAnnotation(popup.getNewAnnotationName(),
					popup.getNewAnnotationCategories(),
					popup.getNewAnnotationForcedValue());
			updateAnnotationTable();
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public util.AnnotationDataType[] getAnnotations() {
		return model.getAnnotations();
	}

	public void updateAnnotationTable() {
		AnnotationTableModel tableModel = (AnnotationTableModel) sysTab
				.getAnnotationsView().getTableModel();
		tableModel.setAnnotations(getAnnotations());
	}

	public void deleteAnnotation() {

	}
}
