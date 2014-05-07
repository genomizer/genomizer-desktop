package util;

public class AnnotationDataValue extends AnnotationData {

	public String value;
	
	public AnnotationDataValue(String id, String name, String value) {
		super(id, name);
		this.value = value;
	}
}
