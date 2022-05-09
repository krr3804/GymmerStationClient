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
    public void editProgram(Program oldProgram, Program newProgram, List<Exercise> additionList, List<Exercise> deletionList) {
        Long programId = newProgram.getId();
        if (!oldProgram.getName().equals(newProgram.getName()) || !oldProgram.getPurpose().equals(newProgram.getPurpose())
                || !oldProgram.getLength().equals(newProgram.getLength()) || !oldProgram.getDivisionQty().equals(newProgram.getDivisionQty())) {
            programRepository.editProgram(newProgram);
        }
        if(!additionList.isEmpty()) {
            programRepository.addExercises(programId,additionList);
        }
        if(!deletionList.isEmpty()) {
            programRepository.deleteExercises(programId,deletionList);
        }
    }

    @Override
    public void removeEntireDivision(Long programId, Long removedDivision) {
        programRepository.removeEntireDivision(programId, removedDivision);
    }

    @Override
    public Program getProgramById(int index) {
        List<Program> programList = programRepository.showProgramList();
        return programRepository.getProgramById(programList.get(index).getId());
    }

    @Override
    public void deleteProgram(Long id) {
        programRepository.deleteProgram(id);
    }
}
