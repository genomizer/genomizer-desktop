/*
 * package gui.sysadmin;
 * 
 * import java.awt.event.ActionEvent; import java.awt.event.ActionListener;
 * 
 * import javax.swing.JOptionPane; import javax.swing.SwingUtilities;
 * 
 * import util.AnnotationDataType; import util.DeleteAnnoationData;
 * 
 * class DeleteAnnotationListener implements ActionListener, Runnable {
 * 
 * @Override public void actionPerformed(ActionEvent e) { new
 * Thread(this).start(); }
 * 
 * @Override public void run() { AnnotationDataType annotationDataType = null;
 * try { annotationDataType = view.getSelectedAnnoationAtAnnotationTable(); if
 * (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the" +
 * annotationDataType.name) == JOptionPane.YES_OPTION) { if
 * (model.deleteAnnotation(new DeleteAnnoationData( annotationDataType))) {
 * JOptionPane.showMessageDialog(null, annotationDataType.name +
 * " has been remove!"); SwingUtilities.invokeLater(new Runnable() {
 * 
 * @Override public void run() {
 * view.setAnnotationTableData(model.getAnnotations()); } }); } else {
 * JOptionPane.showMessageDialog(null, "Could not remove annotation"); } } }
 * catch (IllegalArgumentException e) { JOptionPane.showMessageDialog(null,
 * e.getMessage()); }
 * 
 * } }
 */
