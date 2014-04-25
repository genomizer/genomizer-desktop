package requests;

public class RequestFactory {

	public RequestFactory(){
	}

	public Request makeLoginRequest() {
		return new LoginRequest("Username", "Password");
	}

	public Request makeDownloadFileRequest() {
		return null;
	}
}
