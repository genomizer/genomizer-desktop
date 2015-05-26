package gui.processing;

import gui.CustomButtonFactory;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import model.ErrorLogger;

import util.IconFactory;

@SuppressWarnings("serial")
public class RawToProfileCommandComponent extends JComponent {

    private String commandName;
    private String[] fileNames;
    private String[] genomeReleases;

    private JButton newFileRowButton;

    private HashMap<JButton, JPanel> removeButtonToPanelMap = new HashMap<JButton, JPanel>();

    public RawToProfileCommandComponent(String commandName, String[] fileNames, String[] genomeReleases) {

        this.commandName = commandName;
        this.fileNames = fileNames;
        this.genomeReleases = genomeReleases;

        this.setBorder(new TitledBorder(commandName));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        addFileRow();
    }


    public static void main(String[] args) {

        try {
            UIManager
                    .setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }


        JFrame frame = new JFrame();
        String commandName = "Glass e gott";
        String[] fileNames = {"bild.png", "stinkern.txt", "hus.jpg"};
        String[] genomeReleases = {"hg37", "hg38", "rat"};
        frame.add(new RawToProfileCommandComponent(commandName, fileNames, genomeReleases));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void addFileRow() {

        JPanel fileRowPanel = new JPanel();
        fileRowPanel.setLayout(new FlowLayout());

        RawToProfileFileRow fileRow = new RawToProfileFileRow(fileNames, genomeReleases);

        
        JPanel removeButtonPanel = new JPanel(new GridLayout(2,1));   
        
        JButton removeButton = buildRemoveButton();
        removeButton.addActionListener(new RemoveButtonListener());
        removeButtonToPanelMap.put(removeButton, fileRowPanel);
        
        JPanel paddingPanel = new JPanel();
        paddingPanel.setPreferredSize(new Dimension(17, 17));
        removeButtonPanel.add(paddingPanel);
        removeButtonPanel.add(removeButton);

        fileRowPanel.add(fileRow);
        fileRowPanel.add(removeButtonPanel);

        this.add(fileRowPanel);
        addNewFileRowButton();
        this.repaint();
        this.revalidate();

    }
    

    private JButton buildRemoveButton() {
        ImageIcon icon = IconFactory.getMinusIcon(15, 15);
        ImageIcon hoverIcon = IconFactory.getMinusIcon(17, 17);
        int width = 17;
        int height = 25;
        String tooltip = "Remove this file row";
        return CustomButtonFactory.makeCustomButton(icon, hoverIcon, width, height, tooltip);
    }

    private JButton buildNewRowButton() {
        ImageIcon icon = IconFactory.getPlusIcon(15, 15);
        ImageIcon hoverIcon = IconFactory.getPlusIcon(17, 17);
        int width = 17;
        int height = 25;
        String tooltip = "Add a new file row";
        return CustomButtonFactory.makeCustomButton(icon, hoverIcon, width, height, tooltip);
    }

    private void removeFileRow(Object source) {
        JPanel fileRowToRemove = removeButtonToPanelMap.get(source);
        this.remove(fileRowToRemove);
        removeButtonToPanelMap.remove(source);
        this.repaint();
        this.revalidate();

    }

    private void addNewFileRowButton() {
        if (newFileRowButton != null) {
            this.remove(newFileRowButton);
        }
        newFileRowButton = buildNewRowButton();
        newFileRowButton.addActionListener(new NewFileRowButtonListener());
        this.add(newFileRowButton);
    }

    private class RemoveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            removeFileRow(e.getSource());
        }

    }

    private class NewFileRowButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            addFileRow();
        }

    }

}
