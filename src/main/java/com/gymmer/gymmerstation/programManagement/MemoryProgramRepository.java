package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.domain.Program;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MemoryProgramRepository implements ProgramRepository{
    private static List<Program> list = new LinkedList<>();
    @Override
    public void addProgram(Program program) {
        list.add(program);
    }

    @Override
    public List<String> showProgramList() {
        return list.stream().map(p -> p.getName()).collect(Collectors.toList());
    }

    @Override
    public void editProgram(int index, Program program) {
        list.set(index,program);
    }

    @Override
    public Program getProgramByIndex(int index) {
        return list.get(index);
    }

    @Override
    public void deleteProgram(int index) {
        list.remove(index);
    }
}
