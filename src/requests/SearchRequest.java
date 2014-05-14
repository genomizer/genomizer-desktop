package requests;

public class SearchRequest extends Request {

    public SearchRequest(String pubmedString) {
        super("search", ("/search/?annotations=" + pubmedString), "GET");
        System.out.println("Search url: " + this.url);
    }

}
