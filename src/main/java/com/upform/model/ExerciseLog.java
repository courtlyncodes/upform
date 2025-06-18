package com.upform.model;

import jakarta.persistence.*;

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

    public ExerciseLog() {}

    // can add later:
        // repsInReserve (optional but 🔥 for progressive overload logic)
        //tempo (if you want to get fancy later)
    public ExerciseLog(
            Long id,
            String exerciseName,
            Integer sets,
            Integer reps,
            Double weight,
            Integer exertion,
            WorkoutSession workoutSession
    ) {
        this.id = id;
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.exertion = exertion;
        this.workoutSession = workoutSession;
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

    public WorkoutSession getWorkoutSession() { return workoutSession; }
    public void setWorkoutSession(WorkoutSession workoutSession) { this.workoutSession = workoutSession; }

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
