package requests;

public class AddGenomeReleaseRequest extends Request {

    private String fileName;
    private String specie;
    private String genomeVersion;

    public AddGenomeReleaseRequest(String fileName, String specie,
            String genomeVersion) {
        super("AddGenomeRelease", "/genomeRelease", "POST");
        this.fileName = fileName;
        this.specie = specie;
        this.genomeVersion = genomeVersion;

    }

}
