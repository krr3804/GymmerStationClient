package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OperationDataRepository {
    void save(OperationDataProgram dataProgram);

    void terminate(Program program);

    void deleteDataInProgress(Program program);

    void deleteDataTerminated(int index);

    List<OperationDataProgram> getProgramDataInProgress(Program program);

    List<OperationDataProgram> getProgramDataTerminated(int index);

    int getProgress(Program program);

    List<Program> getProgramsInProgress();
}
