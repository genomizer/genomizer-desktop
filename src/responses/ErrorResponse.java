package responses;

public class ErrorResponse extends Response {
    
    public String message;
    
    public ErrorResponse() {
        super("error");
    }
    
}
