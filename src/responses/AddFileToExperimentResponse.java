package responses;

import com.google.gson.annotations.Expose;

public class AddFileToExperimentResponse extends Response {
    @Expose
    public String UrlUpload;

    public AddFileToExperimentResponse(String responseName, String uploadUrl) {
        super(responseName);
        this.UrlUpload = uploadUrl;
    }

}
