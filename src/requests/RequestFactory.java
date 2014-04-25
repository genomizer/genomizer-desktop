package requests;

import java.util.HashMap;

public class RequestFactory {

    public RequestFactory() {
    }

    public LoginRequest makeLoginRequest(String username, String password) {
	return new LoginRequest(username, password);
    }

    public DownloadFileRequest makeDownloadFileRequest(String fileName,
	    String fileFormat, String uID) {
	return new DownloadFileRequest(fileName, fileFormat, uID);
    }

    public ChangeAnnotationRequest makeChangeAnnotationRequest(String id,
	    HashMap annotations, String uID) {
	return new ChangeAnnotationRequest(id, annotations, uID);
    }
}
