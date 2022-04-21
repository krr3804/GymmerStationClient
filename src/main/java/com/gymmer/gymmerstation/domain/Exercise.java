package com.gymmer.gymmerstation.domain;

public class Exercise {
    String name;
    Long set;
    Long rep;
    Long weight;
    String restTime;
    Long division;

    public Exercise(String name, Long set, Long rep, Long weight, String restTime, Long division) {
        this.name = name;
        this.set = set;
        this.rep = rep;
        this.weight = weight;
        this.restTime = restTime;
        this.division = division;
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

    public String getRestTime() {
        return restTime;
    }

    public Long getDivision() {
        return division;
    }

    public void decreaseDivisionSequence(Long division) {
        this.division = division-1;
    }
}
