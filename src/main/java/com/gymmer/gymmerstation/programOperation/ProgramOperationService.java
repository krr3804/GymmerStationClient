package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public interface ProgramOperationService {
    void saveProgramData(OperationDataProgram operationDataProgram);

    void terminateProgram(Program program);

    void deleteProgramData(Program program, boolean status);

    List<OperationDataProgram> getProgramDataList(Program program);

    int getProgress(Program program);

    Long getCurrentWeek(Program program);

    Long getCurrentDivision(Program program);

    List<String> getPerformanceArchiveList(boolean status);

    Program getProgramByIndex(int index, boolean status);
}
