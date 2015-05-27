package gui.processing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public abstract class CommandComponent extends JComponent {

    private String commandName;
    protected Stack<CommandFileRowPanel> commandFileRowPanelStack;

    public abstract ProcessParameters[] getProcessParameters();

    public CommandComponent( String commandName ){
        this.commandName = commandName;
        this.setBorder(new TitledBorder(commandName));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        commandFileRowPanelStack = new Stack<CommandFileRowPanel>();
    }

    public String getCommandName(){
        return commandName;
    }

    /* Should be called in inheriting class constructor after super call */
    protected void addInitialFileRowPanel() {

        CommandFileRow commandFileRow = buildCommandFileRow();
        CommandFileRowPanel fileRowPanel = new CommandFileRowPanel(commandFileRow);

        fileRowPanel.addAddButtonActionListener(new AddFileRowButtonListener());
        fileRowPanel.addRemoveButtonActionListener(new RemoveFileRowButtonListener());

        fileRowPanel.setRemoveButtonEnabled(false);

        commandFileRowPanelStack.push(fileRowPanel);
        this.add(fileRowPanel);

    }

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

    protected abstract CommandFileRow buildCommandFileRow();

    private class RemoveFileRowButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton) e.getSource();
            CommandFileRowPanel parentPanel = (CommandFileRowPanel) sourceButton.getParent().getParent();
            removeFileRowPanel(parentPanel);
        }

    }

    private class AddFileRowButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            addFileRowPanel();
        }


    }
}
