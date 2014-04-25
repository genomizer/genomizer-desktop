package requestTests;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import requests.DownloadFileRequest;

public class DownloadFileRequestTest {

    @Test
    public void testCreateDownloadRequest() {
	DownloadFileRequest download = new DownloadFileRequest("file1", "wig");
	assertThat(download).isNotNull();
    }

    @Test
    public void testGetFileName() {
	DownloadFileRequest download = new DownloadFileRequest("file1", "wig");
	assertEquals("file1", download.fileName);
    }

    @Test
    public void testGetFileFormat() {
	DownloadFileRequest download = new DownloadFileRequest("file1", "wig");
	assertEquals("wig", download.fileFormat);
    }

    @Test
    public void testGetRequestName() {
	DownloadFileRequest download = new DownloadFileRequest("file1", "wig");
	assertEquals("downloadfile", download.requestName);
    }

}
