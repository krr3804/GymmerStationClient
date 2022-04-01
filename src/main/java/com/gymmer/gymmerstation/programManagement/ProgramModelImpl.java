package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public class ProgramModelImpl implements ProgramModel {
    ProgramRepository programRepository;

    public ProgramModelImpl(ProgramRepository programRepository) {
        this.programRepository = AppConfig.programRepository();
    }

    @Override
    public void addProgram(Program program) {
        programRepository.addProgram(program);
    }

    @Override
    public List<Program> showProgramList() {
        return null;
    }

    @Override
    public void editProgram(Program program) {

    }

    @Override
    public void deleteProgram(Program program) {

    }
}
