package com.gymmer.gymmerstation.domain;

import java.util.List;
import java.util.Map;

public class Program {
    String name;
    String purpose;
    Long length;
    Map<Integer,List<Exercise>> exerciseMap;

    public Program(String name, String purpose, Long length, Map<Integer,List<Exercise>> exerciseMap) {
        this.name = name;
        this.purpose = purpose;
        this.length = length;
        this.exerciseMap = exerciseMap;
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

    public Map<Integer, List<Exercise>> getExerciseMap() {
        return exerciseMap;
    }
}
