package com.gymmer.gymmerstation.domain;

import java.util.List;

public class Program {
    String name;
    String purpose;
    Long length;
    List<Division> division;

    public Program(String name, String purpose, Long length, List<Division> division) {
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

    public List<Division> getDivision() {
        return division;
    }
}
