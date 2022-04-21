package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.util.*;

public class MemoryOperationDataRepository implements OperationDataRepository {
    private static Map<Program, List<OperationDataProgram>> map = new LinkedHashMap<>();

    @Override
    public void save(Program program,List<OperationDataProgram> list) {
        map.put(program,list);
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
        int divisionCount = program.countDivision();
        int lastWeek = map.getOrDefault(program,new ArrayList<>()).size()/divisionCount;
        return lastWeek + 1;
    }

    @Override
    public int getCurrentDivision(Program program) {
        int divisionCount = program.countDivision();
        int lastDivision = map.getOrDefault(program,new ArrayList<>()).size()%divisionCount;
        return lastDivision + 1;
    }

    @Override
    public Map<Program,List<OperationDataProgram>> getPerformanceArchiveMap() {
        return map;
    }
}
