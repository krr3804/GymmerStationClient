package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.OperationDataExercise;
import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProgramOperationServiceImpl implements ProgramOperationService{
    private final OperationDataRepository operationDataRepository;

    public ProgramOperationServiceImpl(OperationDataRepository operationDataRepository) {
        this.operationDataRepository = AppConfig.operationDataRepository();
    }

    @Override
    public void saveProgramData(Program program, int week, int division, List<OperationDataExercise> odeList) {
        List<OperationDataProgram> list = new ArrayList<>(operationDataRepository.getODPList(program));
        list.add(new OperationDataProgram(week,division,odeList));
        operationDataRepository.save(program,list);
    }

    @Override
    public void deleteProgramData(Program program) {
    }

    @Override
    public int getCurrentWeek(Program program) {
        return operationDataRepository.getCurrentWeek(program);
    }

    @Override
    public int getCurrentDivision(Program program) {
        return operationDataRepository.getCurrentDivision(program);
    }

    @Override
    public List<String> getPerformanceArchiveList() {
        List<String> list = new ArrayList<>();
        Map<Program,List<OperationDataProgram>> map = operationDataRepository.getPerformanceArchiveMap();
        for(Program program: map.keySet()) {
            StringBuilder sb = new StringBuilder();
            String programName = program.getName();
            String progress = "" + map.get(program).size();
            String total = "" + (program.getLength() * program.getExerciseMap().size());
            sb.append(programName).append("(").append(progress).append("/").append(total).append(")");
            list.add(sb.toString());
        }

        return list;
    }
}
