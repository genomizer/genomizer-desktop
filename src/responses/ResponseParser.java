package responses;

import com.google.gson.Gson;

public class ResponseParser {

	private static Gson gson = new Gson();

	public static LoginResponse parseLoginResponse(String json) {
		LoginResponse loginResponse = gson.fromJson(json, LoginResponse.class);
		return loginResponse;
	}

}
