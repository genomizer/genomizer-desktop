package requests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class Request {
    @Expose
    public String requestName;
    public String url;
    public String type;

    public Request(String requestName, String url, String type) {
	this.requestName = requestName;
	this.url = url;
	this.type = type;
    }

    public String toJson() {
	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
		.create();
	return gson.toJson(this);
    }

}
