package controller;

import gui.CheckListItem;
import gui.DownloadWindow;
import gui.GenomizerView;
import gui.UploadTab;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import requests.AddExperimentRequest;
import requests.RequestFactory;
import model.GenomizerModel;
import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.DeleteAnnoationData;
import util.ExperimentData;
import util.FileData;

public class Controller {

	private GenomizerView view;
	private GenomizerModel model;
	private final JFileChooser fileChooser = new JFileChooser();

	public Controller(GenomizerView view, GenomizerModel model) {
		this.view = view;
		this.model = model;
		view.addLoginListener(new LoginListener());
		view.addLogoutListener(new LogoutListener());
		view.addSearchListener(new QuerySearchListener());
		view.addConvertFileListener(new ConvertFileListener());
		view.addQuerySearchListener(new QuerySearchListener());
		view.addRawToProfileDataListener(new RawToProfileDataListener());
		view.addRawToRegionDataListener(new RawToRegionDataListener());
		view.addScheduleFileListener(new ScheduleFileListener());
		view.addDownloadFileListener(new DownloadWindowListener());
		// view.addAddAnnotationListener(new AddNewAnnotationListener());
		view.addAddPopupListener(new AddPopupListener());
		view.addAddToExistingExpButtonListener(new AddToExistingExpButtonListener());
		view.addSelectFilesToUploadButtonListener(new SelectFilesToUploadButtonListener());
		view.addUploadToExperimentButtonListener(new UploadToExperimentButtonListener());
		view.setAnnotationTableData(model.getAnnotations());
		view.addUpdateSearchAnnotationsListener(new updateSearchAnnotationsListener());
		view.addProcessFileListener(new ProcessFileListener());
		view.addSearchToWorkspaceListener(new SearchToWorkspaceListener());
		view.addDeleteAnnotationListener(new DeleteAnnotationListener());
		view.addNewExpButtonListener(new NewExpButtonListener());
		view.addSelectButtonListener(new SelectFilesToNewExpListener());
		view.addUploadButtonListener(new UploadNewExpListener());
        view.addAnalyzeSelectedListener(new AnalyzeSelectedListener());
		fileListAddMouseListener(view.getfileList());
	}



	class DeleteAnnotationListener implements ActionListener, Runnable {
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(this).start();
		}

		@Override
		public void run() {
			AnnotationDataType annotationDataType = null;
			try {
				annotationDataType = view
						.getSelectedAnnoationAtAnnotationTable();
				if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to delete the"
								+ annotationDataType.name) == JOptionPane.YES_OPTION) {
					if (model.deleteAnnotation(new DeleteAnnoationData(
							annotationDataType))) {
						JOptionPane.showMessageDialog(null,
								annotationDataType.name + " has been remove!");
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								view.setAnnotationTableData(model
										.getAnnotations());
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

		}
	}

	class AddPopupListener implements ActionListener, Runnable {
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(this).start();
		}

