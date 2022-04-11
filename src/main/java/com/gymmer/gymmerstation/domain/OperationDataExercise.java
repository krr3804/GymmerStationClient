package com.gymmer.gymmerstation.domain;

public class OperationDataExercise {
    int division;
    Exercise exercise;
    String timeConsumed;

    public OperationDataExercise(int division, Exercise exercise, String timeConsumed) {
        this.division = division;
        this.exercise = exercise;
        this.timeConsumed = timeConsumed;
    }

    public int getDivision() {
        return division;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public String getTimeConsumed() {
        return timeConsumed;
    }
}
