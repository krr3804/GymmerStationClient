package com.gymmer.gymmerstation.domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Program {
    String name;
    String purpose;
    Long length;
    Map<Integer,List<Exercise>> exerciseMap;

    public Program() {}

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

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Program other = (Program)o;

        return other.name == name && other.purpose == purpose && other.length == length && other.exerciseMap == exerciseMap;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,purpose,length,exerciseMap);
    }
}
