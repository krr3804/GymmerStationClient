package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryOperationDataRepository implements OperationDataRepository {
    private static Map<Program, List<OperationDataProgram>> map = new HashMap<>();

    @Override
    public void save(Program program,List<OperationDataProgram> list) {
        map.put(program,list);
        System.out.println(map.size());
        System.out.println(map.get(program).size());
    }

    @Override
    public void delete(Program program) {
        map.remove(program);
    }

    @Override
    public List<OperationDataProgram> getODPList(Program program) {
        return map.getOrDefault(program,new ArrayList<>());
    }

    @Override
    public int getCurrentWeek(Program program) {
        int divisionCount = program.getExerciseMap().size();
        int lastWeek = map.getOrDefault(program,new ArrayList<>()).size()/divisionCount;
        return lastWeek + 1;
    }

    @Override
    public int getCurrentDivision(Program program) {
        int divisionCount = program.getExerciseMap().size();
        int lastDivision = map.getOrDefault(program,new ArrayList<>()).size()%divisionCount;
        return lastDivision + 1;
    }
}