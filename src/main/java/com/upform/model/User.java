package com.upform.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name= "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WorkoutSession> workoutSessionList = new ArrayList<>();
    private String authId;
    private String name;
    private String email;
    private String experienceLevel;
    private String goal;
    private LocalDate joinedDate;

    private User() {}

    public User (
        Long id,
        List<WorkoutSession> workoutSessionList,
        String authId,
        String name,
        String email,
        String experienceLevel,
        String goal,
        LocalDate joinedDate
    ) {
        this.id = id;
        this.workoutSessionList = workoutSessionList;
        this.authId = authId;
        this.name = name;
        this.email = email;
        this.experienceLevel = experienceLevel;
        this.goal = goal;
        this.joinedDate = joinedDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public List<WorkoutSession> getWorkoutSessionList() { return workoutSessionList; }
    public void setWorkoutSessionList(List<WorkoutSession> workoutSessionList) { this.workoutSessionList = workoutSessionList; }

    public String getAuthId() { return authId; }
    public void setAuthId(String authId) { this.authId = authId; }

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
}
