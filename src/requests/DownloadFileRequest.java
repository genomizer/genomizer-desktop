package requests;

public class DownloadFileRequest extends Request {

    public DownloadFileRequest(String fileID, String fileFormat, String uID) {
	super("downloadfile", "/file/" + "<file-id>" + "/" + "{user-id}" , "GET");
    }

}
