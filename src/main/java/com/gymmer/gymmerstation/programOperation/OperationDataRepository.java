package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public interface OperationDataRepository {
    void save(Program program, List<OperationDataProgram> list);

    void delete(Program program);

    List<OperationDataProgram> getODPList(Program program);

    int getCurrentWeek(Program program);

    int getCurrentDivision(Program program);
}
