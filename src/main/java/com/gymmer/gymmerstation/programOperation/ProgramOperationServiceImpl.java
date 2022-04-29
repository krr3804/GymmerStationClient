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
    public void saveProgramData(OperationDataProgram operationDataProgram) {
        operationDataRepository.save(operationDataProgram);
    }

    @Override
    public void terminateProgram(Program program) {
        operationDataRepository.terminate(program);
    }

    @Override
    public void deleteProgramData(Program program, boolean status) {
        operationDataRepository.delete(program, status);
    }

    @Override
    public List<OperationDataProgram> getProgramDataList(Program program) {
        return operationDataRepository.getProgramData(program);
    }

    @Override
    public Long getCurrentWeek(Program program) {
        long res = 0L;
        try {
            res = operationDataRepository.getProgress(program) / program.getDivisionQty() + 1;
        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }
        return res;
    }

    @Override
    public Long getCurrentDivision(Program program) {
        long res = 0L;
        try {
            res = operationDataRepository.getProgress(program) % program.getDivisionQty() + 1;
        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }
        return res;
    }

    @Override
    public List<String> getPerformanceArchiveList(boolean status) {
        List<String> list = new ArrayList<>();
        for(Program program: operationDataRepository.getPrograms(status)) {
            StringBuilder sb = new StringBuilder();
            sb.append(program.getName()).append("(");
            if(status) {
                sb.append("complete");
            } else {
                sb.append(operationDataRepository.getProgress(program)).append("/").append((program.getLength() * program.getDivisionQty()));
            }
            sb.append(")");
            list.add(sb.toString());
        }

        return list;
    }

    @Override
    public Program getProgramByIndex(int index, boolean status) {
        return operationDataRepository.getPrograms(status).get(index);
    }
}
