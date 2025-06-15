package com.upform.model;

public class WorkoutLog {
    private String exerciseName;
    private int sets;
    private int reps;
    private double weight;

    public WorkoutLog() {}

    public WorkoutLog(String exerciseName, int sets, int reps, double weight) {
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    // Getters and setters
    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
}
