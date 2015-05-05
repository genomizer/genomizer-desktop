package requests;

public class AddGenomeReleaseRequest extends Request {

    private String genomeVersion;
    private String specie;
    private String[] files;
    private String[] checkSumsMD5;

    public AddGenomeReleaseRequest(String[] files, String species,
            String genomeVersion, String[] fileCheckSumsMD5) {
        super("AddGenomeRelease", "/genomeRelease", "POST");
        this.files = files;
        this.specie = species;
        this.genomeVersion = genomeVersion;
        this.checkSumsMD5 = fileCheckSumsMD5;
    }

}
