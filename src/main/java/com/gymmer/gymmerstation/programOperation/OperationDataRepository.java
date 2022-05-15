package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public interface OperationDataRepository {
    void save(OperationDataProgram dataProgram);

    void terminate(Long programId);

    void delete(Long programId, boolean status);

    List<OperationDataProgram> getProgramData(Program program);

    int getProgress(Long programId);

    List<Program> getPrograms(boolean status);
}
