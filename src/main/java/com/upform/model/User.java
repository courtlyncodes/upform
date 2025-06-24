package com.upform.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}

