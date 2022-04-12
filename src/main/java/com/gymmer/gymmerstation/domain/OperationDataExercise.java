package com.gymmer.gymmerstation.domain;

public class OperationDataExercise {
    Exercise exercise;
    String timeConsumed;

    public OperationDataExercise(Exercise exercise, String timeConsumed) {
        this.exercise = exercise;
        this.timeConsumed = timeConsumed;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public String getTimeConsumed() {
        return timeConsumed;
    }
}
