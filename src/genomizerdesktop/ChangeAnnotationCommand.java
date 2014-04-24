package genomizerdesktop;

import java.util.HashMap;

public class ChangeAnnotationCommand {
	private String id;
	private HashMap<String, String> annotations = new HashMap<String, String>();
	public ChangeAnnotationCommand(String id, HashMap annotations) {
		this.id = id;
		this.annotations = annotations;
	}
}
