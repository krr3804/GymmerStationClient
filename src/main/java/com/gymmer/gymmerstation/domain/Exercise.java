package com.gymmer.gymmerstation.domain;

import java.text.SimpleDateFormat;

public class Exercise {
    String name;
    Long set;
    Long rep;
    Long weight;
    String minute;
    String second;

    public Exercise(String name, Long set, Long rep, Long weight, String minute, String second
    ) {
        this.name = name;
        this.set = set;
        this.rep = rep;
        this.weight = weight;
        this.minute = minute;
        this.second = second;
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
}
