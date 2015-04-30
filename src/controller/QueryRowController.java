package controller;

import gui.QueryBuilderRow;
import gui.QuerySearchTab;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import util.AnnotationDataType;

public class QueryRowController {
    private QuerySearchTab parent;
    
    public QueryRowController(QuerySearchTab querySearchTab) {
        this.parent = querySearchTab;
        
    }
    
    public ActionListener createPlusButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Add a row to the parent when button is clicked */
                parent.addRow();
            }
        };
    }
    
    public ActionListener createMinusButtonListener(final QueryBuilderRow row) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Remove the row and update the search area in the parent */
                parent.removeRow(row);
                parent.updateSearchArea();
            }
        };
    }
    
    public DocumentListener createDocumentListener() {
        return new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                parent.updateSearchArea();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                parent.updateSearchArea();
            }
            
            @Override
            public void insertUpdate(DocumentEvent e) {
                parent.updateSearchArea();
            }
        };
    }
}
