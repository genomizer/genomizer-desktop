package requests;


public class SearchRequest extends Request {

    public SearchRequest(String annotationString) {
	super("search", ("/search/annotations=?" + annotationString), "GET");
    }

}
