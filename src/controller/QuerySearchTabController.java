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
import gui.GUI;
import gui.UploadTab;
import gui.QueryBuilderRow;
import gui.QuerySearchTab;
import gui.sysadmin.annotationview.AnnotationButtonsListener;

public class QuerySearchTabController {
    GUI view;

    GenomizerModel model;
    private QuerySearchTab querySearchTab;

    public QuerySearchTabController(GUI view, GenomizerModel model) {
        this.view = (GUI) view;
        this.querySearchTab = view.getQuerySearchTab();
        this.model = model;
        // querySearchTab.addDownloadButtonListener(listener)
        querySearchTab.addSearchButtonListener(SearchButtonListener());
        // view.addQuerySearchListener( QuerySearchListener());
        querySearchTab
                .addUpdateAnnotationsListener(updateAnnotationsListener());
        // view.addUpdateSearchAnnotationsListener(
        // updateSearchAnnotationsListener());
        querySearchTab
                .addAddToWorkspaceButtonListener(SearchToWorkspaceListener());
        // view.addSearchToWorkspaceListener( SearchToWorkspaceListener());
        querySearchTab.addUploadToListener(SearchUploadToListener());
        // view.addUploadToListenerSearchTab( SearchUploadToListener());

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
                            .getQuerySearchTab().getSelectedData().get(0);

                    UploadTab ut = view.getUploadTab();
                    ut.getNewExpPanel().setSelectButtonEnabled(true);
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
                        String pubmed = view.getQuerySearchTab()
                                .getSearchString();
                        if(pubmed.isEmpty()) {
                            pubmed = "[ExpID]";
                        }
                        ArrayList<ExperimentData> searchResults = model
                                .search(pubmed);
                        if (searchResults != null) {
                            view.getQuerySearchTab().updateSearchResults(
                                    searchResults);
                            if(view.getSelectedIndex() == 0){
                                view.setStatusPanel("Search successful: " + searchResults.size() + " matches.");
                                view.setStatusPanelColorSuccess();
                            }

                            // If search results are null and the active panel
                            // is search
                        } else if (view.getQuerySearchTab().getActivePanel() == ActiveSearchPanel.SEARCH) {
                            view.setStatusPanel("No search results!");
                            view.setStatusPanelColorFail();

                            // If search results are null and the active panel
                            // is table
                        } else if (view.getQuerySearchTab().getActivePanel() == ActiveSearchPanel.TABLE) {
                            // Go back to the query search
                            view.getBackButton().doClick();
                            view.getQuerySearchTab().getBackButton();

                        }
                    };
                }.start();
            }
        };
    }

    public ActionListener updateAnnotationsListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        AnnotationDataType[] annotations = model
                                .getAnnotations();
                        if (annotations != null && annotations.length > 0) {
                            view.getQuerySearchTab().setAnnotationTypes(
                                    annotations);
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
                                .getQuerySearchTab().getSelectedData();
                        if (selectedData != null && selectedData.size() > 0) {
                            if(selectedData.size() == 1){
                                view.setStatusPanel(selectedData.get(0).name
                                        + " was added to the workspace.");
                                view.setStatusPanelColorSuccess();
                            } else if (selectedData.size() > 1) {
                                view.setStatusPanel(selectedData.get(0).name +" + "+ (selectedData.size()-1)
                                        + " other experiments was added to the workspace.");
                                view.setStatusPanelColorSuccess();
                            }

                            view.getWorkSpaceTab().addExperimentsToTable(
                                    view.getQuerySearchTab().getSelectedData());
                            view.getWorkSpaceTab().changeTab(0);
                        } else {
                            view.setStatusPanel( "No data selected!");
                            view.setStatusPanelColorFail();
                        }
                        view.getQuerySearchTab().clearSearchSelection();
                    };
                }.start();

            }
        };
    }

}
