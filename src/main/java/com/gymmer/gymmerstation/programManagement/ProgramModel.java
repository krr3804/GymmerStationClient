package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.domain.Program;

public interface ProgramModel {
    void addProgram(Program program);

    void showProgram();

    void editProgram();

    void deleteProgram(Program program);
}
