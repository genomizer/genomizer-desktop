package util;

import java.util.ArrayList;
import java.util.Random;

import com.google.gson.Gson;

/**
 * Class representing experiment data
 */
public class ExperimentData {
    
    public String name;
    public String createdBy;
    public ArrayList<FileData> files;
    public ArrayList<AnnotationDataValue> annotations;
    
    public ExperimentData(String name, String createdBy,
            ArrayList<FileData> files,
            ArrayList<AnnotationDataValue> annotations) {
        this.name = name;
        this.createdBy = createdBy;
        this.files = files;
        this.annotations = annotations;
    }
    
    /**
     * Returns the list of annotations associated with the project (including
     * experiment name and creator of experiment
     * 
     * @return
     */
    public ArrayList<String> getAnnotationValueList(ArrayList<String> headings) {
        ArrayList<String> annotationList = new ArrayList<String>();
        // annotationList.add(name);
        // annotationList.add(createdBy);
        
        for (String heading : headings) {
            boolean hasValue = false;
            if (heading.equals("ExpID")) {
                if (name == null || name.equals("")) {
                    annotationList.add("-");
                } else {
                    annotationList.add(name);
                }
                hasValue = true;
            } else if (heading.equals("Experiment Created By")) {
                if (createdBy == null || createdBy.equals("")) {
                    annotationList.add("-");
                } else {
                    annotationList.add(createdBy);
                }
                hasValue = true;
            } else {
                for (AnnotationDataValue annotation : annotations) {
                    if (heading.equals(annotation.name)) {
                        if (annotation.value == null
                                || annotation.value.equals("")) {
                            annotationList.add("-");
                        } else {
                            annotationList.add(annotation.value);
                        }
                        hasValue = true;
                    }
                }
            }
            
            if (!hasValue) {
                annotationList.add("-");
            }
        }
        return annotationList;
    }
    
    /**
     * Add new files to the experiment
     * 
     * @param newFiles
     */
    public void addFiles(ArrayList<FileData> newFiles) {
        for (FileData newFile : newFiles) {
            if (!files.contains(newFile)) {
                files.add(newFile);
            }
        }
    }
    
    /**
     * Remove a files from the experiment
     * 
     * @param fileData
     */
    public void removeFile(FileData fileData) {
        files.remove(fileData);
    }
    
    public static ExperimentData[] getExample() {
        
        String[] names = { "Kalle", "Pelle", "Anna", "Nils", "Olle" };
        String[] dates = { "2012-04-30", "2013-02-02", "2008-02-20",
                "2014-12-24", "2012-12-12" };
        String[] species = { "Human", "Rat", "Fly", "Unknown", "Cat" };
        String[] fileSizes = { "12GB", "1GB", "3GB", "100MB", "10GB" };
        String[] fileTypes = { "Raw", "Profile", "Region" };
        String[] sexTypes = { "Male", "Female", "Unknown" };
        Random rand = new Random();
        Gson gson = new Gson();
        
        ExperimentData[] searchResponses = new ExperimentData[20];
        for (int i = 0; i < 20; i++) {
            ArrayList<FileData> fileData = new ArrayList<FileData>();
            for (int j = 0; j < 5; j++) {
                String fileType = fileTypes[rand.nextInt(3)];
                fileData.add(new FileData("" + i + j, "Experiment" + i,
                        fileType, "", names[rand.nextInt(5)], names[rand
                                .nextInt(5)], false, "",
                        dates[rand.nextInt(5)], "", "", "Exp" + i + "_file" + j));
            }
            ArrayList<AnnotationDataValue> annotationData = new ArrayList<AnnotationDataValue>();
            annotationData.add(new AnnotationDataValue("2", "Species",
                    species[rand.nextInt(5)]));
            annotationData.add(new AnnotationDataValue("2", "Sex",
                    sexTypes[rand.nextInt(3)]));
            annotationData.add(new AnnotationDataValue("2", "State", "Larva"));
            annotationData
                    .add(new AnnotationDataValue("2", "Annotationx", "x"));
            annotationData
                    .add(new AnnotationDataValue("2", "Annotationy", "y"));
            searchResponses[i] = new ExperimentData("Experiment" + i,
                    names[rand.nextInt(5)], fileData, annotationData);
            
        }
        
        return searchResponses;
        
    }
    
    public boolean equals(Object o) {
        return (((ExperimentData) o)).name.equals(name);
    }
    
}
