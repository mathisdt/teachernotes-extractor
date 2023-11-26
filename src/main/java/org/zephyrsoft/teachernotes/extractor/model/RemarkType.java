package org.zephyrsoft.teachernotes.extractor.model;

public enum RemarkType {
    POSITIVE, NEGATIVE, NEUTRAL;

    public static RemarkType of(String str) {
        return switch (str) {
            case "P" -> POSITIVE;
            case "N" -> NEGATIVE;
            case "" -> NEUTRAL;
            default -> throw new IllegalArgumentException("unknown remark type " + str);
        };
    }
}
