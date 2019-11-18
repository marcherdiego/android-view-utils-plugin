package com.nerdscorner.android.view.utils.plugin.utils;

import static com.intellij.codeInsight.template.impl.TemplateSettings.SPACE_CHAR;

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

    public static String asCamelCase(String text) {
        text = replaceSpacesWithCamelCase(text);
        if (isNullOrEmpty(text)) {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    private static String replaceSpacesWithCamelCase(String text) {
        if (isNullOrEmpty(text)) {
            return text;
        }
        StringBuilder stringBuilder = new StringBuilder();
        boolean shouldCapitalizeLetter = false;
        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            if (letter == SPACE_CHAR) {
                shouldCapitalizeLetter = true;
            } else if (shouldCapitalizeLetter) {
                shouldCapitalizeLetter = false;
                stringBuilder.append(String.valueOf(letter).toUpperCase());
            } else {
                stringBuilder.append(letter);
            }
        }
        return stringBuilder.toString();
    }
}
