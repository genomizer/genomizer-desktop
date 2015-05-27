package gui.processing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import gui.CustomButtonFactory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import util.IconFactory;

public class CommandFileRowPanel extends JPanel {

    private static final int BUTTON_WIDTH = 17;
    private static final int BUTTON_HEIGHT = 17;
    private JButton removeButton;
    private JButton addButton;
    private CommandFileRow commandFileRow;
    private JPanel buttonPanel;

    public CommandFileRowPanel(CommandFileRow commandFileRow) {
        super();
        this.commandFileRow = commandFileRow;
        this.setLayout(new FlowLayout());
        addComponents();
    }

    private void addComponents() {
        buildButtonPanel();
        this.add((Component) commandFileRow);
        this.add(buttonPanel);
    }

    private void buildButtonPanel() {

        removeButton = buildRemoveButton();
        addButton = buildAddButton();
        buttonPanel = new JPanel(new GridLayout(2,2));
        JPanel removeButtonPaddingPanel = new JPanel();
        JPanel addButtonPaddingPanel = new JPanel();
        Dimension paddingSize = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);

        removeButtonPaddingPanel.setPreferredSize(paddingSize);
        addButtonPaddingPanel.setPreferredSize(paddingSize);
        buttonPanel.add(removeButtonPaddingPanel);
        buttonPanel.add(addButtonPaddingPanel);
        buttonPanel.add(removeButton);
        buttonPanel.add(addButton);

    }

    private JButton buildRemoveButton() {
        ImageIcon icon = IconFactory.getMinusIcon(BUTTON_WIDTH - 2 , BUTTON_HEIGHT - 2);
        ImageIcon hoverIcon = IconFactory.getMinusIcon(BUTTON_WIDTH, BUTTON_HEIGHT);
        String tooltip = "Remove this file row";
        return CustomButtonFactory.makeCustomButton(icon, hoverIcon, BUTTON_WIDTH, 25, tooltip);
    }

    private JButton buildAddButton() {
        ImageIcon icon = IconFactory.getPlusIcon(BUTTON_WIDTH - 2 , BUTTON_HEIGHT - 2);
        ImageIcon hoverIcon = IconFactory.getPlusIcon(BUTTON_WIDTH, BUTTON_HEIGHT);
        String tooltip = "Add a new file row";
        return CustomButtonFactory.makeCustomButton(icon, hoverIcon, BUTTON_WIDTH, 25, tooltip);
    }

    public void setAddButtonEnabled(boolean enabled) {
        if(enabled) {
            buttonPanel.add(addButton);
        } else {
            buttonPanel.remove(addButton);
        }
    }

    public void setRemoveButtonEnabled(boolean enabled) {
        if(enabled) {
            buttonPanel.add(removeButton);
        } else {
            
            buttonPanel.remove(removeButton);
            JPanel removeButtonPaddingPanel = new JPanel();
            Dimension paddingSize = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
            removeButtonPaddingPanel.setPreferredSize(paddingSize);
            buttonPanel.add(removeButtonPaddingPanel);
        }
    }

    public void addRemoveButtonActionListener(ActionListener al) {
        removeButton.addActionListener(al);
    }

    public void addAddButtonActionListener(ActionListener al) {
        addButton.addActionListener(al);
    }

    public CommandFileRow getFileRow() {
        // TODO Auto-generated method stub
        return commandFileRow;
    }
}
