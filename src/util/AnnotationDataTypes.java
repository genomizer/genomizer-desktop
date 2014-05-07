package util;

public class AnnotationDataTypes extends AnnotationData{

	private String[] value;
	private boolean forced;

	public AnnotationDataTypes(String id, String name, String[] value, Boolean forced) {
		super(id, name);
		this.value = value;
		this.forced = forced;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String[] getValue() {
		return value;
	}

	public boolean isForced() {
		return forced;
	}
}
