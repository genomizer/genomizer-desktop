package gui.processing;

import java.util.Iterator;

@SuppressWarnings("serial")
public class SmoothingCommandComponent extends CommandComponent {


    public static final String COMMAND_NAME = "smoothing";
    private String[] fileNames;

    public SmoothingCommandComponent(String[] fileNames) {
        super(COMMAND_NAME);
        this.fileNames = fileNames;
        addInitialFileRowPanel();
    }

    @Override
    protected CommandFileRow buildCommandFileRow() {

        return new SmoothingFileRow(fileNames);

    }

    @Override
    public ProcessParameters[] getProcessParameters() {
        SmoothingParameters[] parameters = new SmoothingParameters[commandFileRowPanelStack.size()];
        Iterator<CommandFileRowPanel> fileRowIterator = commandFileRowPanelStack.iterator();
        for(int i = 0; fileRowIterator.hasNext(); i++) {

            SmoothingFileRow currentFileRow = (SmoothingFileRow) fileRowIterator.next().getFileRow();
            parameters[i] = buildProcessParameters(currentFileRow);
        }

        return parameters;
    }

    private SmoothingParameters buildProcessParameters(SmoothingFileRow fileRow) {

        String infile = fileRow.getInFile();
        String outfile = fileRow.getOutFile();
        int windowSize = fileRow.getWindowSize();
        String meanOrMedian = fileRow.getMeanOrMedian();
        int minSmooth = fileRow.getMinSmooth();
        return new SmoothingParameters(infile, outfile, windowSize, meanOrMedian, minSmooth);

    }
}
