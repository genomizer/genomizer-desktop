package responses;

import com.google.gson.annotations.Expose;

public class LoginResponse extends Response {

   
    public String token;
    
    public String role;

    public LoginResponse(String UserID,String role) {
        super("login");
        this.token = UserID;
        this.role = role;
    }
}
