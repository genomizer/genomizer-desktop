package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UploadToExistingExpPanel extends JPanel {

    private JButton selectFilesToUploadButton;
    private JButton uploadFilesToExperimentButton;

    public UploadToExistingExpPanel() {
        selectFilesToUploadButton = new JButton("Select files");
        uploadFilesToExperimentButton = new JButton("Upload to experiment");
        add(selectFilesToUploadButton, BorderLayout.NORTH);
        add(uploadFilesToExperimentButton, BorderLayout.SOUTH);
        uploadFilesToExperimentButton.disable();
    }

    public void addSelectFilesToUploadButtonListener(ActionListener listener) {
        selectFilesToUploadButton.addActionListener(listener);
    }

    public void addUploadToExperimentButtonListener(ActionListener listener) {
        uploadFilesToExperimentButton.addActionListener(listener);
    }


}
