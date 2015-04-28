package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import model.ErrorLogger;
import model.GenomizerModel;
import util.ActiveSearchPanel;
import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.GenomeReleaseData;
import gui.GenomizerView;
import gui.UploadTab;
import gui.QueryBuilderRow;
import gui.QuerySearchTab;
import gui.sysadmin.annotationview.AnnotationButtonsListener;

public class QuerySearchTabController {
    GenomizerView view;
    GenomizerModel model;
    private QuerySearchTab querySearchTab;
    
    public QuerySearchTabController(GenomizerView view, GenomizerModel model) {
        this.view = view;
        this.querySearchTab = view.getQuerySearchTab();
        this.model = model;
        // querySearchTab.addDownloadButtonListener(listener)
        querySearchTab.addSearchButtonListener(SearchButtonListener());
        // view.addQuerySearchListener( QuerySearchListener());
        view.addUpdateSearchAnnotationsListener(updateSearchAnnotationsListener());
        view.addSearchToWorkspaceListener(SearchToWorkspaceListener());
        view.addUploadToListenerSearchTab(SearchUploadToListener());
        
    }
    
    public ActionListener createClearButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                querySearchTab.clearSearchFields();
            }
        };
    }
    
    public ActionListener createManualEditButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                querySearchTab.getSearchArea().setEditable(true);
                
                for (QueryBuilderRow row : querySearchTab.getRowList()) {
                    row.setEnabled(false);
                }
            }
        };
    }
    
    // En uploadlistener som körs när upload knappen trycks i search-taben
    public ActionListener SearchUploadToListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ExperimentData firstChosenExperiment = view
                            .getSelectedDataInSearch().get(0);
                    UploadTab ut = view.getUploadTab();
                    view.getTabbedPane().setSelectedComponent(ut);
                    ut.getExperimentNameField().setText(
                            firstChosenExperiment.getName());
                    ut.getExistingExpButton().doClick();
                } catch (IndexOutOfBoundsException ee) {
                    ErrorLogger.log(ee);
                    JOptionPane.showMessageDialog(null,
                            "No experiment was selected.");
                }
            }
        };
    }
    
    public ActionListener createQueryBuilderButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                querySearchTab.getSearchArea().setEditable(false);
                
                for (QueryBuilderRow row : querySearchTab.getRowList()) {
                    row.setEnabled(true);
                }
            }
        };
    }
    
    public ActionListener createBackButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                querySearchTab.showSearchView();
            }
        };
    }
    
    public ActionListener SearchButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        String pubmed = view.getQuerySearchString();
                        ArrayList<ExperimentData> searchResults = model
                                .search(pubmed);
                        if (searchResults != null) {
                            view.updateQuerySearchResults(searchResults);
                            
                            // If search results are null and the active panel
                            // is search
                        } else if (view.getActiveSearchPanel() == ActiveSearchPanel.SEARCH) {
                            JOptionPane.showMessageDialog(null,
                                    "No search results!", "Search Warning",
                                    JOptionPane.WARNING_MESSAGE);
                            
                            // If search results are null and the active panel
                            // is table
                        } else if (view.getActiveSearchPanel() == ActiveSearchPanel.TABLE) {
                            // Go back to the query search
                            view.getBackButton().doClick();
                        }
                    };
                }.start();
            }
        };
    }
    
    public ActionListener updateSearchAnnotationsListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        AnnotationDataType[] annotations = model
                                .getAnnotations();
                        if (annotations != null && annotations.length > 0) {
                            view.setSearchAnnotationTypes(annotations);
                        }
                        
                    };
                }.start();
            }
        };
    }
    
    public ActionListener SearchToWorkspaceListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        ArrayList<ExperimentData> selectedData = view
                                .getSelectedDataInSearch();
                        view.setStatusPanel(selectedData.get(0).name
                                + " was added to the workspace");
                        if (selectedData != null && selectedData.size() > 0) {
                            view.addToWorkspace(view.getSelectedDataInSearch());
                            view.changeTabInWorkspace(0);
                        }
                        view.clearSearchSelection();
                    };
                }.start();
            }
        };
    }
    
}
