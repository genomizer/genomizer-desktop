package requests;

/**
 * Created by worfox on 2014-04-25.
 * 
 */

public class AddFileToExperiment extends Request {
    public String experimentID;
    public String fileName;
    public String type;
    public String metaData;
    public String author;
    public String uploader;
    public boolean isPrivate;
    public String grVersion;
    
    public AddFileToExperiment(String experimentId, String fileName,
            String type, String metaData, String author, String uploader,
            boolean isPrivate, String grVersion) {
        super("addfile", "/file", "POST");
        this.experimentID = experimentId;
        this.fileName = fileName;
        this.type = type;
        this.metaData = metaData;
        this.author = author;
        this.uploader = uploader;
        this.isPrivate = isPrivate;
        this.grVersion = grVersion;
    }
    
}
