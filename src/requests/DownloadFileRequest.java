package requests;

/**
 * This class represents a "Download file" request in an application for genome
 * researchers. This request downloads the selected files.
 *
 * @author
 */
public class DownloadFileRequest extends Request {

    /**
     * Constructor creating the request.
     *
     * @param fileID
     *            String representing the file id.
     * @param fileFormat
     *            String representing the format of the file.
     */
    public DownloadFileRequest(String fileID, String fileFormat) {
        super("downloadfile", "/file/" + fileID, "GET");
    }

}
