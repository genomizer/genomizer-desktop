package responses.sysadmin;

public class AnnotationData {
	
	String id;
	String name;
	String[] value;
	boolean forced;
	
	public AnnotationData (String id, String name, String[] value, Boolean forced) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.forced = forced;
	}
	
	@Override
	public String toString(){
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
