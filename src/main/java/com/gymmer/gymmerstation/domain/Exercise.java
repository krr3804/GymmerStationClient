package com.gymmer.gymmerstation.domain;

import java.text.SimpleDateFormat;

public class Exercise {
    String name;
    Long set;
    Long rep;
    Long weight;
    String rest;

    public Exercise(String name, Long set, Long rep, Long weight, String rest
    ) {
        this.name = name;
        this.set = set;
        this.rep = rep;
        this.weight = weight;
        this.rest = rest;
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

    public String toString() {
        return "[" + name + ", " + set + ", " + rep + ", " + weight + ", " + rest + "]";
    }
}
