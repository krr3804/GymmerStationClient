package com.gymmer.gymmerstation.programManagement.validations;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public class ChangeNotFoundValidation {
    private static final String NO_CHANGE = "Change Not Found!";

    public static void changeNotFoundValidation(Program currentProgram, String name, String purpose, Long length, List<Long> removedDivisions, List<Exercise> addedExercises, List<Exercise> deletedExercises) {
        if(currentProgram.getName().equals(name) && currentProgram.getPurpose().equals(purpose) && currentProgram.getLength().equals(length)
        && removedDivisions.isEmpty() && deletedExercises.isEmpty() && addedExercises.isEmpty()) {
            throw new IllegalArgumentException(NO_CHANGE);
        }
    }
}
