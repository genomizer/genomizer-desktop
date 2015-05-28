package gui.processing;

import java.util.Iterator;

public class RatioCommandComponent extends CommandComponent {

    public static final String COMMAND_NAME = "ratio";

    private String[] fileNames;

    /**
     * Constructs a new RatioCommandComponent with available file names.
     *
     * @param COMMAND_NAME
     *            the command name
     * @param fileNames
     *            the file names
     * @param genomeReleases
     *            the genome releases
     */
    public RatioCommandComponent(String[] fileNames) {

        super(COMMAND_NAME);

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

        RatioParameters[] parameters = new RatioParameters[commandFileRowPanelStack
                .size()];
        Iterator<CommandFileRowPanel> fileRowIterator = commandFileRowPanelStack
                .iterator();
        for (int i = 0; fileRowIterator.hasNext(); i++) {

            RatioFileRow currentFileRow = (RatioFileRow) fileRowIterator.next()
                    .getFileRow();
            parameters[i] = buildProcessParameters(currentFileRow);
        }

        return parameters;
    }

    private RatioParameters buildProcessParameters(RatioFileRow fr) {
        return new RatioParameters(fr.getpreChipFile(), fr.getpostChipFile(),
                fr.getOutFile(), fr.getMean(), fr.getReadsCutoff(),
                fr.getChromosomeText());
    }

    /**
     * Constructs a new RatioFileRow
     */
    @Override
    protected CommandFileRow buildCommandFileRow() {

        return new RatioFileRow(fileNames);

    }

}
