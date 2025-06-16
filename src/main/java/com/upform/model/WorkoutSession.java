package com.upform.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workout_sessions")
public class WorkoutSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;

    private String difficulty; // easy, moderate, hard
    private int durationInMinutes;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "workoutSession", cascade = CascadeType.ALL)
    private List<ExerciseLog> exercises = new ArrayList<>();

    public WorkoutSession() {}

    public WorkoutSession(
            long id,
            LocalDate date,
            String difficulty,
            int durationInMinutes,
            String notes,
            User user,
            List<ExerciseLog> exercises
    ) {
        this.id = id;
        this.date = date;
        this.difficulty = difficulty;
        this.durationInMinutes = durationInMinutes;
        this.notes = notes;
        this.user = user;
        this.exercises = exercises;
    }

    // Getters and Setters

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public int getDurationInMinutes() { return durationInMinutes; }
    public void setDurationInMinutes(int durationInMinutes) { this.durationInMinutes = durationInMinutes; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<ExerciseLog> getExercises() { return exercises; }
    public void setExercises(List<ExerciseLog> exercises) { this.exercises = exercises; }
}
