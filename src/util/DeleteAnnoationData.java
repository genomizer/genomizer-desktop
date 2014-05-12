package util;

public class DeleteAnnoationData {
	
	public String id;
	public String[] values;
	public String name;
	
	public DeleteAnnoationData(AnnotationDataType annotation) {
		this.id = annotation.id;
		this.name = annotation.name;
		this.values = new String[]{};
	}

}
