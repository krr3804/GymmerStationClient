package com.gymmer.gymmerstation.domain;

import java.util.List;

public class Division {
    Integer number;
    List<Exercise> exerciseList;
    public Division(Integer number, List<Exercise> exerciseList) {
        this.number = number;
        this.exerciseList = exerciseList;
    }

    public Integer getNumber() {
        return number;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }
}
