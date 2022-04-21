package com.gymmer.gymmerstation.domain;

import java.util.List;
import java.util.Map;

public class OperationDataProgram {
    private Program program;
    private int week;
    private int division;
    private List<OperationDataExercise> odExerciseList;

    public OperationDataProgram(Program program, int week, int division, List<OperationDataExercise> odExerciseList) {
        this.program = program;
        this.week = week;
        this.division = division;
        this.odExerciseList = odExerciseList;
    }

    public Program getProgram() {
        return program;
    }

    public int getWeek() {
        return week;
    }

    public int getDivision() {
        return division;
    }

    public List<OperationDataExercise> getOdExerciseList() {
        return odExerciseList;
    }
}
