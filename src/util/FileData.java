package util;

public class FileData {
    public String id;
    public String type;
    public String filename;
    public String uploadedBy;
    public String date;
    public String size;
    public String path;
    public String url;

    public FileData(String id, String type, String name, String uploadedBy,
	    String date, String size, String path, String URL) {
	this.id = id;
	this.type = type;
	this.filename = name;
	this.uploadedBy = uploadedBy;
	this.date = date;
	this.size = size;
        this.path = path;
	this.url = URL;
    }

    public String getName() {
	return filename;
    }

    public boolean equals(Object o) {
	return (((FileData) o)).id.equals(id);
    }
}
