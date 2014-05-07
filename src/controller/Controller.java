package controller;

import gui.DownloadWindow;
import gui.GenomizerView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gui.UploadTab;
import model.GenomizerModel;
import util.AnnotationDataType;
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
        view.addSearchResultsDownloadListener(new DownloadSearchListener());
		// view.addAddAnnotationListener(new AddNewAnnotationListener());
		view.addAddPopupListener(new AddPopupListener());
        view.addAddToExistingExpButtonListener(new AddToExistingExpButtonListener());
        view.addSelectFilesToUploadButtonListener(new SelectFilesToUploadButtonListener());
        view.addUploadToExperimentButtonListener(new UploadToExperimentButtonListener());
        view.setAnnotationTableData(model.getAnnotations());
        view.addUpdateSearchAnnotationsListener(new updateSearchAnnotationsListener());
	}
    class DownloadSearchListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            FileData[] fileData = view.getSelectedFilesInSearch();

            FileDialog fileDialog = new FileDialog((java.awt.Frame) null, "Choose a file", FileDialog.LOAD);
            fileDialog.setVisible(true);
            String filename = fileDialog.getFile();
            String path = null;
            if (filename == null) {
                System.out.println("You cancelled the choice");
            } else {
                System.out.println("You chose " + filename);
                path = fileChooser.getSelectedFile().getAbsolutePath();
            }
            
            System.out.println(path);
            if(fileData == null) {
                System.err.println("No files selected");
                return;
            }
            for(FileData data: fileData) {

                model.downloadFile(data.id, path + "/" + data.name);
            }

        }
    }

    class DeleteAnnotationListener implements ActionListener, Runnable {
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(this).start();
		}

		@Override
		public void run() {
			AnnotationDataType annotationDataType = null;
			//view.getSelectedAnnoationAtAnnotationTable();
			model.deleteAnnotation(new DeleteAnnoationData(annotationDataType));
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

			System.out.println("RAW TO PROFILE");
			System.out.println(view.getAllMarkedFiles());
			System.out.println("Has converted RAW TO PROFILE: "
					+ model.rawToProfile(view.getAllMarkedFiles()));

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
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(this).start();
		}

		@Override
		public void run() {
			String pubmed = view.getQuerySearchString();
			ExperimentData[] searchResults = model.search(pubmed);
			if (searchResults != null) {
				view.updateQuerySearchResults(searchResults);
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

			if (model.uploadFile()) {
				// update view?
			}

		}
	}

	class DownloadWindowListener implements ActionListener, Runnable {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			new Thread(this).start();
		}

		@Override
		public void run() {
			/*
			 * TODO N�r vi har faktiska filer som ska nedladdas: anv�nd den
			 * andra konstruktorn new DownloadWindow(ArrayList<String>)
			 * ist�llet
			 */
			DownloadWindow downloadWindow = new DownloadWindow();
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
			model.downloadFile("<fileid>", "filename");
		}
	}

	class AddToExistingExpButtonListener implements ActionListener, Runnable {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			new Thread(this).start();
		}

		@Override
		public void run() {
			view.getUploadTab().addExistingExpPanel();
		}
	}

	class SelectFilesToUploadButtonListener implements ActionListener, Runnable {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			new Thread(this).start();
		}

		@Override
		public void run() {
			new java.awt.FileDialog((java.awt.Frame) null).setVisible(true);

			// Old fileChooser: fileChooser.showOpenDialog(new JPanel());
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
			System.out.println("updateSearchAnnotations");
			AnnotationDataType[] annotations = model.getAnnotations();
			if (annotations != null) {
				view.setSearchAnnotationTypes(annotations);
			}
		}
	}
}
