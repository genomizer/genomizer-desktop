package requests;

import util.AnnotationDataValue;

public class AddExperimentRequest extends Request {
    public String experimentName;
    public String createdBy;
    public AnnotationDataValue[] annotations;

    public AddExperimentRequest(String expirementName, String createdBy,
	    AnnotationDataValue[] annotations) {
	super("addexperiment", "/experiment", "POST");
	this.experimentName = experimentName;
	this.createdBy = createdBy;
	this.annotations = annotations;
    }
}
