package requests;

public class GetGenomeSpecieReleasesRequest extends Request {

    public GetGenomeSpecieReleasesRequest(String specie) {
        super("getGenomeSpecieReleases", "/genomeRelease/" + specie.trim(), "GET");
    }
}
