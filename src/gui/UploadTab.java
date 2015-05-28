package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import util.ActivePanel;
import util.AnnotationDataType;
import util.ExperimentData;
import util.GenomeReleaseData;

/**
 * A class representing a upload view in an application for genome research.
 * This class allows the user to upload files to the database of the
 * application.
 */
public class UploadTab extends JPanel {

    private static final long serialVersionUID = -2830290705724588252L;
    private JButton existingExpButton, newExpButton;
    private JPanel northPanel, expNamePanel, uploadPanel;
    private UploadExpPanel uploadExpPanel;
    private ActivePanel activePanel;
    private JLabel boldTextLabel;
    private JTextField experimentNameField;
    private JScrollPane uploadScroll;

    /**
     * Gets the UploadToNewExpPanel
     *
     * @return the uploadToNewExpPanel
     */
    public UploadExpPanel getUploadToNewExpPanel() {
        return uploadExpPanel;
    }

    /**
     * Constructor creating a upload tab.
     */
    public UploadTab() {

        activePanel = ActivePanel.NONE;
        setLayout(new BorderLayout());
        uploadExpPanel = new UploadExpPanel();

        northPanel = new JPanel();
        expNamePanel = new JPanel();
        add(northPanel, BorderLayout.NORTH);

        experimentNameField = new JTextField();
        experimentNameField.setColumns(30);
        expNamePanel.add(experimentNameField);

        northPanel.add(expNamePanel);
        northPanel.setBorder(BorderFactory.createTitledBorder("Upload"));

        existingExpButton = new JButton("Search for existing experiment");

        // TODO: Ta bort knappen och textfältet sen om det inte kommer behövas
        // senare!
        existingExpButton.setVisible(false);
        experimentNameField.setVisible(false);

        newExpButton = new JButton("Create new experiment");
        experimentNameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                existingExpButton.doClick();
            }
        });
        northPanel.add(existingExpButton, BorderLayout.EAST);
        northPanel.add(newExpButton, BorderLayout.EAST);
        uploadPanel = new JPanel(new BorderLayout());
        uploadScroll = new JScrollPane(uploadPanel);
        uploadScroll.getVerticalScrollBar().setUnitIncrement(16);
        add(uploadScroll, BorderLayout.CENTER);
        boldTextLabel = new JLabel(
                "<html><b>Bold text indicates a forced annotation.</b></html>");
        boldTextLabel.setOpaque(true);

    }

    /**
     * Method adding a listener to the "addToExistingExpButton".
     *
     * @see controller.UploadTabController#AddToExistingExpButtonListener()
     * @param listener
     *            The listener to add file to existing experiment.
     */
    public void addAddToExistingExpButtonListener(ActionListener listener) {
        existingExpButton.addActionListener(listener);
    }

    /**
     * Method adding a listener to the "newExpButton".
     *
     * @see controller.UploadTabController#NewExpButtonListener()
     * @param listener
     *            The listener to create a experiment.
     */
    public void addNewExpButtonListener(ActionListener listener) {
        newExpButton.addActionListener(listener);
    }

    /**
     * Displays a panel for adding to an existing experiment.
     *
     * @param ed
     *            The experiment data for the existing experiment.
     */
    public void addExistingExpPanel(ExperimentData ed,
            AnnotationDataType[] annot) {
        killContentsOfUploadPanel();
        // TODO Rensa gammal implementation CF
        // activePanel = ActivePanel.EXISTING;
        // uploadToExistingExpPanel.build();
        // uploadToExistingExpPanel.addExistingExp(ed);
        // uploadPanel.add(uploadToExistingExpPanel, BorderLayout.CENTER);
        uploadExpPanel.createNewExpPanel(annot, false);
        uploadExpPanel.setExistingExp(ed);
        uploadPanel.add(uploadExpPanel, BorderLayout.CENTER);
        activePanel = ActivePanel.NEW;
        repaint();
        revalidate();
    }

    // the gui's interface had one javadoc, and here's a different one
    /**
     * Displays a panel for creating a new experiment. <br>
     * OR<br>
     * Creates a new experiment to upload to using the provided annotations.
     *
     * @param annotations
     *            The available annotations at the server.<br>
     *            OR<br>
     *            The annotations of the new experiment.
     */
    public void addNewExpPanel(AnnotationDataType[] annotations) {
        killContentsOfUploadPanel();
        uploadExpPanel.createNewExpPanel(annotations, true);
        uploadPanel.add(uploadExpPanel, BorderLayout.CENTER);
        activePanel = ActivePanel.NEW;
        repaint();
        revalidate();
    }

    public UploadExpPanel getNewExpPanel() {
        return uploadExpPanel;
    }

    public boolean newExpStarted() {
        return activePanel == ActivePanel.NEW;
    }

    /**
     * Method returning the text in the experiment name field.
     *
     * @return a String with the experiment name.
     */
    public String getSearchText() {
        return experimentNameField.getText();
    }

    public JButton getExistingExpButton() {
        return existingExpButton;
    }

    public JTextField getExperimentNameField() {
        return experimentNameField;
    }

    public String getGenomeVersion(File f) {

        return uploadExpPanel.getGenomeVersion(f);

    }

    /**
     * Ummm No idea, wrong sprint anyway, TODO
     *
     * @param grd
     */
    public void setGenomeReleases(GenomeReleaseData[] grd) {
        uploadExpPanel.setGenomeReleases(grd);
    }

    /**
     * A method removing the components in the panels when one of them gets
     * chosen by the user, to make sure the new components won't overlap and end
     * up invisible. The method checks the Enum ActivePanel to check which panel
     * was the active one.
     */
    public void killContentsOfUploadPanel() {
        switch (activePanel) {
            case NONE:
                break;
            default:
                uploadPanel.remove(uploadExpPanel);
                uploadExpPanel.removeAll();
                repaint();
                revalidate();
                activePanel = ActivePanel.NONE;
                break;
        }
    }
    /**
     * NOT IMPLEMENTED AT ALL!
     * @param enabled TODO
     * @param f
     */
    public void setFileRowsEnabled(boolean enabled) {
        Iterator<UploadFileRow> i = uploadExpPanel.getFileRows().values().iterator();
        while(i.hasNext()){
            UploadFileRow currentRow = i.next();
            currentRow.setRowEnabled(enabled);
        }

    }

}
