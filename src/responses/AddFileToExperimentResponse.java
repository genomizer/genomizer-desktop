package responses;

import com.google.gson.annotations.Expose;

public class AddFileToExperimentResponse extends Response {
    @Expose
    public String URLupload;

    public AddFileToExperimentResponse(String responseName, String uploadUrl) {
        super(responseName);
        this.URLupload = uploadUrl;
    }

}
