package gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.AnnotationDataValue;
import util.ExperimentData;
import util.FileDrop;
import util.GenomeReleaseData;

public class UploadToExistingExpPanel extends JPanel implements ExperimentPanel {

    private static final long serialVersionUID = 7796310269631189223L;
    private JButton selectFilesToUploadButton, uploadFilesToExperimentButton;
    private JPanel northPanel, centerPanel, uploadFilesPanel, buttonsPanel;
    private HashMap<File, UploadFileRow> uploadFileRows;
    private ExperimentData ed;
    private ArrayList<String> genome;
    /**
     * Initiates an uploadToExistingExpPanel with its standard buttons and
     * panels. Calls the method build() to build it further.
     */

    public UploadToExistingExpPanel() {
        selectFilesToUploadButton = new JButton("Browse for files");
        uploadFilesToExperimentButton = new JButton("Upload files");
        uploadFileRows = new HashMap<>();
        genome = new ArrayList<String>();

        northPanel = new JPanel(new BorderLayout());
        centerPanel = new JPanel(new BorderLayout());
        uploadFilesPanel = new JPanel(new GridLayout(0, 1));
        buttonsPanel = new JPanel(new FlowLayout());

        setLayout(new BorderLayout());
        addFileDrop();
        build();
    }

    public HashMap<File, UploadFileRow> getFileRows() {
        return uploadFileRows;
    }

    /**
     * Builds/rebuilds the panel. This is not part of the constructor so it can
     * be called from elsewhere aswell.
     */
    public void build() {
        add(northPanel, BorderLayout.NORTH);
        centerPanel.add(uploadFilesPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
        gbl_panel.rowHeights = new int[] { 0, 0, 0, 0 };
        gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE };
        gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
        northPanel.setLayout(gbl_panel);
        repaintSelectedFiles();
    }

