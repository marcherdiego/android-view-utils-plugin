package com.nerdscorner.android.view.utils.plugin.utils;

public class StringUtils {
    private static final String EMPTY_STRING = "";

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.equals(EMPTY_STRING);
    }

    public static String extractSimpleClassName(String elementClass) {
        return substringFromChar(elementClass, ".");
    }

    public static String extractElementId(String elementRawId) {
        return substringFromChar(elementRawId, "/");
    }

    public static String substringFromChar(String string, String charString) {
        if (isNullOrEmpty(string)) {
            return string;
        }
        int lastDotIndex = string.lastIndexOf(charString);
        if (lastDotIndex == -1) {
            return string;
        }
        return string.substring(lastDotIndex + 1);
    }
}
