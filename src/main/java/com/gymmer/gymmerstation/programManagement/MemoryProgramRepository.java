package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;

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
    public List<Program> showProgramList() {
        return list;
    }

    @Override
    public void editProgram(Program oldProgram, Program newProgram, List<Exercise> additionList, List<Exercise> deletionList) {
        int index = list.indexOf(getProgramById(oldProgram.getId()));
        list.set(index, newProgram);
    }

    @Override
    public Program getProgramById(Long id) {
        return list.stream().filter(program -> program.getId().equals(id)).findAny().get();
    }

    @Override
    public void deleteProgram(Long id) {
        list.removeIf(program -> program.getId().equals(id));
    }
}
