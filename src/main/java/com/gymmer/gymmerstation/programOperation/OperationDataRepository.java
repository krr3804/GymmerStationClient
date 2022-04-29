package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public interface OperationDataRepository {
    void save(OperationDataProgram dataProgram);

    void terminate(Program program);

    void delete(Program program, boolean status);

    List<OperationDataProgram> getProgramData(Program program);

    int getProgress(Program program);

    List<Program> getPrograms(boolean status);
}
