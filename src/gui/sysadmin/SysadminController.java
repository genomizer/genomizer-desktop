package gui.sysadmin;

import gui.sysadmin.annotationview.AnnotationButtonsListener;
import gui.sysadmin.annotationview.AnnotationPopupListener;
import gui.sysadmin.annotationview.AnnotationTableModel;
import gui.sysadmin.annotationview.SysadminAnnotationPopup;
import model.GenomizerModel;
import util.AnnotationDataType;
import util.DeleteAnnoationData;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class SysadminController extends Observable {

    private SysadminTab    sysTab;
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
        if (sysTab.getAnnotationTable().getSelectedRow() != -1) {
            int row = sysTab.getAnnotationTable().getSelectedRow();
            row = sysTab.getAnnotationTable().convertRowIndexToModel(row);
            int col = 3;
            AnnotationDataType annotation = (AnnotationDataType) sysTab
                    .getAnnotationTable().getModel().getValueAt(row, col);

            try {
                if (JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete the "
                                + annotation.name + " annotation?"
                ) == JOptionPane.YES_OPTION) {
                    if (model.deleteAnnotation(new DeleteAnnoationData(
                            annotation))) {
                        JOptionPane.showMessageDialog(null, annotation.name
                                + " has been remove!");
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
}
