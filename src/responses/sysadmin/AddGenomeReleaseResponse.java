package responses.sysadmin;

import responses.Response;

public class AddGenomeReleaseResponse extends Response {

    public String urlUpload;

    public AddGenomeReleaseResponse(String responseName, String URLupload) {
        super(responseName);
        this.urlUpload = URLupload;

    }

}
