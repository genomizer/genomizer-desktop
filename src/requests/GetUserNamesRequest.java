package requests;

public class GetUserNamesRequest extends Request {

    public GetUserNamesRequest() {
        super("getuserlist", "/admin/userlist", "GET");
    }
}
