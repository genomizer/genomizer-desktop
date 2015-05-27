package gui.processing;

import java.util.Iterator;

public class RatioCommandComponent extends CommandComponent {

    private static final String commandName = "ratio";

    private String[] fileNames;

    /**
     * Constructs a new RawToProfileCommandComponent with the given command
     * name, available file names and available genome releases.
     *
     * @param commandName
     *            the command name
     * @param fileNames
     *            the file names
     * @param genomeReleases
     *            the genome releases
     */
    public RatioCommandComponent( String[] fileNames ) {

        super(commandName);

        this.fileNames = fileNames;
        addInitialFileRowPanel();

    }

    /**
     * Returns the parameters put in by the user in the file rows contained by
     * this CommandComponent.
     *
     * @return the input parameters of the CommandComponent
     */
    @Override
    public ProcessParameters[] getProcessParameters() {

        RawToProfileParameters[] parameters = new RawToProfileParameters[commandFileRowPanelStack.size()];
        Iterator<CommandFileRowPanel> fileRowIterator = commandFileRowPanelStack.iterator();
        for(int i = 0; fileRowIterator.hasNext(); i++) {

            RawToProfileFileRow currentFileRow = (RawToProfileFileRow) fileRowIterator.next().getFileRow();
            parameters[i] = buildProcessParameters(currentFileRow);
        }

        return parameters;
    }

    private RawToProfileParameters buildProcessParameters(RawToProfileFileRow fileRow) {
        String infile = fileRow.getInFile();
        String outfile = fileRow.getOutFile();
        String flags = fileRow.getFlags();
        String genomeRelease = fileRow.getGenomeRelease();
        boolean keepSam = fileRow.getKeepSam();
        return new RawToProfileParameters(infile, outfile, flags, genomeRelease, keepSam);
    }

    /**
     * Constructs a new RawToProfileFileRow
     */
    @Override
    protected CommandFileRow buildCommandFileRow() {

        return new RatioFileRow(fileNames);

    }

}