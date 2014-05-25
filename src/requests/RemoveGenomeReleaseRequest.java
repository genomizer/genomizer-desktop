package requests;

public class RemoveGenomeReleaseRequest extends Request {

    public RemoveGenomeReleaseRequest(String species, String version) {
        super("deleteGenomeRelease",
                "/genomeRelease/" + species + "/" + version, "DELETE");



    }

}
