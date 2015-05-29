package controller;

import gui.ErrorDialog;
import gui.GUI;
import gui.QueryBuilderRow;
import gui.QuerySearchTab;
import gui.UploadTab;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import model.ErrorLogger;
import model.GenomizerModel;
import util.ActiveSearchPanel;
import util.AnnotationDataType;
import util.ExperimentData;
import util.RequestException;
import util.TreeTable;

public class QuerySearchTabController {
    GUI view;

    GenomizerModel model;
    private QuerySearchTab querySearchTab;

    public QuerySearchTabController(GUI view, GenomizerModel model) {
        this.view = view;
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
        querySearchTab.getResultsTable().addTreeSelectionListener(
                SelectionListener());
    }

    public TreeSelectionListener SelectionListener() {

        return new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent arg0) {
                System.out.println("test");
                TreeTable treeTable = querySearchTab.getResultsTable();
                System.out.println(treeTable.getNumberOfSelected());
                if (treeTable.getNumberOfSelected() > 0) {
                    querySearchTab.getAddToWorkspaceButton().setEnabled(true);
                    querySearchTab.getAddToUploadButton().setEnabled(true);
                } else {
                    querySearchTab.getAddToWorkspaceButton().setEnabled(false);
                    querySearchTab.getAddToUploadButton().setEnabled(false);
                }
            }
        };

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
                ExperimentData firstChosenExperiment = view.getQuerySearchTab()
                        .getSelectedData().get(0);

                UploadTab ut = view.getUploadTab();
                ut.getNewExpPanel().setSelectButtonEnabled(true);
                view.getTabbedPane().setSelectedComponent(ut);
                ut.getExperimentNameField().setText(
                        firstChosenExperiment.getName());
                ut.getExistingExpButton().doClick();
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
                        if (pubmed.isEmpty()) {
                            pubmed = "[ExpID]";
                        }
                        ArrayList<ExperimentData> searchResults;
                        try {
                            searchResults = model.search(pubmed);
                            if (searchResults != null) {
                                view.getQuerySearchTab().updateSearchResults(
                                        searchResults);
                                if (view.getSelectedIndex() == 0) {
                                    view.setStatusPanel("Search successful: "
                                            + searchResults.size()
                                            + " matches.");
                                    view.setStatusPanelColor("success");
                                }

                                // If search results are null and the active
                                // panel
                                // is search
                            } else if (view.getQuerySearchTab()
                                    .getActivePanel() == ActiveSearchPanel.SEARCH) {
                                view.setStatusPanel("No search results!");
                                view.setStatusPanelColor("fail");
                                // If search results are null and the active
                                // panel
                                // is table
                            } else if (view.getQuerySearchTab()
                                    .getActivePanel() == ActiveSearchPanel.TABLE) {
                                // Go back to the query search
                                view.getBackButton().doClick();
                                view.getQuerySearchTab().getBackButton();
                            }
                        } catch (RequestException e) {
                            new ErrorDialog("Search failed", e).showDialog();
                            ErrorLogger.log(e);
                        }
                        querySearchTab.getResultsTable()
                                .addTreeSelectionListener(SelectionListener());
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
//TODO improve the "if one selected", making a new arraylist isn't that good
    public ActionListener SearchToWorkspaceListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        if (querySearchTab.getResultsTable()
                                .getNumberOfSelected() == 1) {
                            ExperimentData selectedData = querySearchTab
                                    .getResultsTable().getSelectedExperiment();
                            view.setStatusPanel(selectedData.name
                                    + " was added to the workspace.");
                            view.setStatusPanelColor("success");
                            ArrayList<ExperimentData> exDataAL = new ArrayList<ExperimentData>();
                            exDataAL.add(selectedData);
                            view.getWorkSpaceTab().addExperimentsToTable(exDataAL);
                            view.getWorkSpaceTab().changeTab(0);
                            view.getQuerySearchTab().clearSearchSelection();
                        } else {
                            ArrayList<ExperimentData> selectedData = view
                                    .getQuerySearchTab().getSelectedData();
                            if (selectedData.size() == 1) {
                                view.setStatusPanel(selectedData.get(0).name
                                        + " was added to the workspace.");
                                view.setStatusPanelColor("success");
                                // view.setStatusPanelColorSuccess();
                            } else if (selectedData.size() > 1) {
                                view.setStatusPanel(selectedData.get(0).name
                                        + " + "
                                        + (selectedData.size() - 1)
                                        + " other experiments was added to the workspace.");
                                view.setStatusPanelColor("success");
                                // view.setStatusPanelColorSuccess();
                            }

                            view.getWorkSpaceTab().addExperimentsToTable(
                                    view.getQuerySearchTab().getSelectedData());
                            view.getWorkSpaceTab().changeTab(0);
                            view.getQuerySearchTab().clearSearchSelection();
                        }

                    };
                }.start();

            }
        };
    }

}
