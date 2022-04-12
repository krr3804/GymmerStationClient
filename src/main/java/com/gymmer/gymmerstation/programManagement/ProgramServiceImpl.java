package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;

    public ProgramServiceImpl(ProgramRepository programRepository) {
        this.programRepository = AppConfig.programRepository();
    }

    @Override
    public void addProgram(Program program) {
        programRepository.addProgram(program);
    }

    @Override
    public List<String> showProgramList() {
        return programRepository.showProgramList();
    }

    @Override
    public void editProgram(int index,Program program) {
        programRepository.editProgram(index,program);
    }

    @Override
    public Program getProgram(int index) {
        return programRepository.getProgramByIndex(index);
    }

    @Override
    public void deleteProgram(int index) {
        programRepository.deleteProgram(index);
    }

    @Override
    public Map<Integer,List<Exercise>> createExerciseMap(int divCount) {
        Map<Integer,List<Exercise>> exerciseMap = new LinkedHashMap<>();
        for(int i = 1; i <= divCount; i++) {
            List<Exercise> exerciseList = new ArrayList<>();
            exerciseMap.put(i,exerciseList);
        }
        return exerciseMap;
    }
}
