package requests;

/**
 * Created by worfox on 2014-04-25.
 *
 */

public class AddFileToExperiment extends Request {
	public String experimentId;
	public String fileName;
	public String type;
	private String metaData;
	private String author;
	private String uploader;
	private boolean isPrivate;
	private String grVersion;

	public AddFileToExperiment(String experimentId, String fileName,
			String type, String metaData, String author, String uploader, boolean isPrivate, String grVersion) {
		super("addfile", "/file/" + fileName, "POST");
		this.experimentId = experimentId;
		this.fileName = fileName;
		this.type = type;
		this.metaData = metaData;
		this.author = author;
		this.uploader = uploader;
		this.isPrivate = isPrivate;
		this.grVersion = grVersion;
	}

}
