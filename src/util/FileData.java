package util;

public class FileData {
    public String id;
    public String expId;
    public String type;
    public String metaData;
    public String author;
    public String filename;
    public String uploader;
    public boolean isPrivate;
    public String grVersion;
    public String date;
    public String path;
    public String url;

    public FileData(String fileId, String experimentID, String type,
	    String metaData, String author, String uploader, boolean isPrivate,
	    String grVersion, String date, String path, String url,
	    String fileName) {
	this.id = fileId;
	this.expId = experimentID;
	this.type = type;
	this.metaData = metaData;
	this.author = author;
	this.isPrivate = isPrivate;
	this.grVersion = grVersion;
	this.uploader = uploader;
	this.date = date;
	this.path = path;
	this.url = url;
	this.filename = fileName;
    }

    public String getName() {
	return filename;
    }

    public boolean equals(Object o) {
	return (((FileData) o)).id.equals(id);
    }
}
