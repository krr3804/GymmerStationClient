package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public interface ProgramService {
    void addProgram(Program program);

    List<String> showProgramList();

    void editProgram(Program oldProgram, Program newProgram, List<Long> removedDivisions, List<Exercise> addedExercises, List<Exercise> deletedExercises);

    Program getProgramById(int index);

    void deleteProgram(int index);
}
