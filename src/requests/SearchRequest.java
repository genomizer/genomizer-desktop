package requests;

public class SearchRequest extends Request {

    public SearchRequest(String pubmedString) {
	super("search", ("/search/annotations=?" + pubmedString), "GET");
    }

}
