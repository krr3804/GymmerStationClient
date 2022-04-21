package com.gymmer.gymmerstation.domain;

import java.text.SimpleDateFormat;

public class Exercise {
    String name;
    Long set;
    Long rep;
    Long weight;
    String minute;
    String second;
    Long division;

    public Exercise(String name, Long set, Long rep, Long weight, String minute, String second, Long division) {
        this.name = name;
        this.set = set;
        this.rep = rep;
        this.weight = weight;
        this.minute = minute;
        this.second = second;
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

    public String getMinute() {
        return minute;
    }

    public String getSecond() {
        return second;
    }

    public Long getDivision() {
        return division;
    }

    public void decreaseDivisionSequence(Long division) {
        this.division = division-1;
    }
}
