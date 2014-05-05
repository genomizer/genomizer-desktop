package util;

public class FileData {

	public String id;
	public String type;
	public String name;
	public String uploadedBy;
	public String date;
	public String size;
	public String URL;

	public FileData(String id, String type, String name, String uploadedBy,
			String date, String size, String URL) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.uploadedBy = uploadedBy;
		this.date = date;
		this.size = size;
		this.URL = URL;
	}

}
