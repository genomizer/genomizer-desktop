package util;


public class ExperimentData {

	public String name;
	public String createdBy;
	public FileData[] files;
	public AnnotationData[] annotations;

	public ExperimentData(String name, String createdBy, FileData[] files,
			AnnotationData[] annotations) {
		this.name = name;
		this.createdBy = createdBy;
		this.files = files;
		this.annotations = annotations;
	}

}
