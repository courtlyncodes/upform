package com.upform.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkoutSessionDto {

    @JsonIgnore
    private Long id;

    private LocalDate date;
    private String difficulty; // easy, moderate, hard

    @Min(1)
    private Integer durationInMinutes;

    @Size(max = 1000, message = "Notes must be under 1000 characters")
    private String notes;

    private List<@Valid ExerciseLogDto> exercises = new ArrayList<>();

    public WorkoutSessionDto() {}

    public WorkoutSessionDto(
            Long id,
            LocalDate date,
            String difficulty,
            Integer durationInMinutes,
            String notes,
            List<ExerciseLogDto> exercises
    ) {
        this.id = id;
        this.date = date;
        this.difficulty = difficulty;
        this.durationInMinutes = durationInMinutes;
        this.notes = notes;
        this.exercises = exercises;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public Integer getDurationInMinutes() { return durationInMinutes; }
    public void setDurationInMinutes(Integer durationInMinutes) { this.durationInMinutes = durationInMinutes; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public List<ExerciseLogDto> getExercises() { return exercises; }
    public void setExercises(List<ExerciseLogDto> exercises) { this.exercises = exercises; }
}
