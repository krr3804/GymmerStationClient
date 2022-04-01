package com.gymmer.gymmerstation.domain;

import java.text.SimpleDateFormat;

public class Exercise {
    String name;
    Long set;
    Long rep;
    SimpleDateFormat rest = new SimpleDateFormat("mm:ss");

    public Exercise(String name, Long set, Long rep, SimpleDateFormat rest) {
        this.name = name;
        this.set = set;
        this.rep = rep;
        this.rest = rest;
    }
}
