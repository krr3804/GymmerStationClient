package com.gymmer.gymmerstation.domain;

import java.util.List;
import java.util.Map;

public class OperationDataProgram {
    int week;
    int division;
    List<OperationDataExercise> odExerciseList;

    public OperationDataProgram(int week, int division, List<OperationDataExercise> odExerciseList) {
        this.week = week;
        this.division = division;
        this.odExerciseList = odExerciseList;
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
