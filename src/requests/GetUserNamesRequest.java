package requests;

public class GetUserNamesRequest extends Request {

    public GetUserNamesRequest() {
        super("getuserlist", "/admin/user", "GET");
    }
}
