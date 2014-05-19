package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import util.IconFactory;

public class UploadFileRow extends JPanel {
    private ExperimentPanel parent;
    private JPanel filePanel;
    private JLabel fileLabel;
    private JButton closeButton, uploadButton;
    private JComboBox typeBox;
    private JProgressBar uploadBar;
    private File file;

    public UploadFileRow(File f, ExperimentPanel parent, boolean newExp) {
        this.parent = parent;
        setLayout(new BorderLayout());
        this.file = f;
        filePanel = new JPanel();
        add(filePanel, BorderLayout.SOUTH);
        setContent(newExp);
    }

    private void setContent(boolean newExp) {
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[] { 0, 0, 0, 0 };
        gbl.rowHeights = new int[] { 0, 0 };
        gbl.columnWeights = new double[] { 1.0, 0.0, 0.0,
                Double.MIN_VALUE };
        gbl.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
        filePanel.setLayout(gbl);

        fileLabel = new JLabel(file.getName());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        filePanel.add(fileLabel, gbc);

        uploadBar = new JProgressBar(0, 100);
        uploadBar.setStringPainted(true);
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        filePanel.add(uploadBar, gbc);

        String[] fileTypes = { "Profile", "Raw", "Region" };
        typeBox = new JComboBox(fileTypes);
        typeBox.setPreferredSize(new Dimension(120, 31));
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        filePanel.add(typeBox, gbc);
        closeButton = CustomButtonFactory.makeCustomButton(
                IconFactory.getStopIcon(30, 30),
                IconFactory.getStopHoverIcon(32, 32), 32, 32, "Stop upload");
        addCloseButtonListener(new closeButtonListener());
        GridBagConstraints gbc_btnX = new GridBagConstraints();
        gbc_btnX.gridx = 2;
        gbc_btnX.gridy = 1;
        filePanel.add(closeButton, gbc_btnX);

        if(newExp) {
            uploadButton = CustomButtonFactory.makeCustomButton(
                    IconFactory.getUploadIcon(25,25),
                    IconFactory.getUploadHoverIcon(28,28), 28, 28, "Upload file to current experiment");
            gbc.insets = new Insets(0, 0, 0, 5);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridx = 3;
            gbc.gridy = 1;
            filePanel.add(uploadButton, gbc);
        }
    }

    public void addCloseButtonListener(ActionListener listener) {
        closeButton.addActionListener(listener);
    }

    public String getFileName() {
        return file.getName();
    }

    public String getType() {
        return typeBox.getSelectedItem().toString();
    }

    public void updateProgressBar(float progress) {
        /*Progress is handled as %*/
        uploadBar.setMinimum(0);
        uploadBar.setMaximum(100);
        uploadBar.setValue((int)progress);
    }

    class closeButtonListener implements ActionListener, Runnable {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(this).start();
        }

        @Override
        public void run() {
            parent.deleteFileRow(file);
        }
    }
}
