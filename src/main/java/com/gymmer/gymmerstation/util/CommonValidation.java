package com.gymmer.gymmerstation.util;

public class CommonValidation {
    private static final String NO_ITEM_SELECTED = "No Item Selected!";

    public static void noItemSelectedValidation(Object selectedItem) {
        if (selectedItem == null) {
            throw new IllegalArgumentException(NO_ITEM_SELECTED);
        }
    }

    public static void noIndexSelectedValidation(int index) {
        if(index < 0) {
            throw new IllegalArgumentException(NO_ITEM_SELECTED);
        }
    }
}