    /**
     * Creates an uploadFileRow from the provided files. Checks if the files are
     * already in an uploadFileRow so there won't be duplicates. Displays an
     * error message if it was selected and added previously.
     *
     * @param files The files to make an uploadFileRow out of.
     */
    public void createUploadFileRow(File[] files) {
        for (File f : files) {
            if (!uploadFileRows.containsKey(f)) {
                UploadFileRow fileRow = new UploadFileRow(f, this, false);
                uploadFileRows.put(f, fileRow);
            } else {
                JOptionPane.showMessageDialog(this, "File already selected: "
                                + f.getName() + "", "File error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
        repaintSelectedFiles();
    }

    /**
     * Deletes an uploadFileRow and calls repaintSelectedFiles() to repaint. If
     * it fails to find the file, an error message is shown to the user.
     *
     * @param f This is used to identify which uploadFileRow to be deleted.
     */
    public void deleteFileRow(File f) {
        if (uploadFileRows.containsKey(f)) {
            uploadFileRows.remove(f);
            uploadFilesPanel.removeAll();
            repaintSelectedFiles();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Can't delete file: " + f.getName() + "", "File error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds a listener for the "Select files" button.
     *
     * @param listener The listener to be added.
     */
    public void addSelectFilesToUploadButtonListener(ActionListener listener) {
        selectFilesToUploadButton.addActionListener(listener);
    }

    /**
     * Adds a listener to the upload button.
     *
     * @param listener The listener to be added.
     */
    public void addUploadToExperimentButtonListener(ActionListener listener) {
        uploadFilesToExperimentButton.addActionListener(listener);
    }

    /**
     * Checks if there are any uploadfilerows. Disables the uploadbutton if
     * there aren't, and adds them to the panel if there are. After these
     * updates, it repaints the panel.
     */
    private void repaintSelectedFiles() {
        if (!uploadFileRows.isEmpty()) {
            for (File f : uploadFileRows.keySet()) {
                uploadFilesPanel.add(uploadFileRows.get(f));
            }
        } else {
            enableUploadButton();
        }
        buttonsPanel.add(selectFilesToUploadButton);
        buttonsPanel.add(uploadFilesToExperimentButton);
        uploadFilesPanel.add(buttonsPanel);
        repaint();
        revalidate();
    }

    /**
     * Tries to set the experiment button to either be enabled or disabled. If
     * there are no fileRows, it won't be set to true.
     */
    public void enableUploadButton() {
        if (!uploadFileRows.isEmpty()) {
            uploadFilesToExperimentButton.setEnabled(true);
        } else {
            uploadFilesToExperimentButton.setEnabled(false);
        }
    }

    /**
     * Removes everything in the panel and underlying panels.
     */
    @Override
    public void removeAll() {
        uploadFilesPanel.removeAll();
        northPanel.removeAll();
        super.removeAll();
    }

    /**
     * @param ed The experiment to be added, in the form of ExperimentData
     */
    public void addExistingExp(ExperimentData ed) {
        this.ed = ed;
        build();
        ArrayList<AnnotationDataValue> annot = ed.getAnnotations();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 30);
        int x = 0;
        int y = 0;
        gbc.gridx = x;
        gbc.gridy = y;
        JPanel exp = new JPanel(new BorderLayout());
        JLabel expHeader = new JLabel("Experiment ID");
        Font font = expHeader.getFont();
        Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
        expHeader.setFont(boldFont);
        JTextField expID = new JTextField(ed.getName());
        expID.setEnabled(false);
        expID.setOpaque(true);
        expID.setDisabledTextColor(Color.BLACK);
        exp.add(expHeader, BorderLayout.NORTH);
        exp.add(expID, BorderLayout.CENTER);
        northPanel.add(exp, gbc);
        x++;
        for (AnnotationDataValue adv : annot) {
            if (x > 6) {
                x = 0;
                y++;
            }
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 0, 5, 30);
            gbc.gridx = x;
            gbc.gridy = y;
            JPanel p = new JPanel(new BorderLayout());
            if (adv.getName().equalsIgnoreCase("species")) {
                adv.getValue();
            }
            JLabel annotationHeader = new JLabel(adv.getName());
            font = annotationHeader.getFont();
            boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
            annotationHeader.setFont(boldFont);
            JTextField annotationValue = new JTextField(adv.getValue());
            annotationValue.setEnabled(false);
            annotationValue.setOpaque(true);
            annotationValue.setDisabledTextColor(Color.BLACK);
            p.add(annotationHeader, BorderLayout.NORTH);
            p.add(annotationValue, BorderLayout.CENTER);
            northPanel.add(p, gbc);
            x++;
        }
    }

    public ExperimentData getExperiment() {
        return ed;
    }

    public ArrayList<File> getFilesToUpload() {
        ArrayList<File> files = new ArrayList<>();
        for (File f : uploadFileRows.keySet()) {
            files.add(f);
        }
        return files;
    }

    public HashMap<String, String> getTypes() {
        HashMap<String, String> types = new HashMap<>();
        for (File f : uploadFileRows.keySet()) {
            types.put(f.getName(), uploadFileRows.get(f).getType());
        }
        return types;
    }

    /**
     * Makes dragging & dropping of files into the panel possible
     */
    public void addFileDrop() {
        new FileDrop(this, new FileDrop.Listener() {
            public void filesDropped(java.io.File[] files) {
                createUploadFileRow(files);
                enableUploadButton();
            }
        });
    }

    public String getGenomeVersion(File f) {
        if (uploadFileRows.containsKey(f)) {
            return uploadFileRows.get(f).getGenomeRelease();
        }
        return null;
    }

    public void setGenomeReleases(GenomeReleaseData[] grd) {
        if (genome.size() > 0) {
            genome.clear();
        }
        if (grd.length > 0) {
            for (GenomeReleaseData g : grd) {
                try {
                    genome.add(g.getVersion());
                } catch (NullPointerException e) {
                    // TODO: Catch NullpointerEx emtpy, looks very stupid... OO
//                    System.out.println("Couldn't find genome version.");
                }
            }
        }
    }

    public ArrayList<String> getGenomeReleases() {
        return genome;
    }
}
