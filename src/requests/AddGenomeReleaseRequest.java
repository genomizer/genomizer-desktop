package requests;


public class AddGenomeReleaseRequest extends Request {

    private String genomeVersion;
    private String specie;
    private String[] files;

    public AddGenomeReleaseRequest(String[] files, String species,
            String genomeVersion) {
        super("AddGenomeRelease", "/genomeRelease", "POST");
        this.files = files;
        this.specie = species;
        this.genomeVersion = genomeVersion;
    }

}
