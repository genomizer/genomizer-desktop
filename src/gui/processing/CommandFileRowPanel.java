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

/**
 * Class for containing command file row and buttons for removal and adding of new file row panels.
 * @author oi12mlw, oi12pjn
 * @see CommandFileRow
 */
@SuppressWarnings("serial")
public class CommandFileRowPanel extends JPanel {

    private static final int BUTTON_WIDTH = 17;
    private static final int BUTTON_HEIGHT = 17;
    private JButton removeButton;
    private JButton addButton;
    private CommandFileRow commandFileRow;
    private JPanel buttonPanel;

    /**
     * Constructs a new CommandFileRowPanel instance from a given CommandFileRow
     * @param commandFileRow the CommandFileRow
     */
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

    /**
     * Builds a padded button panel for the add and remove buttons
     */
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

    /**
     * Enables or disables (adds or removes) the add button from this panel
     * @param enabled enables if true, disables otherwise
     */
    public void setAddButtonEnabled(boolean enabled) {
        if(enabled) {
            buttonPanel.add(addButton);
        } else {
            buttonPanel.remove(addButton);
        }
    }

    /**
     * Enables or disables (adds or removes) the remove button from this panel
     * @param enabled enables if true, disables otherwise
     */
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

    /**
     * Adds an action listener to the remove button
     * @param al the action listener
     */
    public void addRemoveButtonActionListener(ActionListener al) {
        removeButton.addActionListener(al);
    }

    /**
     * Adds an action listener to the add button
     * @param al the action listener
     */
    public void addAddButtonActionListener(ActionListener al) {
        addButton.addActionListener(al);
    }

    /**
     * Returns the CommandFileRow contained by this panel
     * @return the CommandFileRow contained by this panel
     */
    public CommandFileRow getFileRow() {

        return commandFileRow;
    }
}
