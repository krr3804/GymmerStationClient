package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public interface ProgramModel {
    void addProgram(Program program);

    List<String> showProgramList();

    void editProgram(Program program);

    void deleteProgram(Program program);
}
