package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.OperationDataExercise;
import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public interface ProgramOperationService {
    void saveProgramData(OperationDataProgram operationDataProgram);

    void deleteProgramData(Program program);

    List<OperationDataProgram> getProgramDataList(Program program);

    int getCurrentWeek(Program program);

    int getCurrentDivision(Program program);

    List<String> getPerformanceArchiveList();

    Program getProgramByIndex(int index);
}
