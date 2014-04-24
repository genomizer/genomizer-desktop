package requests;

import com.google.gson.Gson;

public class Request {

    public String requestName;

    public Request(String requestName) {
	this.requestName = requestName;
    }

    public String toJson() {
	Gson gson = new Gson();
	return gson.toJson(this);
    }

}
