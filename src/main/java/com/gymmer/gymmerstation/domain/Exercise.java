package com.gymmer.gymmerstation.domain;

import java.io.Serializable;

public class Exercise implements Serializable {
    private static final long serialVersionUID = 1234567891L;

    private String name;
    private Long set;
    private Long rep;
    private Long weight;
    private String restTime;
    private Long division;

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
