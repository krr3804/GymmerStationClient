package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public interface ProgramOperationService {
    void saveProgramData(OperationDataProgram operationDataProgram);

    void terminateProgram(Long programId);

    void deleteProgramData(Long programId, boolean status);

    List<OperationDataProgram> getProgramDataList(Program program);

    int getProgress(Long programId);

    Long getCurrentWeek(Program program);

    Long getCurrentDivision(Program program);

    List<Program> getPerformanceArchiveList(boolean status);
}
