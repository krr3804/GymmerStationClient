package com.gymmer.gymmerstation.domain;

public class Program {
    String name;
    String purpose;
    Long length;
    Division division;

    public Program(String name, String purpose, Long length, Division division) {
        this.name = name;
        this.purpose = purpose;
        this.length = length;
        this.division = division;
    }
}
