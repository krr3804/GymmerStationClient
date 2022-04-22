package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;
import java.util.stream.Collectors;

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
        return programRepository.showProgramList().stream().map(p -> p.getName()).collect(Collectors.toList());
    }

    @Override
    public void editProgram(Program oldProgram, Program newProgram, List<Long> removedDivisions, List<Exercise> addedExercises, List<Exercise> deletedExercises) {
        programRepository.editProgram(oldProgram,newProgram,removedDivisions,addedExercises,deletedExercises);
    }

    @Override
    public Program getProgramById(int index) {
        Long id = programRepository.showProgramList().get(index).getId();
        return programRepository.getProgramById(id);
    }

    @Override
    public void deleteProgram(int index) {
        programRepository.deleteProgram(programRepository.showProgramList().get(index).getId());
    }
}
