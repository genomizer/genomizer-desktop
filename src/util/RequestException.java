package util;

public class RequestException extends Exception {

    private static final long serialVersionUID = -5331379710023333449L;
    private int responseCode;
    private String responseBody;

    public RequestException(int responseCode, String responseBody) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

}
