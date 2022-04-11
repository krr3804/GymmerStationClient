package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryOperationDataRepository implements OperationDataRepository {
    Map<Program, List<OperationDataProgram>> map = new HashMap<>();

    @Override
    public void save(Program program,List<OperationDataProgram> list) {
        map.put(program,list);
    }

    @Override
    public void delete(Program program) {
        map.remove(program);
    }
}
