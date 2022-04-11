package com.gymmer.gymmerstation.domain;

import java.util.List;
import java.util.Map;

public class OperationDataProgram {
    int week;
    List<OperationDataExercise> odExerciseList;

    public OperationDataProgram(int week, List<OperationDataExercise> odExerciseList) {
        this.week = week;
        this.odExerciseList = odExerciseList;
    }

    public int getWeek() {
        return week;
    }

    public List<OperationDataExercise> getOdExerciseList() {
        return odExerciseList;
    }
}
