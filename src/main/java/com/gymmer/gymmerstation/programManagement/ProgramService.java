package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public interface ProgramService {
    void addProgram(Program program);

    List<Program> getProgramList();

    void editProgram(Program oldProgram, Program newProgram, List<Exercise> additionList, List<Exercise> deletionList);

    void removeEntireDivision(Long programId, Long removedDivision);

    void deleteProgram(Long programId);
}
