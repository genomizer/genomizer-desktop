package responses;

public class NewExperimentResponse extends Response {
    public String experimentID;
    
    public NewExperimentResponse(String responseName, String ID) {
        super(responseName);
        this.experimentID = ID;
    }
    
}
