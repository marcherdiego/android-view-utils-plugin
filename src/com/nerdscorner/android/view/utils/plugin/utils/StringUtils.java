package com.nerdscorner.android.view.utils.plugin.utils;

import static com.intellij.codeInsight.template.impl.TemplateSettings.SPACE_CHAR;
import static com.nerdscorner.android.view.utils.plugin.utils.Constants.DOT;
import static com.nerdscorner.android.view.utils.plugin.utils.Constants.SLASH;
import static com.nerdscorner.android.view.utils.plugin.utils.Constants.UNDERSCORE;

public class StringUtils {
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.equals(Constants.EMPTY);
    }

    public static String extractSimpleClassName(String elementClass) {
        return substringFromChar(elementClass, DOT);
    }

    public static String extractElementId(String elementRawId) {
        return substringFromChar(elementRawId, SLASH);
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


    public static String snakeToCamel(String id) {
        if (StringUtils.isNullOrEmpty(id)) {
            return id;
        }
        StringBuilder result = new StringBuilder();
        boolean shouldWriteUppercase = false;
        for (int i = 0; i < id.length(); i++) {
            String nextChar = String.valueOf(id.charAt(i));
            if (nextChar.equals(UNDERSCORE)) {
                shouldWriteUppercase = true;
                continue;
            }
            if (shouldWriteUppercase) {
                shouldWriteUppercase = false;
                result.append(nextChar.toUpperCase());
            } else {
                result.append(nextChar);
            }
        }
        return result.toString();
    }
}
