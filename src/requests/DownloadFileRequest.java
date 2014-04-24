package requests;

public class DownloadFileRequest extends Request {
    public String fileName;
    public String fileFormat;

    public DownloadFileRequest(String fileName, String fileFormat) {
	super("downloadfile");
	this.fileName = fileName;
	this.fileFormat = fileFormat;
    }

}
