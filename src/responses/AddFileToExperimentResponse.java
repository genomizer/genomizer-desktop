package responses;

public class AddFileToExperimentResponse extends Response {
    public String uploadUrl;
    
    public AddFileToExperimentResponse(String responseName, String uploadUrl) {
	super(responseName);
	this.uploadUrl = uploadUrl;
    }

}
