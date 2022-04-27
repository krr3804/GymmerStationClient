package com.gymmer.gymmerstation.programManagement.validations;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

import static com.gymmer.gymmerstation.programManagement.validations.ChangeNotFoundValidation.changeNotFoundValidation;

public class DataUnsavedValidation {
    private static final String DATA_UNSAVED = "Data Unsaved!";
    public static void dataUnsavedValidationCreation(Program program, String name, String purpose, String length) {
        if(!name.isBlank() || !purpose.isBlank() || !length.isBlank() || !program.getExerciseList().isEmpty()) {
            throw new IllegalArgumentException(DATA_UNSAVED);
        }
    }
}
