package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;

import java.util.List;
import java.util.Map;

public interface ProgramService {
    void addProgram(Program program);

    List<String> showProgramList();

    void editProgram(int index, Program program);

    Program getProgram(int index);

    void deleteProgram(int index);

    Map<Integer,List<Exercise>> createExerciseMap(int divCount);
}
