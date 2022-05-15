package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;

import java.util.ArrayList;
import java.util.List;

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
    public void terminateProgram(Long programId) {
        operationDataRepository.terminate(programId);
    }

    @Override
    public void deleteProgramData(Long programId, boolean status) {
        operationDataRepository.delete(programId, status);
    }

    @Override
    public List<OperationDataProgram> getProgramDataList(Program program) {
        return operationDataRepository.getProgramData(program);
    }

    @Override
    public int getProgress(Long programId) {
        return operationDataRepository.getProgress(programId);
    }

    @Override
    public Long getCurrentWeek(Program program) {
        long res = 0L;
        try {
            res = operationDataRepository.getProgress(program.getId()) / program.getDivisionQty() + 1;
        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }
        return res;
    }

    @Override
    public Long getCurrentDivision(Program program) {
        long res = 0L;
        try {
            res = operationDataRepository.getProgress(program.getId()) % program.getDivisionQty() + 1;
        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }
        return res;
    }

    @Override
    public List<Program> getPerformanceArchiveList(boolean status) {
        return operationDataRepository.getPrograms(status);
    }


}
