package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public interface ProgramRepository {
    void addProgram(Program program);

    List<Program> showProgramList();

    void removeEntireDivision(Long programId, Long removedDivision);

    void addExercises(Long programId, List<Exercise> exerciseList);

    void deleteExercises(Long programId, List<Exercise> exerciseList);

    void editProgram(Program newProgram);

    void deleteProgram(Long id);
}
