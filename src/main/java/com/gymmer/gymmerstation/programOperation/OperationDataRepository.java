package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OperationDataRepository {
    void save(Program program, List<OperationDataProgram> list);

    void delete(Program program);

    List<OperationDataProgram> getODPList(Program program);

    int getCurrentWeek(Program program);

    int getCurrentDivision(Program program);

    Map<Program,List<OperationDataProgram>> getPerformanceArchiveMap();
}
