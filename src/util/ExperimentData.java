package util;

import java.util.ArrayList;
import java.util.Random;

import com.google.gson.Gson;

/**
 * Class representing experiment data
 */
public class ExperimentData {

    public String name;
    public ArrayList<FileData> files;
    public ArrayList<AnnotationDataValue> annotations;
    public ExperimentData() {

    }

    public ExperimentData(String name, ArrayList<FileData> files,
            ArrayList<AnnotationDataValue> annotations) {
        this.name = name;
        this.files = files;
        this.annotations = annotations;
        testFileSioze();
    }

    public String getName() {
        return name;
    }

    /**
     * Returns the list of annotations associated with the project (including
     * experiment name and creator of experiment
     *
     * @return
     */
    public ArrayList<String> getAnnotationValueList(ArrayList<String> headings) {
        ArrayList<String> annotationList = new ArrayList<String>();

        for (String heading : headings) {
            boolean hasValue = false;
            if (heading.equals("ExpID")) {
                if (name == null || name.equals("")) {
                    annotationList.add("-");
                } else {
                    annotationList.add(name);
                }
                hasValue = true;
            } else if (heading.equals("")) {
                annotationList.add("-");
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


    public void convertToUTF8() {
        if (this.name != null && !this.name.isEmpty()) {
            this.name = UTF8Converter.convertFromUTF8(name);
        }
        for (AnnotationDataValue value : annotations) {
            if (value.name != null && !value.name.isEmpty()) {
                value.name = UTF8Converter.convertFromUTF8(value.name);
            }
            if (value.value != null && !value.value.isEmpty()) {
                value.value = UTF8Converter.convertFromUTF8(value.value);
            }
        }
        for (FileData data : files) {
            if (data.filename != null && !data.filename.isEmpty()) {
                data.filename = UTF8Converter.convertFromUTF8(data.filename);
            }
            if (data.author != null && !data.author.isEmpty()) {
                data.author = UTF8Converter.convertFromUTF8(data.author);
            }
            if (data.uploader != null && !data.uploader.isEmpty()) {
                data.uploader = UTF8Converter.convertFromUTF8(data.uploader);
            }
        }
    }

    /**
     * Check equality on name field
     */
    @Override
    public boolean equals(Object o) {
        return (((ExperimentData) o)).name.equals(name);
    }

    //TODO: Testmetod som skall tas bort n�r filstorlek kan h�mtas fr�n json

    public void testFileSioze() {
        for (int i = 0; i < files.size(); i++) {
            files.get(i).fileSize = FileSizeFormatter.convertByteToString(547);
        }
    }

    /**
     * Check hash on name field
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public ArrayList<AnnotationDataValue> getAnnotations() {
        return annotations;
    }
}
