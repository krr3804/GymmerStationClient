package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.OperationDataExercise;
import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public interface ProgramOperationService {
    void saveProgramData(Program program, List<OperationDataProgram> odpList);

    void deleteProgramData(Program program);

    void updateODPList(Program program, List<OperationDataExercise> odeList);

    int getCurrentWeek(Program program);
}
