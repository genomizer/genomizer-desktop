package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import com.google.gson.Gson;

/**
 * Class representing experiment data
 */
public class ExperimentData {

    public String name;
    public String createdBy;
    public FileData[] files;
    public AnnotationDataValue[] annotations;

    public ExperimentData(String name, String createdBy, FileData[] files,
	    AnnotationDataValue[] annotations) {
	this.name = name;
	this.createdBy = createdBy;
	this.files = files;
	this.annotations = annotations;
    }

    /**
     * Returns the list of annotations associated with the project
     * (including experiment name and creator of experiment
     * @return
     */
    public String[] getAnnotationValueList() {
	String[] annotationList = new String[2 + annotations.length];
	annotationList[0] = name;
	annotationList[1] = createdBy;
	for (int i = 0; i < annotations.length; i++) {
	    annotationList[2 + i] = annotations[i].value;
	}
	return annotationList;
    }

    /**
     * Add new files to the experiment
     * @param newFiles
     */
    public void addFiles(FileData[] newFiles) {
	ArrayList<FileData> both = new ArrayList<FileData>(files.length
		+ newFiles.length);
	Collections.addAll(both, files);
	Collections.addAll(both, newFiles);
	HashSet hs = new HashSet();
	hs.addAll(both);
	both.clear();
	both.addAll(hs);
	files = both.toArray(new FileData[both.size()]);
    }

    /**
     * Remove a files from the experiment
     * @param fileData
     */
    public void removeFile(FileData fileData) {
	if (files.length > 0) {
	    ArrayList<FileData> list = new ArrayList<FileData>(
		    Arrays.asList(files));
	    list.removeAll(Arrays.asList(fileData));
	    files = list.toArray(new FileData[files.length - 1]);
	}
    }

    public static ExperimentData[] getExample() {

	String[] names = { "Kalle", "Pelle", "Anna", "Nils", "Olle" };
	String[] dates = { "2012-04-30", "2013-02-02", "2008-02-20",
		"2014-12-24", "2012-12-12" };
	String[] species = { "Human", "Rat", "Fly", "Unknown", "Cat" };
	String[] fileSizes = { "12GB", "1GB", "3GB", "100MB", "10GB" };
	String[] fileTypes = { "raw", "profile", "region" };
	String[] sexTypes = { "Male", "Female", "Unknown" };
	Random rand = new Random();
	Gson gson = new Gson();

	ExperimentData[] searchResponses = new ExperimentData[20];
	for (int i = 0; i < 20; i++) {
	    FileData[] fileData = new FileData[5];
	    for (int j = 0; j < 5; j++) {
		String fileType = fileTypes[rand.nextInt(3)];
		/*fileData[j] = new FileData("" + i + j, fileType, "exp" + i
			+ "_" + fileType + "file" + j, names[rand.nextInt(5)],
			dates[rand.nextInt(5)], fileSizes[rand.nextInt(5)], "-");*/
	    }
	    AnnotationDataValue[] annotationData = new AnnotationDataValue[5];
	    annotationData[0] = new AnnotationDataValue("2", "Species",
		    species[rand.nextInt(5)]);
	    annotationData[1] = new AnnotationDataValue("2", "Sex",
		    sexTypes[rand.nextInt(3)]);
	    annotationData[2] = new AnnotationDataValue("2", "State", "Larva");
	    annotationData[3] = new AnnotationDataValue("2", "Annotationx", "x");
	    annotationData[4] = new AnnotationDataValue("2", "Annotationy", "y");
	    searchResponses[i] = new ExperimentData("Experiment" + i,
		    names[rand.nextInt(5)], fileData, annotationData);

	}

	return searchResponses;

    }

    public boolean equals(Object o) {
	return (((ExperimentData) o)).name.equals(name);
    }

}
