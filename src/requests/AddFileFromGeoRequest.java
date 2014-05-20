package requests;

/**
 * This class contains info for adding files to an experiment from the geo database
 * 
 * @date 14-05-20
 * 
 *
 */
public class AddFileFromGeoRequest extends Request {
    
    private String experimentID;
    private String fileName;
    private String type;
    private String author;
    private String uploader;
    private boolean isPrivate;
    private String grVersion;
    private String url;
    
    public AddFileFromGeoRequest(String experimentID, String filename, String type,
            String author, String uploader, boolean isPrivate, String grVersion, String url) {
        super("addgeofile", "/geo", "POST");
        this.experimentID = experimentID;
        this.fileName = filename;
        this.type = type;
        this.author = author;
        this.uploader = uploader;
        this.isPrivate = isPrivate;
        this.grVersion = grVersion;
        this.url = url;
        
    }
    
}
