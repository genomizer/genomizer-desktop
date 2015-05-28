package requests;

public class UpdateUserRequest extends Request {
    public String username;
    public String password;
    public String privileges;
    public String name;
    public String email;

    public UpdateUserRequest(String username,
            String password, String privileges, String name, String email) {
        super("updateuser", "/admin/user", "PUT");
        this.username = username;
        this.password = password;
        this.privileges = privileges;
        this.name = name;
        this.email = email;
    }
}
