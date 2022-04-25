package com.gymmer.gymmerstation.util;

import com.gymmer.gymmerstation.domain.Program;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Popup;

import java.util.List;
import java.util.Optional;

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
