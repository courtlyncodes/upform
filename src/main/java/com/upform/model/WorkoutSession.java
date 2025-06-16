package com.upform.model;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public class WorkoutSession {
    private long id;
    private LocalDate date;
    private List<ExerciseLog> exercises;
    private String difficulty; // easy, moderate, hard
    private int durationInMinutes;
    private String notes;

    public WorkoutSession() {}

    public WorkoutSession(
            long id,
            LocalDate date,
            List<ExerciseLog> exercises,
            String difficulty,
            int durationInMinutes,
            String notes
    ) {
        this.id = id;
        this.date = date;
        this.exercises = exercises;
        this.difficulty = difficulty;
        this.durationInMinutes = durationInMinutes;
        this.notes = notes;
    }

    // Getters and Setters

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public List getExercises() { return exercises; }
    public void setExercises(List<ExerciseLog> exercises) { this.exercises = exercises; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public int getDurationInMinutes() { return durationInMinutes; }
    public void setDurationInMinutes(int durationInMinutes) { this.durationInMinutes = durationInMinutes; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
