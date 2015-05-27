package gui.processing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.TitledBorder;

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

    private String commandName;
    protected Stack<CommandFileRowPanel> commandFileRowPanelStack;

    /**
     * Constructs a new instance of a CommandComponent with the given command
     * name.
     *
     * @param commandName
     *            the command name
     */
    public CommandComponent( String commandName ){
        this.commandName = commandName;
        this.setBorder(new TitledBorder(commandName));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        commandFileRowPanelStack = new Stack<CommandFileRowPanel>();
    }

    /**
     * Returns the command name
     *
     * @return the command name
     */
    public String getCommandName(){
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
        CommandFileRowPanel fileRowPanel = new CommandFileRowPanel(commandFileRow);

        fileRowPanel.addAddButtonActionListener(new AddFileRowButtonListener());
        fileRowPanel.addRemoveButtonActionListener(new RemoveFileRowButtonListener());

        fileRowPanel.setRemoveButtonEnabled(false);

        commandFileRowPanelStack.push(fileRowPanel);
        this.add(fileRowPanel);

    }

    /**
     * Removes the given CommandFileRowPanel from this CommandComponent
     *
     * @param fileRowPanel
     *            the given CommandFileRowPanel
     */
    private void removeFileRowPanel(CommandFileRowPanel fileRowPanel) {

        CommandFileRowPanel topPanel = commandFileRowPanelStack.peek();
        if(fileRowPanel.equals(topPanel)) {
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
        CommandFileRowPanel fileRowPanel = new CommandFileRowPanel(commandFileRow);
        fileRowPanel.addAddButtonActionListener(new AddFileRowButtonListener());
        fileRowPanel.addRemoveButtonActionListener(new RemoveFileRowButtonListener());

        CommandFileRowPanel topPanel = commandFileRowPanelStack.peek();
        topPanel.setAddButtonEnabled(false);
        topPanel.revalidate();
        topPanel.repaint();

        commandFileRowPanelStack.push(fileRowPanel);
        this.add(fileRowPanel);
    }

    /**
     * Abstract method for building the correct CommandFileRow used by the inheriting class
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
            CommandFileRowPanel parentPanel = (CommandFileRowPanel) sourceButton.getParent().getParent();

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
}
