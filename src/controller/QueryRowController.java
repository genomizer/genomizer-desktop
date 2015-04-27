package controller;

import gui.QuerySearchTab;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueryRowController {
    private QuerySearchTab querySearchTab;
    public QueryRowController(QuerySearchTab querySearchTab){
        this.querySearchTab = querySearchTab;
        
    }

    public ActionListener createPlusButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Add a row to the parent when button is clicked */
                querySearchTab.addRow();
            }
        };
    }
}
