package gui.processing;

import gui.CustomButtonFactory;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import util.IconFactory;

import net.miginfocom.swing.MigLayout;

/**
 *
 * Abstract class representing a process command GUI component. A
 * CommandComponent has a number of CommandFileRows, where the user can specify
 * parameters such as in- and outfiles.
 *
 * @author oi12mlw, oi12pjn
 * @see CommandFileRow
 *
 */
@SuppressWarnings("serial")
public abstract class CommandComponent extends JComponent {

    private static final int BUTTON_WIDTH = 20;
    private static final int BUTTON_HEIGHT = 20;

    public String commandName;
    private JButton removeButton;
    protected Stack<CommandFileRowPanel> commandFileRowPanelStack;

    /**
     * Constructs a new instance of a CommandComponent with the given command
     * name.
     *
     * @param commandName
     *            the command name
     */
    public CommandComponent(String commandName) {
        this.commandName = commandName;
        this.setBorder(new TitledBorder(commandName));
        this.setLayout(new MigLayout());
        commandFileRowPanelStack = new Stack<CommandFileRowPanel>();

    }

    private void addRemoveButton() {
        removeButton = buildRemoveButton();
        this.add(removeButton, "alignx right, wrap");

    }


    private JButton buildRemoveButton() {
        ImageIcon icon = IconFactory.getClearIcon(BUTTON_WIDTH - 2 , BUTTON_HEIGHT - 2);
        ImageIcon hoverIcon = IconFactory.getClearIcon(BUTTON_WIDTH, BUTTON_HEIGHT);
        String tooltip = "Remove this command";
        return CustomButtonFactory.makeCustomButton(icon, hoverIcon, BUTTON_WIDTH, 25, tooltip);
    }

    /**
     * Returns the command name
     *
     * @return the command name
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Adds the initial file row panel to the CommandComponent. This method
     * should be called in inheriting class constructor after super-constructor
     * call
     *
     */
    protected void addInitialFileRowPanel() {

        CommandFileRow commandFileRow = buildCommandFileRow();
        CommandFileRowPanel fileRowPanel = new CommandFileRowPanel(
                commandFileRow);

        fileRowPanel.addAddButtonActionListener(new AddFileRowButtonListener());
        fileRowPanel
                .addRemoveButtonActionListener(new RemoveFileRowButtonListener());

        fileRowPanel.setRemoveButtonEnabled(false);

        commandFileRowPanelStack.push(fileRowPanel);

        addRemoveButton();
        this.add(fileRowPanel, "wrap");


    }

    /**
     * Removes the given CommandFileRowPanel from this CommandComponent
     *
     * @param fileRowPanel
     *            the given CommandFileRowPanel
     */
    private void removeFileRowPanel(CommandFileRowPanel fileRowPanel) {

        CommandFileRowPanel topPanel = commandFileRowPanelStack.peek();
        if (fileRowPanel.equals(topPanel)) {
            commandFileRowPanelStack.pop();
            this.remove(fileRowPanel);

            CommandFileRowPanel newTopPanel = commandFileRowPanelStack.peek();
            newTopPanel.setAddButtonEnabled(true);
        } else {
            commandFileRowPanelStack.remove(fileRowPanel);
            this.remove(fileRowPanel);
        }

        this.revalidate();
        this.repaint();

    }

    /**
     * Adds a new CommandFileRowPanel to the bottom of this CommandComponent
     */
    private void addFileRowPanel() {

        CommandFileRow commandFileRow = buildCommandFileRow();
        CommandFileRowPanel fileRowPanel = new CommandFileRowPanel(
                commandFileRow);
        fileRowPanel.addAddButtonActionListener(new AddFileRowButtonListener());
        fileRowPanel
                .addRemoveButtonActionListener(new RemoveFileRowButtonListener());

        CommandFileRowPanel topPanel = commandFileRowPanelStack.peek();
        topPanel.setAddButtonEnabled(false);
        topPanel.revalidate();
        topPanel.repaint();

        commandFileRowPanelStack.push(fileRowPanel);
        this.add(fileRowPanel, "wrap");
    }

    /**
     * Abstract method for building the correct CommandFileRow used by the
     * inheriting class
     *
     * @return the built CommandFileRow
     */
    protected abstract CommandFileRow buildCommandFileRow();

    /**
     * Returns the parameters put in by the user in the file rows contained by
     * this CommandComponent.
     *
     * @return the input parameters of the CommandComponent
     */
    public abstract ProcessParameters[] getProcessParameters();

    /**
     *
     * Button listener for removal of a file row panel
     *
     * @author oi12mlw, oi12pjn
     * @see CommandFileRowPanel
     */
    private class RemoveFileRowButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton) e.getSource();

            /* The parent of the parent of the button is the file row panel. */
            CommandFileRowPanel parentPanel = (CommandFileRowPanel) sourceButton
                    .getParent().getParent();

            removeFileRowPanel(parentPanel);
        }

    }

    /**
     * Button listener for adding of file row panels
     *
     * @author oi12mlw, oi12pjn
     *
     */
    private class AddFileRowButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            addFileRowPanel();
        }

    }

    public void addRemoveButtonListener(ActionListener removeButtonListener) {
        removeButton.addActionListener(removeButtonListener);

    }

    private class RemoveCommandComponentListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            JButton sourceButton = (JButton) e.getSource();

            // TODO: Come up with better solution.
            /* The parent of the parent of the button is the file scroll panel. */
            CommandScrollPane parentPanel = (CommandScrollPane) sourceButton
                    .getParent().getParent().getParent().getParent().getParent();

            parentPanel.removeCommand((CommandComponent) sourceButton
                    .getParent().getParent());

        }
    }

}
