package controller;

import gui.GenomizerView;
import gui.CheckListItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;

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
	//view.addFileListMouseListener(new MouseAdapter(mouseClicked(MouseEvent event) );

    }

    class ConvertFileListener implements ActionListener, Runnable {
	@Override
	public void actionPerformed(ActionEvent e) {
	    new Thread(this).start();
	}

	@Override
	public void run() {

		//SKICKAR REQUEST MED ALLA FILER SOM SKALL CONVERTERAS
		for(int i = 0; i < view.getAllMarkedFiles().size(); i++){
			System.out.println("CONVERTING [" + view.getAllMarkedFiles().get(i) + "]");
		}

	}
    }

    class LoginListener implements ActionListener, Runnable {
	@Override
	public void actionPerformed(ActionEvent e) {
	    new Thread(this).start();
	}

	@Override
	public void run() {
	    String username = view.getUsername();
	    String pwd = view.getPassword();
	    view.updateLoginAccepted(username, pwd, "Yuri Gagarin");
	    // if (model.loginUser(username, pwd)) {
	    // view.updateLoginAccepted(username, pwd, "Yuri Gagarin");
	    // } else {
	    // view.updateLoginNeglected("Login not accepted");
	    // }
	}
    }

    class QuerySearchListener implements ActionListener, Runnable {
	public void actionPerformed(ActionEvent e) {
	    new Thread(this).start();
	}

	@Override
	public void run() {
	    String pubmed = view.getQuerySearchString();
	    model.search(pubmed);
	}
    }

    class LogoutListener implements ActionListener, Runnable {
	public void actionPerformed(ActionEvent e) {
	    new Thread(this).start();
	}

	@Override
	public void run() {
	    view.updateLogout();
	    // if (model.logoutUser()) {
	    // view.updateLogout();
	    // } else {
	    // view.updateLogout();
	    // }

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

    class DownloadListener implements ActionListener, Runnable {

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

	    new Thread(this).start();
	}

	@Override
	public void run() {
	}
    }

    public void mouseClicked(MouseEvent event)
    {
       JList list = (JList) event.getSource();

       // Get index of item clicked

       int index = list.locationToIndex(event.getPoint());
       CheckListItem item = (CheckListItem)
          list.getModel().getElementAt(index);

       // Toggle selected state

       item.setSelected(! item.isSelected());

       // Repaint cell

       list.repaint(list.getCellBounds(index, index));
    }
}
