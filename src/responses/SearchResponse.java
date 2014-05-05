package responses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

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

	public ArrayList<LinkedHashMap<String, String>> getFileList() {
		ArrayList<LinkedHashMap<String, String>> list
			= new ArrayList<LinkedHashMap<String, String>>();
		for (int i = 0; i < files.length; i++) {
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("Experiment Name", this.name);
			map.put("Experiment Created By", createdBy);
			map.put("File Name", files[i].name + "." + files[i].type);
			map.put("File Size", files[i].size);
			map.put("File Uploaded By", files[i].uploadedBy);
			map.put("File Upload Date", files[i].date);
			//map.put("URL", files[i].URL);
			for (int j = 0; j < annotations.length; j++) {
				map.put(annotations[j].name, annotations[j].value);
			}
			list.add(map);
		}
		return list;
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

	public static String getJsonExampleTest() {

		String[] names = {"Kalle", "Pelle", "Big Boss", "Nils", "SwagMaster"};
		String[] dates = {"2012-04-30", "1764-02-02", "2008-02-20", "2014-12-24", "2012-12-12"};
		String[] species = {"Human", "Rat", "Xenomorph", "Unknown", "Cat"};
		String[] fileNames = {"Protein123_A5_2014", "Protein120_A1_2014"
				, "Protein65_A1_2012", "Protein1_A0_2014", "Protein200_A6_2003"};
		String[] fileSizes= {"12GB", "1GB", "3GB", "100MB", "10GB"};
		String[] fileTypes= {"wig", "raw", "gff", "fastq", "sgr"};
		Random rand = new Random();
		SearchResponse response = new SearchResponse("s", "df", null, null);
		Gson gson = new Gson();


		SearchResponse[] searchResponses = new SearchResponse[10];
		for (int i = 0; i < 10; i++) {
			FileData[] fileData = new FileData[5];
			for (int j = 0; j < 5; j++) {
				fileData[j] = response.new FileData("2", fileTypes[rand.nextInt(5)], "exp" + i +"_file"+j
						, names[rand.nextInt(5)],
							dates[rand.nextInt(5)], fileSizes[rand.nextInt(5)], "-");
			}
			AnnotationData[] annotationData = new AnnotationData[5];
			annotationData[0] = response.new AnnotationData("2", "Species",
					species[rand.nextInt(5)]);
			annotationData[1] = response.new AnnotationData("2", "Species2",
					species[rand.nextInt(5)]);
			annotationData[2] = response.new AnnotationData("2", "Species3",
					species[rand.nextInt(5)]);
			annotationData[3] = response.new AnnotationData("2", "Species4",
					species[rand.nextInt(5)]);
			annotationData[4] = response.new AnnotationData("2", "Species5",
					species[rand.nextInt(5)]);
			searchResponses[i] = new SearchResponse("Experiment" + i, names[rand.nextInt(5)],
					fileData, annotationData);

		}
		String json = gson.toJson(searchResponses);
		return json;

	}

}
