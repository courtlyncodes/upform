package com.upform.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;

public class ExerciseLogDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @NotBlank
    private String exerciseName;

    @Min(1)
    private Integer sets;

    @Min(1)
    private Integer reps;

    @Min(1)
    private Double weight;

    @Min(1)
    @Max(10)
    private Integer exertion;

    public ExerciseLogDto() {}

    public ExerciseLogDto(
            Long id,
            String exerciseName,
            Integer sets,
            Integer reps,
            Double weight,
            Integer exertion
    ) {
        this.id = id;
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.exertion = exertion;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public Integer getSets() { return sets; }
    public void setSets(Integer sets) { this.sets = sets; }

    public Integer getReps() { return reps; }
    public void setReps(Integer reps) { this.reps = reps; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public Integer getExertion() { return exertion; }
    public void setExertion(Integer exertion) { this.exertion = exertion; }
}
