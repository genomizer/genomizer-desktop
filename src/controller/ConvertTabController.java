package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JList;
import util.ExperimentData;
import util.FileData;
import gui.CheckListItem;
import gui.ConvertTab;
import gui.GUI;
import model.GenomizerModel;

public class ConvertTabController {
    GUI view;
    GenomizerModel model;
    private boolean deletedProcessFiles = false;

    /**
     * Constructor..
     * @param view
     * @param model
     * @param fileChooser
     */
    public ConvertTabController(GUI view, GenomizerModel model,
            JFileChooser fileChooser) {
        this.view = view;
        this.model = model;

        ConvertTab ct = view.getConvertTab();
        ct.convertSelectedButtonListener(ConvertSelectedFileListener());
        ct.deleteSelectedButtonListener(DeleteSelectedFileListener());
        fileListAddMouseListener(view.getConvertTab().getFileList());


    }


    /**
     *
     * @return
     */
    public ActionListener ConvertSelectedFileListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        // TODO Skicka in filedata arrayen

                        ArrayList<FileData> selectedFiles = view.getConvertTab().getAllMarkedFiles();


                        Iterator<FileData> it = selectedFiles.iterator();
                        while(it.hasNext()){
                            FileData data = it.next();
                            System.out.println(data);
                        }

                       // view.setConvertFileList(selectedFiles);
                    };
                }.start();
            }
        };
    }

    public ActionListener DeleteSelectedFileListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        ArrayList<FileData> markedFiles = view.getConvertTab().getAllMarkedFiles();

                        ArrayList<ExperimentData> exData = view.getConvertTab().getFileInfo();

                        if (exData != null && markedFiles != null) {

                            for (ExperimentData data : exData) {
                                data.files.removeAll(markedFiles);
                            }
                            view.getConvertTab().setFileInfo(exData);
                            deletedProcessFiles = true;
                        }

                    };
                }.start();
            }
        };
    }



  private void fileListAddMouseListener(JList fileList) {
      fileList.addMouseListener(new MouseAdapter() {

          String species = "";
          int count = 0;
          String fileType1 = "";
          String fileType2 = "";
          ArrayList<String> fileTypeList = null;

          @Override
          public void mouseClicked(MouseEvent event) {
              JList list = (JList) event.getSource();

              if (deletedProcessFiles) {
                  species = "";
                  count = 0;
                  view.getConvertTab().resetCurrentSelectedFileType();
              }

              if (list.getModel().getSize() > 0) {





                  int index = list.locationToIndex(event.getPoint());

                  CheckListItem item = (CheckListItem) list.getModel()
                          .getElementAt(index);

          //        System.out.println("listans storlek = " + list.getModel().getSize());

                  String fileName = item.getfile().getName().toUpperCase();
                  fileType1 = fileName.substring(fileName.lastIndexOf(".") + 1);


                  if (count == 0) {
                      fileType2 = "";
                      view.getConvertTab().resetCurrentSelectedFileType();
                  }
                  if (fileType2.equals("") && count == 0) {
                      fileName = item.getfile().getName().toUpperCase();
                      fileType2 = fileName.substring(fileName.lastIndexOf(".") + 1);
                  }

                  // OBS vill använda och sätta alla checkboxes som inte går att konvertera ifrån till not enabled!
                  fileTypeList = view.getConvertTab().getPossibleConvertFromFileTypes();

                  if (fileType1.equals(fileType2)) {

                      view.getConvertTab().setCurrentSelectedFileType(fileType1);

                      item.setSelected(!item.isSelected());


                      if (item.isSelected()) {
                          count++;
                      } else {
                          count--;
                      }
                  }
                  deletedProcessFiles = false;
                  list.repaint(list.getCellBounds(index, index));
              }
          }
      });
  }


}
