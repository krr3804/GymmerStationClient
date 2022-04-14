package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.OperationDataExercise;
import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public void deleteProgramData(int index) {
        Program program = getProgramByIndex(index);
        operationDataRepository.delete(program);
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
            sb.append(program.getName()).append("(")
                    .append(map.get(program).size()).append("/")
                    .append((program.getLength() * program.getExerciseMap().size())).append(")");
            list.add(sb.toString());
        }

        return list;
    }

    @Override
    public Program getProgramByIndex(int index) {
        List<Program> programList = operationDataRepository.getPerformanceArchiveMap().keySet().stream().collect(Collectors.toList());
        return programList.get(index);
    }

    @Override
    public List<OperationDataProgram> getODPList(Program program) {
        return operationDataRepository.getODPList(program);
    }
}
