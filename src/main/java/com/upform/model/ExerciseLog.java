package com.upform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "exercise_log")
public class ExerciseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String exerciseName;
    private Integer sets;
    private Integer reps;
    private Double weight;
    private Integer exertion;

    @ManyToOne()
    @JoinColumn(name = "workout_session_id")
    private WorkoutSession workoutSession;

    // can add later:
        // repsInReserve (optional but 🔥 for progressive overload logic)
        //tempo (if you want to get fancy later)

    @Override
    public String toString() {
        return "ExerciseLog{" +
                "id=" + id +
                ", exerciseName='" + exerciseName + '\'' +
                ", sets=" + sets +
                ", reps=" + reps +
                ", weight=" + weight +
                ", exertion=" + exertion +
                ", workoutSessionId=" + (workoutSession != null ? workoutSession.getId() : null) +
                '}';
    }
}
