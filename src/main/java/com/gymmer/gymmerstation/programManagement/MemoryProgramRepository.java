package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.domain.Program;

import java.util.ArrayList;
import java.util.List;

public class MemoryProgramRepository implements ProgramRepository{
    private static List<Program> list = new ArrayList<>();
    @Override
    public void addProgram(Program program) {
        list.add(program);
    }

    @Override
    public List<Program> showProgramList() {
        return list;
    }

    @Override
    public void editProgram(Program program) {

    }

    @Override
    public void deleteProgram(Program program) {

    }
}
