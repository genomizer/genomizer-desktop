package controller;

import gui.DownloadWindow;
import gui.GenomizerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import util.ExperimentData;
import model.GenomizerModel;

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
		view.addUploadFileListener(new UploadListener());
		view.addBrowseListener(new BrowseListener());
		view.addConvertFileListener(new ConvertFileListener());
		view.addQuerySearchListener(new QuerySearchListener());
		view.addRawToProfileDataListener(new RawToProfileDataListener());
		view.addRawToRegionDataListener(new RawToRegionDataListener());
		view.addScheduleFileListener(new ScheduleFileListener());
		view.addDownloadFileListener(new DownloadWindowListener());
		// view.addAddAnnotationListener(new AddNewAnnotationListener());
		view.addAddPopupListener(new AddPopupListener());

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
			if (name == null) {
				// TODO: fix popupwarning?
				System.err
						.println("You must specify a name for a new annotation!");
			} else {
				model.addNewAnnotation(name, categories, forced);
				view.closePopup(); // Is this needed here?
				// Update annoationsearch?
			}
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

	class BrowseListener implements ActionListener, Runnable {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			new Thread(this).start();
		}

		@Override
		public void run() {
			int returnVal = fileChooser.showOpenDialog(new JPanel());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String filePath = fileChooser.getSelectedFile()
						.getAbsolutePath();

				view.updateFileChosen(filePath);
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
			 * TODO När vi har faktiska filer som ska nedladdas: använd den
			 * andra konstruktorn new DownloadWindow(ArrayList<String>) istället
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
			model.downloadFile();
		}
	}
}
