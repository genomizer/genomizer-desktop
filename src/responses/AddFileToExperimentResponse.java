package responses;

import com.google.gson.annotations.Expose;

public class AddFileToExperimentResponse extends Response {
    @Expose
    public String URLupload;

    public AddFileToExperimentResponse(String responseName, String wwwTunnelPath) {
        super(responseName);
        this.URLupload = wwwTunnelPath;
    }

}
