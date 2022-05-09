package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;

public interface ProgramService {
    void addProgram(Program program);

    List<String> showProgramList();

    void editProgram(Program oldProgram, Program newProgram, List<Exercise> additionList, List<Exercise> deletionList);

    void removeEntireDivision(Long programId, Long removedDivision);

    Program getProgramById(int index);

    void deleteProgram(Long id);
}
