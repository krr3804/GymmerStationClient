package com.gymmer.gymmerstation.programManagement.validations;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public class DataUnsavedValidation {
    private static final String DATA_UNSAVED = "Data Unsaved!";
    public static void dataUnsavedValidationCreation(Program program, String name, String purpose, String length) {
        if(!name.isBlank() || !purpose.isBlank() || !length.isBlank() || !program.getExerciseList().isEmpty()) {
            throw new IllegalArgumentException(DATA_UNSAVED);
        }
    }

    public static void dataUnsavedValidationEdition(Program currentProgram, String name, String purpose, String length,
                                                    List<Exercise> additionList, List<Exercise> deletionList) {
        Long tmpLength = 0L;
        if(!length.isBlank()) {
            tmpLength = Long.parseLong(length);
        }
        if(!currentProgram.getName().equals(name) || !currentProgram.getPurpose().equals(purpose) || !currentProgram.getLength().equals(tmpLength)
        || !additionList.isEmpty() || !deletionList.isEmpty()) {
            throw new IllegalArgumentException(DATA_UNSAVED);
        }
    }
}
