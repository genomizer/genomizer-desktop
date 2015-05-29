package requests;

import gui.processing.ProcessCommand;

public class ProcessCommandRequest extends Request{
    public String expId;
    public ProcessCommand[] processCommands;

    public ProcessCommandRequest(String expId, ProcessCommand[] processCommands) {
        super("process","/process/processCommands","PUT");
        this.expId = expId;
        this.processCommands = processCommands;
    }

}
