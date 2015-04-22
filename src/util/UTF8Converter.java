package util;

import java.io.UnsupportedEncodingException;

import model.ErrorLogger;

public class UTF8Converter {
    public static String convertFromUTF8(String str) {
        try {
            return new String(str.getBytes(), ("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            ErrorLogger.log(e);
            return "";
        }
    }
}
