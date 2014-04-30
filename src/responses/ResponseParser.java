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

    public static SearchResponse[] parseSearchResponse(String json) {
	SearchResponse[] searchResponses = null;
	try {
	    searchResponses = gson.fromJson(json, SearchResponse[].class);
	} catch (JsonParseException e) {
	    return null;
	}
	return searchResponses;
    }

}
