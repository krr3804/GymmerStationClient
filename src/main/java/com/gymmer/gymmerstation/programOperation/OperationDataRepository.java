package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OperationDataRepository {
    void save(OperationDataProgram dataProgram);

    void delete(Program program);

    List<OperationDataProgram> getDataListByProgram(Program program);

    int getProgress(Program program);

    List<Program> getProgramsInProgress();
}
