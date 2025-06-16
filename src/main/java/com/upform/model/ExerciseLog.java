package com.upform.model;

import jakarta.persistence.*;

@Entity
@Table(name = "exercise_log")
public class ExerciseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String exerciseName;
    private int sets;
    private int reps;
    private double weight;
    private int exertion;

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
            int sets,
            int reps,
            double weight,
            int exertion,
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

    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public int getExertion() { return exertion; }
    public void setExertion(int exertion) { this.exertion = exertion; }

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
