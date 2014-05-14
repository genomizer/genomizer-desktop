package responses;

public class AddFileToExperimentResponse extends Response {
    public String URLupload;
    
    public AddFileToExperimentResponse(String responseName, String uploadUrl) {
        super(responseName);
        this.URLupload = uploadUrl;
    }
    
}
