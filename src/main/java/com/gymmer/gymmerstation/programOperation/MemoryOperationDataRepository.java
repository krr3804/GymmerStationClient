package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryOperationDataRepository implements OperationDataRepository {
    private static List<OperationDataProgram> list = new LinkedList<>();

    @Override
    public void save(OperationDataProgram dataProgram) {
        list.add(dataProgram);
    }

    @Override
    public void delete(Program program) {
        list.removeIf(dataProgram -> dataProgram.getProgram().getId().equals(program.getId()));
    }

    @Override
    public List<OperationDataProgram> getDataListByProgram(Program program) {
        return list.stream().filter(dataProgram -> dataProgram.getProgram().getId().equals(program.getId())).collect(Collectors.toList());
    }

    @Override
    public int getProgress(Program program) {
        return list.stream().filter(dataProgram -> dataProgram.getProgram().getId().equals(program.getId())).collect(Collectors.toList()).size();
    }

    @Override
    public List<Program> getProgramsInProgress() {
        return list.stream().map(dataProgram -> dataProgram.getProgram()).distinct().collect(Collectors.toList());
    }
}
