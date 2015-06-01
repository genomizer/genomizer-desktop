package requests;

public class FileConversionRequest extends Request {
    public String fileid;
    public String toformat;

    public FileConversionRequest(String fileid, String toformat) {
        super("convertfile", "/convertfile", "PUT");
        this.fileid = fileid;
        this.toformat = toformat;
    }

}
