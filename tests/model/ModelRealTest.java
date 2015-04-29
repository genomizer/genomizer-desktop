package model;

import org.junit.Before;
import org.junit.Test;

import exampleData.ExampleExperimentData;
import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.FileData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by dv12csr on 5/26/14.
 */
public class ModelRealTest {

    String username = ExampleExperimentData.getTestUsername();
    String password = ExampleExperimentData.getTestPassword();
    Model m = new Model();

    @Before
    public void setUp() {
        m.setIp(ExampleExperimentData.getTestServerIP());
        m.loginUser(username, password);
    }

    @Test
    public void shouldUploadExperimentAndRemove() {
        String expid = "thisisatestexp476-";
        String strAp = Long.toString(System.currentTimeMillis() / 60);
        expid += strAp;
        String homeDir = System.getProperty("user.home");
        File file = new File(homeDir + "/.testupload.txt");
        BufferedWriter fout;
        try {
            fout = new BufferedWriter(new FileWriter(file));
            fout.write("test");
            fout.newLine();
            fout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnnotationDataType[] types = m.getAnnotations();
        AnnotationDataValue[] values = new AnnotationDataValue[types.length];
        for (int i = 0; i < types.length; i++) {
            if (types[i].getName().equalsIgnoreCase("ExpID")) {
                continue;
            }
            if (types[i].getName().equalsIgnoreCase("Species")) {
                values[i] = new AnnotationDataValue(Integer.toString(i), types[i].getName(), "Fly");
                continue;
            }
            if (!types[i].getValues()[0].equalsIgnoreCase("freetext")) {
                values[i] = new AnnotationDataValue(Integer.toString(i), types[i].getName(), types[i].getValues()[0]);
            } else {
                values[i] = new AnnotationDataValue(Integer.toString(i), types[i].getName(), "test");
            }
        }
        assertThat(m.search(expid + "[ExpID]")).isNull();
        assertThat(m.addNewExperiment(expid, values)).isTrue();
        assertThat(m.uploadFile(expid, file, "Profile", "genomizer", false, "fb5")).isTrue();
        ArrayList<ExperimentData> data = m.search(expid + "[ExpID]");
        assertThat(data).isNotNull();
        for (FileData fileData : data.get(0).files) {
            assertThat(m.deleteFileFromExperiment(fileData.id));
        }
        assertThat(m.deleteExperimentFromDatabase(expid)).isTrue();
        assertThat(m.search(expid + "[ExpID]")).isNull();
    }
}
