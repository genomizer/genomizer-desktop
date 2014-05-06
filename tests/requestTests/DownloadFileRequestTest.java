package requestTests;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import requests.DownloadFileRequest;

public class DownloadFileRequestTest {

	private DownloadFileRequest download;

	@Before
	public void setUp() {
		download = new DownloadFileRequest("file1", "wig");
	}

	@Test
	public void testCreateDownloadRequest() {
		assertThat(download).isNotNull();
	}

	@Test
	public void testGetFileName() {
		assertEquals("file1", download.fileName);
	}

	@Test
	public void testGetFileFormat() {
		assertEquals("wig", download.fileFormat);
	}

	@Test
	public void testGetRequestName() {
		assertEquals("downloadfile", download.requestName);
	}

}
