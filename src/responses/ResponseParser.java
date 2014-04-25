package responses;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class ResponseParser {

    private static Gson gson = new Gson();

    public static LoginResponse parseLoginResponse(String json) {
	LoginResponse loginResponse = null;
	try {
	    loginResponse = gson.fromJson(json, LoginResponse.class);
	} catch (JsonParseException e) {
	    return null;
	}
	return loginResponse;
    }

    private static String getHttpPart(String response) {
	return "http";
    }

    private static String getJsonPart(String response) {
	return "json";
    }

}
