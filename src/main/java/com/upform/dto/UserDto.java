package com.upform.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @JsonIgnore
    private Long id;

    private String name;
    private String email;
    private String experienceLevel;
    private String goal;
    private LocalDate joinedDate;
    private List<@Valid WorkoutSessionDto> workoutSessionList = new ArrayList<>();

    public UserDto() {}

    public UserDto (
            Long id,
            String name,
            String email,
            String experienceLevel,
            String goal,
            LocalDate joinedDate,
            List<WorkoutSessionDto> workoutSessionList
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.experienceLevel = experienceLevel;
        this.goal = goal;
        this.joinedDate = joinedDate;
        this.workoutSessionList = workoutSessionList;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }

    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }

    public LocalDate getJoinedDate() { return joinedDate; }
    public void setJoinedDate(LocalDate joinedDate) { this.joinedDate = joinedDate; }

    public List<WorkoutSessionDto> getWorkoutSessionList() { return workoutSessionList; }
    public void setWorkoutSessionList(List<WorkoutSessionDto> workoutSessionList) { this.workoutSessionList = workoutSessionList; }
}
