package com.upform.model;

public class ExerciseLog {
    private Long id;
    private String exerciseName;
    private int sets;
    private int reps;
    private double weight;

    public ExerciseLog() {}

    // can add later:
        // repsInReserve (optional but 🔥 for progressive overload logic)
        //tempo (if you want to get fancy later)
    public ExerciseLog(
            Long id,
            String exerciseName,
            int sets,
            int reps,
            double weight,
            int exertion
    ) {
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
}
