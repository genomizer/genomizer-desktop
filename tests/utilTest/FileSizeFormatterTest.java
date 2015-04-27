package utilTest;

import static org.junit.Assert.*;

import org.junit.Test;

import util.FileSizeFormatter;

public class FileSizeFormatterTest {

    @Test
    public void shouldReturnZeroByteString() {
        String actual = FileSizeFormatter.convertByteToString(0);
        assertEquals(actual,"0 B");
    }

    @Test
    public void shouldReturnCorrectByteSize() {
        String actual = FileSizeFormatter.convertByteToString(864);
        assertEquals(actual,"864 B");
    }

    @Test
    public void shouldReturnBytesConvertedTokB() {
        String actual = FileSizeFormatter.convertByteToString(5468);
        assertEquals("5.47 kB", actual);
    }

    @Test
    public void shouldReturnBytesConvertedToMB() {
        String actual = FileSizeFormatter.convertByteToString(52619870);
        assertEquals("52.62 MB", actual);
    }

    @Test
    public void shouldReturnBytesConvertedToGB() {
        String actual = FileSizeFormatter.convertByteToString(52619870000L);
        assertEquals("52.62 GB", actual);
        
    }
    








}