		@Override
		public void run() {
			view.annotationPopup();
			view.addAddAnnotationListener(new AddNewAnnotationListener());
		}
	}

	class AddNewAnnotationListener implements ActionListener, Runnable {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(this).start();
		}

		@Override
		public void run() {
			String name = view.getNewAnnotationName();
			String[] categories = view.getNewAnnotionCategories();
			boolean forced = view.getNewAnnotationForcedValue();
			try {
				if (model.addNewAnnotation(name, categories, forced)) {
					view.closePopup(); // Is this needed here?
				} else {
					JOptionPane.showMessageDialog(null,
							"Could not create new annotation, check server?");
				}
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			// Update annotationsearch?
		}
	}

	class ConvertFileListener implements ActionListener, Runnable {
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(this).start();
		}

		@Override
		public void run() {

			System.out.println("CONVERT");
			System.out.println(view.getAllMarkedFiles());

		}
	}

	class RawToProfileDataListener implements ActionListener, Runnable {
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(this).start();
		}

		@Override
		public void run() {

			view.setBowtieParameters();
			ArrayList<FileData> allMarked = view.getAllMarkedFileData();
			int markedSize = allMarked.size();
			String message = null;
			Boolean isConverted = false;
			
			if (!allMarked.isEmpty()) {

				for (int i = 0; i < markedSize; i++) {

					String fileName = allMarked.get(i).filename;
					String fileID = allMarked.get(i).id;
					String author = view.getUsername();
					String parameters[] = new String[4];

					parameters[0] = view.getBowtieParameters()[0];
					parameters[1] = view.getBowtieParameters()[1];
					parameters[2] = view.getBowtieParameters()[2];
					parameters[3] = view.getBowtieParameters()[3];

					String expid = allMarked.get(i).expId;
					String genomeRelease = allMarked.get(i).grVersion;
					String metadata = allMarked.get(i).metaData;

					System.out.println("RAW TO PROFILE");
					System.out.println("File: " + fileName);
					System.out.println("File ID: " + fileID);
					System.out.println("Author: " + view.getUsername());
					System.out.println("Expid: " + expid);
					System.out.println("Genome Release: " + genomeRelease);
					System.out.println("Metadata: " + metadata);

					System.out.println("Parameter 1: " + parameters[0]);
					System.out.println("Parameter 2: " + parameters[1]);
					System.out.println("Parameter 3: " + parameters[2]);
					System.out.println("Parameter 4: " + parameters[3]);

					isConverted = model.rawToProfile(fileName, fileID, expid, "rawtoprofile", parameters,
							metadata, genomeRelease, author);

					if (isConverted.equals(true)) {
						message = "The server has converted: " + fileName + " with file id: " + fileID + " from " + expid + "\n";
						view.printToConvertText(message,"green");

					} else {
						message = "WARNING - The server couldn't convert: "
								+ fileName + " with file id: " + fileID + " from "+ expid +"\n";
						view.printToConvertText(message,"red");
					}
				}
			}
		}
	}

	class RawToRegionDataListener implements ActionListener, Runnable {
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(this).start();
		}

		@Override
		public void run() {

			System.out.println("RAW TO REGION");
			System.out.println(view.getAllMarkedFiles());

		}
	}

	class ScheduleFileListener implements ActionListener, Runnable {
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(this).start();
		}

		@Override
		public void run() {

			System.out.println("SCHEDULEING FILE");
			System.out.println(view.getAllMarkedFiles());

		}
	}

	class ProcessFileListener implements ActionListener, Runnable {
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(this).start();
		}
		@Override
		public void run() {
			System.out.println("Process");
			// TODO Skicka in filedata arrayen
			ArrayList<ExperimentData> selectedData = view.getSelectedDataInWorkspace();
			ArrayList<FileData> selectedFiles = new ArrayList<FileData>();
			for(ExperimentData experiment : selectedData) {
				for(FileData file : experiment.files) {
					if(!selectedFiles.contains(file)) {
						selectedFiles.add(file);
					}
				}
			}
			view.setProccessFileList(selectedFiles);
		}
	}

	class LoginListener implements ActionListener, Runnable {
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(this).start();
		}

		@Override
		public void run() {
			model.setIp(view.getIp());
			String username = view.getUsername();
			String pwd = view.getPassword();
			if (model.loginUser(username, pwd)) {
				view.updateLoginAccepted(username, pwd, "Yuri Gagarin");
			} else {
				view.updateLoginAccepted(username, pwd, "Yuri Gagarin");
			}
		}
	}

	class QuerySearchListener implements ActionListener, Runnable {
		public void actionPerformed(ActionEvent e) {
			new Thread(this).start();
		}

		@Override
		public void run() {
			String pubmed = view.getQuerySearchString();
			ArrayList<ExperimentData> searchResults = model.search(pubmed);
			if (searchResults != null) {
				view.updateQuerySearchResults(searchResults);
			} else {
//				searchResults = new ArrayList<ExperimentData>(Arrays.asList(ExperimentData.getExample()));
//				 view.updateQuerySearchResults(searchResults);
				JOptionPane.showMessageDialog(null, "No search results!",
						"Search Warning", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	class LogoutListener implements ActionListener, Runnable {
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(this).start();
		}

		@Override
		public void run() {
			if (model.logoutUser()) {
				view.updateLogout();
			} else {
				view.updateLogout();
			}

		}
	}

	class UploadListener implements ActionListener, Runnable {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			new Thread(this).start();
		}

		@Override
		public void run() {
//			if (model.uploadFile()) {
//				 update view?
//			}

		}
	}

	class DownloadWindowListener implements ActionListener, Runnable {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			new Thread(this).start();
		}

		@Override
		public void run() {
			// Skicka med arraylist<FileData> för de filer som ska nerladdas
			ArrayList<ExperimentData> selectedData = view.getSelectedDataInWorkspace();
			ArrayList<FileData> selectedFiles = new ArrayList<FileData>();
			for(ExperimentData experiment : selectedData) {
				for(FileData file : experiment.files) {
					if(!selectedFiles.contains(file)) {
						selectedFiles.add(file);
					}
				}
			}

			DownloadWindow downloadWindow = new DownloadWindow(selectedFiles);
			view.setDownloadWindow(downloadWindow);
			downloadWindow.addDownloadFileListener(new DownloadFileListener());
		}
	}

	class DownloadFileListener implements ActionListener, Runnable {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			new Thread(this).start();
		}

		@Override
		public void run() {

			DownloadWindow downloadWindow = view.getDownloadWindow();
			ArrayList<FileData> fileData = downloadWindow.getFiles();
			/*
			 * >>>>>>> branch 'dev' of
			 * https://github.com/genomizer/genomizer-desktop.git FileDialog
			 * fileDialog = new FileDialog((java.awt.Frame) null,
			 * "Choose a directory", FileDialog.SAVE);
			 * fileDialog.setVisible(true); String directoryName =
			 * fileDialog.getDirectory(); System.out.println("You chose " +
			 * directoryName);
			 *
			 * if (fileData == null) {
			 * System.err.println("No directory selected"); return; }
			 */
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int ret = fileChooser.showOpenDialog(new JPanel());
			String directoryName;
			if (ret == JFileChooser.APPROVE_OPTION) {
				directoryName = fileChooser.getSelectedFile().getAbsolutePath();
			} else {
				return;
			}

			for (FileData data : fileData) {
				System.out.println(data.url);
				File experimentDir = new File(directoryName + "/" + data.expId);
				if(experimentDir.mkdir()) {
					System.out.println("Created dir: " + data.expId);
				}
				File typeDir = new File(directoryName + "/" + data.expId + "/"
						+ data.type);
				if(typeDir.mkdir()) {
					System.out.println("Created dir: " + data.type);
				}
				model.downloadFile(data.url, data.id, directoryName + "/"
						+ data.expId + "/" + data.type + "/" + data.filename);
			}
		}
	}

	class AddToExistingExpButtonListener implements ActionListener, Runnable {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			new Thread(this).start();
		}

		@Override
		public void run() {
			UploadTab uploadTab = view.getUploadTab();
			uploadTab.killContentsOfUploadPanel();
			AnnotationDataType[] annotations = model.getAnnotations();
			uploadTab.addExistingExpPanel(annotations);
            uploadTab.repaint();
            uploadTab.revalidate();
		}
	}

	class SelectFilesToUploadButtonListener implements ActionListener, Runnable {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			new Thread(this).start();
		}

		@Override
		public void run() {
            FileDialog fileDialog = new java.awt.FileDialog(
                    (java.awt.Frame) null);
            fileDialog.setMultipleMode(true);
            fileDialog.setVisible(true);
            File[] files = fileDialog.getFiles();
            String[] fileNames = new String[files.length];
            for(int i = 0; i < files.length ; i++) {
                fileNames[i] = files[i].getName();
            }
            view.selectFilesToNewExp(fileNames, files);
		}
	}

	class UploadToExperimentButtonListener implements ActionListener, Runnable {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			new Thread(this).start();
		}

		@Override
		public void run() {
			// Get list of files to upload

			// Upload them.

			// Move to where the check if upload is complete will be.
			JOptionPane.showMessageDialog(null, "Upload complete.");
		}
	}

	class updateSearchAnnotationsListener implements ActionListener, Runnable {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			new Thread(this).start();
		}

		@Override
		public void run() {
			AnnotationDataType[] annotations = model.getAnnotations();
			if (annotations != null) {
				view.setSearchAnnotationTypes(annotations);
			}
		}
	}

	class SearchToWorkspaceListener implements ActionListener, Runnable {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			new Thread(this).start();
		}

		@Override
		public void run() {
			view.addToWorkspace(view.getSelectedDataInSearch());
		}

	}

	class NewExpButtonListener implements ActionListener, Runnable {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			new Thread(this).start();
		}

		@Override
		public void run() {
			AnnotationDataType[] annotations = model.getAnnotations();
			view.createNewExp(annotations);
		}
	}

	class SelectFilesToNewExpListener implements ActionListener, Runnable  {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(this).start();
		}

		@Override
		public void run() {
			FileDialog fileDialog = new java.awt.FileDialog(
					(java.awt.Frame) null);
			fileDialog.setMultipleMode(true);
			fileDialog.setVisible(true);
			File[] files = fileDialog.getFiles();
			String[] fileNames = new String[files.length];
			for(int i = 0; i < files.length ; i++) {
				fileNames[i] = files[i].getName();
			}
			view.selectFilesToNewExp(fileNames, files);
		}
	}
	class UploadNewExpListener implements ActionListener, Runnable  {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(this).start();
		}

		@Override
		public void run() {
		    	String expName = view.getNewExpName();
			AnnotationDataValue[] annotations = view.getUploadAnnotations();
			File[] files = view.getFilesToUpload();
			HashMap<String, String> types = view.getFilesToUploadTypes();
			//Should be genome release from uploadTab
			String release = "releaseNr";
			//Test purpose
			for(AnnotationDataValue a : annotations) {
			    System.out.println(a.getName() + " " + a.getValue());
			}
			String ID = model.addNewExperiment(expName, view.getUsername(), annotations);
			System.out.println(ID);
			if(!ID.isEmpty()) {
			    for(File f : files) {
				model.uploadFile(ID, f, types.get(f.getName()), view.getUsername(), false, release);
			    }
			    
			}
		}
	}

    class AnalyzeSelectedListener implements ActionListener, Runnable  {

        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            System.out.println("ANALYZE");
        }
    }

	private void fileListAddMouseListener(JList fileList) {
		fileList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				JList list = (JList) event.getSource();

				if(list.getModel().getSize() > 0){
					int index = list.locationToIndex(event.getPoint());
					CheckListItem item = (CheckListItem) list.getModel()
							.getElementAt(index);

					item.setSelected(!item.isSelected());

					list.repaint(list.getCellBounds(index, index));
				}
			}
		});
	}
}
