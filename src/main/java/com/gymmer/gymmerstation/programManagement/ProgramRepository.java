package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public interface ProgramRepository {
    void addProgram(Program program);

    List<Program> showProgramList();

    void editProgram(Program oldProgram, Program newProgram, List<Long> removedDivisions, List<Exercise> addedExercises, List<Exercise> deletedExercises);

    Program getProgramById(Long id);

    void deleteProgram(Long id);
}
