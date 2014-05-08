package controller;

import gui.DownloadWindow;
import gui.GenomizerView;
import gui.UploadTab;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
	view.addProcessFileListener(new ProcessFileListener());
	view.addSearchToWorkspaceListener(new SearchToWorkspaceListener());
	view.addDeleteAnnotationListener(new DeleteAnnotationListener());
	view.addNewExpButtonListener(new NewExpButtonListener());
	view.addSelectButtonListener(new SelectFilesToUploadButtonListener());
    }

    class DownloadSearchListener implements ActionListener, Runnable {
	@Override
	public void actionPerformed(ActionEvent e) {
	    new Thread(this).start();
	}

	@Override
	public void run() {

        //Skicka med arraylist<FileData> för de filer som ska nerladdas
        ArrayList<FileData> selectedFiles = view.getQuerySearchTabSelectedFiles();
        ArrayList<ExperimentData> experimentData = view.getQuerySearchTabSelectedExperiments();
        ExperimentData currentExperiment;

        for(int i=0; i<experimentData.size(); i++) {
            currentExperiment = experimentData.get(i);
            for(int j=0; j<currentExperiment.files.length; j++) {
                selectedFiles.add(currentExperiment.files[j]);
            }
        }

        DownloadWindow downloadWindow = new DownloadWindow(selectedFiles);
        view.setDownloadWindow(downloadWindow);
        downloadWindow.addDownloadFileListener(new DownloadFileListener());
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

	    System.out.println("RAW TO PROFILE");

	    ArrayList<FileData> allMarked = view.getAllMarkedFileData();
	    int markedSize = allMarked.size();

	    if (!allMarked.isEmpty()/* !view.getAllMarkedFiles().isEmpty() */) {

		for (int i = 0; i < markedSize; i++) {

		    // TEST for(int i = 0; i < view.getAllMarkedFiles().size();
		    // i++){

		    String fileName = view.getAllMarkedFiles().get(i);// allMarked.get(i).name;
		    String filePath = null;// allMarked.get(i).URL;
		    String author = view.getUsername();

		    // ProcessTa
		    String parameters[] = null;
		    //parameters[0] = view.getParameters()[0];

		    // WorkspaceTab
		    String metadata = null;
		    String genomeRelease = null;
		    String expid = null;

		    Boolean converted = model.rawToProfile(fileName, filePath,
			    metadata, genomeRelease, author, expid, parameters);
		    String message = null;

		    if (converted.equals(true)) {
			message = "The server has converted: " + fileName;
			view.printToConvertText(message);

		    } else {
			message = "WARNING - The server couldn't convert: "
				+ fileName + "\n";
			view.printToConvertText(message);
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
	    view.setProccessFileList();

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
	    } else {
		// view.updateQuerySearchResults(ExperimentData.getExample());
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
            //Skicka med arraylist<FileData> för de filer som ska nerladdas
            ArrayList<FileData> selectedFiles = view.getWorkspaceSelectedFiles();
            ArrayList<ExperimentData> experimentData = view.getWorkspaceSelectedExperiments();
            ExperimentData currentExperiment;

            for(int i=0; i<experimentData.size(); i++) {
                currentExperiment = experimentData.get(i);
                for(int j=0; j<currentExperiment.files.length; j++) {
                    selectedFiles.add(currentExperiment.files[j]);
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

	    FileDialog fileDialog = new FileDialog((java.awt.Frame) null,
		    "Choose a directory", FileDialog.SAVE);
	    fileDialog.setVisible(true);
	    String directoryName = fileDialog.getDirectory();
	    System.out.println("You chose " + directoryName);

	    if (fileData == null) {
		System.err.println("No directory selected");
		return;
	    }
	    for (FileData data : fileData) {

		model.downloadFile(data.id, directoryName + "/" + data.name);
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

    class SearchToWorkspaceListener implements ActionListener, Runnable {

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

	    new Thread(this).start();
	}

	@Override
	public void run() {
	    System.out.println("add to workspace");
	    view.addToWorkspace(view.getSelectedFilesWithExpsInSearch());
	    view.addToWorkspace(view.getSelectedExperimentsInSearch());
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

}

