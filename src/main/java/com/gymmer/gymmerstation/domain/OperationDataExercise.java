package com.gymmer.gymmerstation.domain;

public class OperationDataExercise extends Exercise {
    private String timeConsumed;

    public OperationDataExercise(String name, Long set, Long rep, Long weight, String restTime, Long division, String timeConsumed) {
        super(name, set, rep, weight, restTime, division);
        this.timeConsumed = timeConsumed;
    }

    public String getTimeConsumed() {
        return timeConsumed;
    }
}
