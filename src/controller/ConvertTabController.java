package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import util.ExperimentData;
import util.FileData;
import util.GenomeReleaseData;
import gui.CheckListItem;
import gui.DeleteDataWindow;
import gui.GenomizerView;
import gui.UploadTab;
import gui.WorkspaceTab;
import model.ErrorLogger;
import model.GenomizerModel;

public class ConvertTabController {
    GenomizerView view;
    GenomizerModel model;
    private boolean deletedProcessFiles = false;

    public ConvertTabController(GenomizerView view, GenomizerModel model,
            JFileChooser fileChooser) {
        this.view = view;
        this.model = model;

        WorkspaceTab workspaceTab = view.getWorkSpaceTab();
        workspaceTab.addConvertFileListener(ConvertFileListener());
        fileListAddMouseListener(view.getConvertTab().getFileList());

    }


    public ActionListener ConvertFileListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        // TODO Skicka in filedata arrayen
                        ArrayList<ExperimentData> selectedData = view.getWorkSpaceTab().getSelectedData();
                        ArrayList<FileData> selectedFiles = new ArrayList<>();
                        for (ExperimentData experiment : selectedData) {
                            for (FileData file : experiment.files) {
                                if (!selectedFiles.contains(file)) {
                                    selectedFiles.add(file);
                                }
                            }
                        }
                        view.setConvertFileList(selectedFiles);
                    };
                }.start();
            }
        };
    }



  private void fileListAddMouseListener(JList fileList) {
      fileList.addMouseListener(new MouseAdapter() {

          String species = "";
          int count = 0;

          @Override
          public void mouseClicked(MouseEvent event) {
              JList list = (JList) event.getSource();

              if (deletedProcessFiles) {
                  species = "";
                  count = 0;
              }

              if (list.getModel().getSize() > 0) {
                  int index = list.locationToIndex(event.getPoint());

                  CheckListItem item = (CheckListItem) list.getModel()
                          .getElementAt(index);

                      item.setSelected(!item.isSelected());




                  deletedProcessFiles = false;
                  list.repaint(list.getCellBounds(index, index));
              }
          }
      });
  }


}
