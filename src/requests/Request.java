package requests;

import com.google.gson.Gson;

public class Request {
    //TODO Inte serialized, behöver inte transient CF
    public transient String requestName;
    public transient String url;
    public transient String requestType;

    public Request(String requestName, String url, String type) {
        this.requestName = requestName;
        this.url = url;
        this.requestType = type;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getRequestName() {
        return requestName;
    }

}
