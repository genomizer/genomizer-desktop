package requests;

public class rawToProfileRequest extends Request {

	public String fileName;
	public String filePath;
	public String expid;
	public String[] parameters;
	public String metadata;
	public String genomeRelease;
	public String author;

	public rawToProfileRequest(String fileName,String filePath,String expid,String metadata, String genomeRelease,String author,String[] parameters) {

		super("rawtoprofile", "/process/rawtoprofile", "PUT");
		this.fileName = fileName;
		this.filePath = filePath;
		this.expid = expid;
		this.metadata = metadata;
		this.genomeRelease = genomeRelease;
		this.author = author;
		this.parameters = parameters;

	}

}
