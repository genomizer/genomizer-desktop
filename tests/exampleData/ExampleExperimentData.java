package exampleData;

import java.util.ArrayList;
import java.util.Random;

import util.AnnotationDataValue;
import util.ExperimentData;
import util.FileData;

/**
 * Extracted example ExperimentData from ExperimentData class.
 * @author c12oor
 *
 */
public final class ExampleExperimentData {


    public static ExperimentData[] getExample() {

        String[] names = { "Kalle", "Pelle", "Anna", "Nils", "Olle" };
        String[] dates = { "2012-04-30", "2013-02-02", "2008-02-20",
                "2014-12-24", "2012-12-12" };
        String[] species = { "Human", "Rat", "Fly", "Unknown", "Cat" };
        String[] fileTypes = { "Raw", "Profile", "Region" };
        String[] sexTypes = { "Male", "Female", "Unknown" };
        Random rand = new Random();

        ExperimentData[] searchResponses = new ExperimentData[20];
        for (int i = 0; i < 20; i++) {
            ArrayList<FileData> fileData = new ArrayList<FileData>();
            for (int j = 0; j < 5; j++) {
                String fileType = fileTypes[rand.nextInt(3)];
                fileData.add(new FileData("" + i + j, "Experiment" + i,
                        fileType, "", names[rand.nextInt(5)], names[rand
                                .nextInt(5)], false, "",
                        dates[rand.nextInt(5)], "", "", "Exp" + i + "_file" + j,"1000"));
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
            searchResponses[i] = new ExperimentData("Experiment" + i, fileData,
                    annotationData);

        }

        return searchResponses;

    }
    public static String getTestServerIP() {
        return "130.239.192.110:4434/api";
    }
    public static String getTestUsername() {
        return "testuser";
    }
    public static String getTestPassword() {
        return "baguette";
    }

}
