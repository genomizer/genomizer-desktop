package util;

public class AnnotationDataTypes extends AnnotationData{

	private String[] values;
	private boolean forced;

	public AnnotationDataTypes(String id, String name, String[] values, Boolean forced) {
		super(id, name);
		this.values = values;
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

	public String[] getValues() {
		return values;
	}

	public boolean isForced() {
		return forced;
	}

	public boolean getForced() {
		return forced;
	}
}
