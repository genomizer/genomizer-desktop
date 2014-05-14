package requests;

import util.AnnotationDataValue;

public class AddExperimentRequest extends Request {
    public String                name;
    // @SerializedName("created by")
    public String                createdBy;
    public AnnotationDataValue[] annotations;
    
    public AddExperimentRequest(String experimentName, String createdBy,
            AnnotationDataValue[] annotations) {
        super("addexperiment", "/experiment", "POST");
        this.name = experimentName;
        this.createdBy = createdBy;
        this.annotations = annotations;
    }
}
