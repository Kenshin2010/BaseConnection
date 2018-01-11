package com.manroid.core.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Created by Mr.Sen on 9/19/2017.
 */

public class StringUtil {
    public static boolean isNullOrEmpty(String input) {
        if (input == null || "".equals(input.trim()) || "null".equals(input)) {
            return true;
        }
        return false;
    }

    public static String[] splitStringByCharacter(String str, String character) {
        String[] result = null;
        if (!StringUtil.isNullOrEmpty(str) || !StringUtil.isNullOrEmpty(character)) {
            result = str.split(character);
        }
        return result;
    }

    public static String getAvatarNameDefault(String name) {
        if (isNullOrEmpty(name))
            return null;
        StringBuilder result = new StringBuilder();
        String[] nameSplit = name.split(" ");
        int length = nameSplit.length;
        String firstCharacter = null;
        String lastCharacter = null;
        firstCharacter = String.valueOf(nameSplit[0].charAt(0));
        result.append(deAccent(firstCharacter));
        if (length > 1) {
            lastCharacter = String.valueOf(nameSplit[length - 1].charAt(0));
            result.append(deAccent(lastCharacter));
        }
        return result.toString().toUpperCase();
    }

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}
