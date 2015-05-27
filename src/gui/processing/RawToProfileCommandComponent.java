package gui.processing;

import java.util.Iterator;

@SuppressWarnings("serial")
public class RawToProfileCommandComponent extends CommandComponent {

    private String[] fileNames;
    private String[] genomeReleases;

    public RawToProfileCommandComponent(String commandName, String[] fileNames,
            String[] genomeReleases) {

        super(commandName);

        this.fileNames = fileNames;
        this.genomeReleases = genomeReleases;
        addInitialFileRowPanel();

    }

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
        boolean saveSam = false;
        return new RawToProfileParameters(infile, outfile, flags, genomeRelease, saveSam);
    }

    @Override
    protected CommandFileRow buildCommandFileRow() {

        return new RawToProfileFileRow(fileNames, genomeReleases);

    }

}
