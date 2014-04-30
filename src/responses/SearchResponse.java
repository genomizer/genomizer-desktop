package responses;

import com.google.gson.Gson;

public class SearchResponse extends Response {

    public String name;
    public String createdBy;
    public FileData[] files;
    public AnnotationData[] annotations;

    public SearchResponse(String name, String createdBy, FileData[] files,
	    AnnotationData[] annotations) {
	super("search");
	this.name = name;
	this.createdBy = createdBy;
	this.files = files;
	this.annotations = annotations;

    }

    public String toString() {
	return name + " " + createdBy + " " + files;
    }

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

    public class AnnotationData {
	public String id;
	public String name;
	public String value;

	public AnnotationData(String id, String name, String value) {
	    this.id = id;
	    this.name = name;
	    this.value = value;
	}
    }

    public static void main(String args[]) {
	SearchResponse response = new SearchResponse("s", "df", null, null);
	Gson gson = new Gson();
	AnnotationData[] annotationData = new AnnotationData[1];
	for (int i = 0; i < 1; i++) {
	    annotationData[i] = response.new AnnotationData("1", "Species",
		    "Human");
	}
	FileData[] fileData = new FileData[1];
	for (int i = 0; i < 1; i++) {
	    fileData[i] = response.new FileData("2", "wig", "file", "kalle",
		    "12-12-12", "12GB", "///");
	}

	SearchResponse[] searchResponses = new SearchResponse[3];
	for (int i = 0; i < 3; i++) {
	    searchResponses[i] = new SearchResponse("Experiment" + i, "Kalle",
		    fileData, annotationData);

	}
	String json = gson.toJson(searchResponses);
	System.out.println(json);

	SearchResponse[] responses2 = gson.fromJson(json,
		SearchResponse[].class);
	for (int i = 0; i < responses2.length; i++) {
	    System.out.println(responses2[i]);
	}

    }

}