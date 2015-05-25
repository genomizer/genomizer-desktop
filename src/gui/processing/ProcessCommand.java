package gui.processing;

public class ProcessCommand {

    public String type;
    public ProcessParameters[] files;

    public ProcessCommand(String type, ProcessParameters[] files) {
        this.type = type;
        this.files = files;
    }

}
