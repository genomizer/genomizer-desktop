package requests;

/**
 * Created by worfox on 2014-04-25.
 *
 */

public class AddFileToExperiment extends Request {
	public String experimentId;
	public String fileName;
	public String size;
	public String type;

	public AddFileToExperiment(String experimentId, String fileName,
			String size, String type) {
		super("addfile", "/file/" + fileName, "POST");
		this.experimentId = experimentId;
		this.fileName = fileName;
		this.size = size;
		this.type = type;

	}

}
