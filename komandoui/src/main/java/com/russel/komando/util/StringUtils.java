package com.russel.komando.util;

public class StringUtils {

    private StringUtils() {
    }
    public static String toTitleCase(String input) {
        if (input == null || input.isEmpty()) return "";

        String[] parts = input.toLowerCase().split("_");
        StringBuilder sb = new StringBuilder();

        for (String part : parts) {
            if (part.length() > 0) {
                sb.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1))
                        .append(" ");
            }
        }

        return sb.toString().trim();
    }
}
