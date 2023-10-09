package org.lemon.utils;

public class StringUtil {
    public static boolean isBlank(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        for (char ch : s.toCharArray()) {
            if (!Character.isWhitespace(ch)) {
                return false;
            }
        }
        return true;
    }
}
