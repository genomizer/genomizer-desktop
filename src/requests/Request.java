package requests;

import com.google.gson.Gson;

public class Request {
    public transient String requestName;
    public transient String url;
    public transient String type;

    public Request(String requestName, String url, String type) {
        this.requestName = requestName;
        this.url = url;
        this.type = type;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
