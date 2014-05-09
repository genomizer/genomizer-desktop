package requests;

import java.util.HashMap;

import util.AnnotationDataType;

public class AddExperimentRequest extends Request {
	public String experimentName;
	public String createdBy;
	public AnnotationDataType[] annotations;

	public AddExperimentRequest(String expirementName,
			String createdBy, AnnotationDataType[] annotations) {
		super("addexperiment", "/experiment", "POST");
		this.experimentName = experimentName;
		this.createdBy = createdBy;
		this.annotations = annotations;
	}
}
