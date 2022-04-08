package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public class ProgramServiceImpl implements ProgramService {
    ProgramRepository programRepository;

    public ProgramServiceImpl(ProgramRepository programRepository) {
        this.programRepository = AppConfig.programRepository();
    }

    @Override
    public void addProgram(Program program) {
        programRepository.addProgram(program);
    }

    @Override
    public List<String> showProgramList() {
        return programRepository.showProgramList();
    }

    @Override
    public void editProgram(int index,Program program) {
        programRepository.editProgram(index,program);
    }

    @Override
    public Program getProgram(int index) {
        return programRepository.getProgramByIndex(index);
    }

    @Override
    public void deleteProgram(int index) {
        programRepository.deleteProgram(index);
    }
}
