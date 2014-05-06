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
	
	public String[] getAnnotationValueList() {
		String[] annotationList = new String[2 +annotations.length];
		annotationList[0] = name;
		annotationList[1] = createdBy;
		for(int i=0; i<annotations.length; i++) {
			annotationList[2+i] = annotations[i].value;
		}
		return annotationList;
	}
	
	public String[] getFileListString() {
		String[] fileList = new String[files.length];
		for(int i=0; i<files.length; i++) {
			fileList[i] = files[i].name + "." + files[i].type;
		}
		return fileList;
	}

}
