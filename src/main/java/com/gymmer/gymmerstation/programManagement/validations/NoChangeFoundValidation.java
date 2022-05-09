package com.gymmer.gymmerstation.programManagement.validations;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public class NoChangeFoundValidation {
    private static final String NO_CHANGE_FOUND = "No Change Found!";
    public static void noChangeFoundValidation(Program currentProgram, String name, String purpose, Long length,
                                               List<Exercise> additionList, List<Exercise> deletionList) {
        if(currentProgram.getName().equals(name) && currentProgram.getPurpose().equals(purpose) && currentProgram.getLength().equals(length)
                && additionList.isEmpty() && deletionList.isEmpty()) {
            throw new IllegalArgumentException(NO_CHANGE_FOUND);
        }
    }
}
