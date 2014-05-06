package requests;

public class rawToProfileRequest extends Request {

	public rawToProfileRequest(String fileName) {
		super("rawtoprofile", "/process/rawtoprofile/" + fileName, "PUT");
	}

}
