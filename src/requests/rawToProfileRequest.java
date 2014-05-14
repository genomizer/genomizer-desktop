package requests;

public class rawToProfileRequest extends Request {

    public String filename;
    public String fileId;
    public String expid;
    public String processtype;
    public String[] parameters;
    public String metadata;
    public String genomeRelease;
    public String author;

    /**
     * Creates the request for creating profile data from RAW-file.
     *
     * @param fileName
     * @param fileId
     * @param expid
     * @param processtype
     * @param parameters
     * @param metadata
     * @param genomeRelease
     * @param author
     */
    public rawToProfileRequest(String filename, String fileId, String expid,
            String processtype, String[] parameters, String metadata,
            String genomeRelease, String author) {

        super("rawtoprofile", "/process", "PUT");
        this.filename = filename;
        this.fileId = fileId;
        this.expid = expid;
        this.processtype = processtype;
        this.parameters = parameters;
        this.metadata = metadata;
        this.genomeRelease = genomeRelease;
        this.author = author;

    }

}
