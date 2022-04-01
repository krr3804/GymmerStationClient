package com.gymmer.gymmerstation.domain;

public class Program {
    String name;
    String purpose;
    Long length;
    Long division;

    public Program(String name, String purpose, Long length, Long division) {
        this.name = name;
        this.purpose = purpose;
        this.length = length;
        this.division = division;
    }

    public String getName() {
        return name;
    }

    public String getPurpose() {
        return purpose;
    }

    public Long getLength() {
        return length;
    }

    public Long getDivision() {
        return division;
    }
}
