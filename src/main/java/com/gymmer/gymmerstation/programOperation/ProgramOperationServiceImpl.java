package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.OperationDataExercise;
import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public class ProgramOperationServiceImpl implements ProgramOperationService{
    OperationDataRepository operationDataRepository;

    public ProgramOperationServiceImpl(OperationDataRepository operationDataRepository) {
        this.operationDataRepository = AppConfig.operationDataRepository();
    }

    @Override
    public void saveProgramData(Program program, List<OperationDataProgram> odpList) {
        operationDataRepository.save(program,odpList);
    }

    @Override
    public void deleteProgramData(Program program) {

    }

    @Override
    public void updateODPList(Program program, List<OperationDataExercise> odeList) {
        List<OperationDataProgram> list =operationDataRepository.getODPList(program);
        int currentWeek = getCurrentWeek(program);
        list.add(new OperationDataProgram(currentWeek,odeList));
        saveProgramData(program,list);
    }


    @Override
    public int getCurrentWeek(Program program) {
        return operationDataRepository.getCurrentWeek(program);
    }
}
