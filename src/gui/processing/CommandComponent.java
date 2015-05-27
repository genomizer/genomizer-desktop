package gui.processing;

import javax.swing.JComponent;

public abstract class CommandComponent extends JComponent {

    private String commandName;

    public abstract ProcessParameters[] getProcessParameters();

    public CommandComponent( String commandName ){
        this.commandName = commandName;
    }

    public String getCommandName(){
        return commandName;
    }
}
