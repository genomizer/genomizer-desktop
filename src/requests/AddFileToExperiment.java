package requests;

import com.google.gson.annotations.Expose;

/**
 * Created by worfox on 2014-04-25.
 *
 */

public class AddFileToExperiment {
    public String experimentId;
    public String fileName;
    public String size;
    public String type;

    public AddFileToExperiment(String experimentId, String fileName,
                               String size, String type) {
        this.experimentId = experimentId;
        this.fileName = fileName;
        this.size = size;
        this.type = type;

    }
}
