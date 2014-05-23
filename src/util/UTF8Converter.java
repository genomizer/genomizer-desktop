package util;

import java.io.UnsupportedEncodingException;

public class UTF8Converter {
    public static String convertFromUTF8(String str) {
        try {
            return new String(str.getBytes(), ("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
