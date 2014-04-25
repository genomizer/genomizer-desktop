package responses;

import com.google.gson.Gson;

public class ResponseParser {

    private static Gson gson = new Gson();

    public static LoginResponse parseLoginResponse(String response) {
	String http = getHttpPart(response);
	// check http
	String json = getJsonPart(response);
	LoginResponse loginResponse = gson.fromJson(json, LoginResponse.class);
	return loginResponse;
    }

    private static String getHttpPart(String response) {
	return "";
    }

    private static String getJsonPart(String response) {
	return "";
    }

}
