package requests;

public class DeleteUserRequest extends Request {

    public DeleteUserRequest(String User) {
        super("deleteuser", "/admin/user/" + User, "DELETE");
    }

}
