package gui.processing;

import java.util.Iterator;

@SuppressWarnings("serial")
public class StepCommandComponent extends CommandComponent {

    public static final String COMMAND_NAME = "step";
    private String[] fileNames;

    public StepCommandComponent(String[] fileNames) {
        super(COMMAND_NAME);
        this.fileNames = fileNames;
        addInitialFileRowPanel();
    }

    @Override
    protected CommandFileRow buildCommandFileRow() {

        return new StepFileRow(fileNames);

    }

    @Override
    public ProcessParameters[] getProcessParameters() {
        StepParameters[] parameters = new StepParameters[commandFileRowPanelStack.size()];
        Iterator<CommandFileRowPanel> fileRowIterator = commandFileRowPanelStack.iterator();
        for(int i = 0; fileRowIterator.hasNext(); i++) {

            StepFileRow currentFileRow = (StepFileRow) fileRowIterator.next().getFileRow();
            parameters[i] = buildProcessParameters(currentFileRow);
        }

        return parameters;
    }

    private StepParameters buildProcessParameters(StepFileRow fileRow) {
        String infile = fileRow.getInFile();
        String outfile = fileRow.getOutFile();
        int stepSize = fileRow.getStepSize();
        return new StepParameters(infile, outfile, stepSize);
    }

}
