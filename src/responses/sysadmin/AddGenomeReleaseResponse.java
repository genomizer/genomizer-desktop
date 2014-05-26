package responses.sysadmin;

import responses.Response;

public class AddGenomeReleaseResponse extends Response {

    public String URLupload;

    public AddGenomeReleaseResponse(String responseName, String URLupload) {
        super(responseName);
        this.URLupload = URLupload;

    }

}
