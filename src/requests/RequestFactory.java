package requests;

import java.util.HashMap;

public class RequestFactory {

    public RequestFactory() {
    }

    public LoginRequest makeLoginRequest(String username, String password) {
	return new LoginRequest(username, password);
    }

    public DownloadFileRequest makeDownloadFileRequest(String fileName,
	    String fileFormat) {
	return new DownloadFileRequest(fileName, fileFormat);
    }

    public ChangeAnnotationRequest makeChangeAnnotationRequest(String id,
	    HashMap annotations) {
	return new ChangeAnnotationRequest(id, annotations);
    }
}