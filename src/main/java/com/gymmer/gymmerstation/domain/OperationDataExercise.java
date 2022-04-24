package com.gymmer.gymmerstation.domain;

public class OperationDataExercise extends Exercise {
    private String timeConsumed;
    private Long currentSet;

    public OperationDataExercise(String name, Long set, Long rep, Long weight, String restTime, Long division, Long currentSet, String timeConsumed) {
        super(name, set, rep, weight, restTime, division);
        this.currentSet = currentSet;
        this.timeConsumed = timeConsumed;
    }

    public Long getCurrentSet() {
        return currentSet;
    }

    public String getTimeConsumed() {
        return timeConsumed;
    }
}
