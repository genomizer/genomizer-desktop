package util;

public class LoginException extends Exception {

    private static final long serialVersionUID = -6903252869963607909L;
    private String message;

    public LoginException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
