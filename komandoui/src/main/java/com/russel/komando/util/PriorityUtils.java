package com.russel.komando.util;

public class PriorityUtils {
    public static int getPriorityWeight(String priority) {
        if (priority == null) return 0;
        return switch (priority) {
            case "HIGH" -> 3;
            case "MEDIUM" -> 2;
            case "LOW" -> 1;
            default -> 0;
        };
    }
}
