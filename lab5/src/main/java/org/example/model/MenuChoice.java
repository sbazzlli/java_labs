package org.example.model;

public enum MenuChoice {
    EXIT(0, "Exit"),
    FIND_MAX_WORDS(1, "Find line with maximum words (Task 1)"),
    ENCRYPT_FILE(2, "Encrypt file (Task 3)"),
    DECRYPT_FILE(3, "Decrypt file (Task 3)"),
    COUNT_HTML_TAGS(4, "Count HTML tag frequencies (Task 4)");

    private final int code;
    private final String description;

    MenuChoice(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static MenuChoice fromCode(int code) {
        for (MenuChoice choice : values()) {
            if (choice.code == code) {
                return choice;
            }
        }
        throw new IllegalArgumentException("Invalid menu choice: " + code);
    }
}

