package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public interface ProgramRepository {
    void addProgram(Program program);

    List<Program> showProgramList();

    void editProgram(Program program, List<Exercise> additionList, List<Exercise> deletionList);

    Program getProgramById(Long id);

    void deleteProgram(Long id);
}
