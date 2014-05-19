package requests;

public class RemoveGenomeReleaseRequest extends Request {

    public RemoveGenomeReleaseRequest(String specie, String version) {
        super("deleteGenomeRelease",
                "/genomeRelease/" + specie + "/" + version, "DELETE");

    }

}
