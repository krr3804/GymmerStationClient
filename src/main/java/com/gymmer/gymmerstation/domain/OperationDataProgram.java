package com.gymmer.gymmerstation.domain;

import java.util.List;

public class OperationDataProgram {
    private Program program;
    private Long week;
    private Long division;
    private List<OperationDataExercise> odExerciseList;

    public OperationDataProgram(Program program, Long week, Long division, List<OperationDataExercise> odExerciseList) {
        this.program = program;
        this.week = week;
        this.division = division;
        this.odExerciseList = odExerciseList;
    }

    public Program getProgram() {
        return program;
    }

    public Long getWeek() {
        return week;
    }

    public Long getDivision() {
        return division;
    }

    public List<OperationDataExercise> getOdExerciseList() {
        return odExerciseList;
    }
}
