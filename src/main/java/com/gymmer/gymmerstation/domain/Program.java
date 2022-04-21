package com.gymmer.gymmerstation.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Program {
    private Long id;
    private String name;
    private String purpose;
    private Long length;
    private List<Exercise> exerciseList;

    public Program(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public Program(Long id, String name, String purpose, Long length,List<Exercise> exerciseList) {
        this.id = id;
        this.name = name;
        this.purpose = purpose;
        this.length = length;
        this.exerciseList = exerciseList;
    }

    public Long getId() {
        return id;
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

    public Exercise removeExercise(Long division, String name) {
        Exercise exercise = exerciseList.stream().filter(e -> e.division.equals(division) && e.getName().equals(name)).findAny().get();
        exerciseList.remove(exercise);
        return exercise;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public int countDivision() {
        return (int)exerciseList.stream().map(Exercise::getDivision).distinct().count();
    }

    public List<Exercise> getExerciseByDivision(Long divisionNumber) {
        return exerciseList.stream().filter(exercise -> exercise.division.equals(divisionNumber)).collect(Collectors.toList());
    }

    public void removeExerciseInDivision(Long division) {
        exerciseList.removeIf(exercise -> exercise.division.equals(division));
        exerciseList.stream().filter(exercise -> exercise.division > division).forEach(exercise -> exercise.decreaseDivisionSequence(exercise.division));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setLength(Long length) {
        this.length = length;
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

        return other.name == name && other.purpose == purpose && other.length == length && other.exerciseList == exerciseList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,purpose,length,exerciseList);
    }
}
