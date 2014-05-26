package genomizerdesktop.tests;

import static org.fest.assertions.api.Assertions.assertThat;
import model.GenomizerModel;
import model.Model;

import org.junit.Before;
import org.junit.Test;
import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ModelTest {
    
    Model m;
    String username = "desktoptest";
    String password = "umea@2014";
    @Before
    public void setUp() throws Exception {
        m = new Model();
        m.setIp("http://genomizer.apiary-mock.com");
    }
    
    @Test
    public void shouldImplementGenomizerView() throws Exception {
        assertThat(m).isInstanceOf(GenomizerModel.class);
    }

    @Test
    public void shouldLogin() throws Exception {
        assertThat(m.getUserID()).isEmpty();
        assertThat(m.loginUser(username, password)).isTrue();
        assertThat(m.getUserID()).isNotEmpty();
    }

    @Test
    public void shouldLogout() throws Exception {
        assertThat(m.loginUser(username, password)).isTrue();
        assertThat(m.getUserID()).isNotEmpty();
        assertThat(m.logoutUser()).isTrue();
        assertThat(m.getUserID()).isEmpty();
    }

    @Test
    public void shouldAddExperiment() throws Exception {
        AnnotationDataValue[] values = new AnnotationDataValue[1];
        values[0] = new AnnotationDataValue("test", "name", "val");
        assertThat(m.addNewExperiment("testexp", values)).isTrue();
    }

    @Test
    public void shouldUploadFile() throws Exception {
        assertThat(m.uploadFile("test", new File("test"), "type", "user", false, "fb5")).isTrue();
    }

    @Test
    public void shouldSearch() throws Exception {
        assertThat(m.search("exp1[ExpID]")).isNotNull();
    }

    @Test
    public void shouldGetUrlFromSearch() throws Exception {
        ArrayList<ExperimentData> data;
        data = m.search("exp1[ExpID]");
        assertThat(data.get(0).files.get(0).url).
                isEqualToIgnoringCase("http://scratchy.cs.umu.se:8000/download.php?" +
                        "path=/var/www/data/Exp1/raw/file1.fastq");
    }

    /*@Test
    public void shouldUploadExperimentAndRemove() {
        String expid = "thisisatestexp95996";
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
        Model m2 = new Model();
        m2.setIp("http://scratchy.cs.umu.se:7000");
        m2.loginUser(username, password);
        AnnotationDataType[] types = m2.getAnnotations();
        AnnotationDataValue[] values = new AnnotationDataValue[types.length];
        for(int i = 0; i < types.length; i++) {
            if(types[i].getName().equalsIgnoreCase("ExpID")) {
                continue;
            }
            if(!types[i].getValues()[0].equalsIgnoreCase("freetext")) {
                values[i] = new AnnotationDataValue(Integer.toString(i), types[i].getName(), types[i].getValues()[0]);
            } else {
                values[i] = new AnnotationDataValue(Integer.toString(i), types[i].getName(), "test");
            }
        }
        assertThat(m2.search(expid + "[ExpID]")).isNull();
        assertThat(m2.addNewExperiment(expid, "genomizer", values)).isTrue();
        assertThat(m2.uploadFile(expid, file, "Profile", "genomizer", false, "rn5")).isTrue();
        // SEARCH IS WRONG SOMEHOW
        assertThat(m2.search(expid + "[ExpID]")).isNotNull();
        assertThat(m2.deleteExperimentFromDatabase(expid)).isTrue();
        assertThat(m2.search(expid + "[ExpID]")).isNull();
    }*/
}
