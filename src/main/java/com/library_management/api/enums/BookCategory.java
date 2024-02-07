package com.library_management.api.enums;

public enum BookCategory {
    NOVEL(0), SCIENCE(1);


    private final int value;

    BookCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static BookCategory fromValue(int value) {
        for (BookCategory status : BookCategory.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + value);
    }
}
