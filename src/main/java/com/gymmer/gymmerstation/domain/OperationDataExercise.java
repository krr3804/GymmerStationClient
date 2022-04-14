package com.gymmer.gymmerstation.domain;

public class OperationDataExercise{
    String name;
    Long set;
    Long rep;
    Long weight;
    String rest;
    String timeConsumed;


    public OperationDataExercise(String name, Long set, Long rep, Long weight, String rest, String timeConsumed) {
        this.name = name;
        this.set = set;
        this.rep = rep;
        this.weight = weight;
        this.rest = rest;
        this.timeConsumed = timeConsumed;
    }

    public String getName() {
        return name;
    }

    public Long getSet() {
        return set;
    }

    public Long getRep() {
        return rep;
    }

    public Long getWeight() {
        return weight;
    }

    public String getRest() {
        return rest;
    }

    public String getTimeConsumed() {
        return timeConsumed;
    }
}
