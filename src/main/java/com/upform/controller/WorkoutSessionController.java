package com.upform.controller;

import com.upform.model.ExerciseLog;
import com.upform.model.WorkoutSession;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/workout")
public class WorkoutSessionController {

    private final Map<Long, WorkoutSession> workoutMap = new HashMap<>();
    private long nextWorkoutId = 1 ;

    @PostMapping("/workouts")
    public WorkoutSession startWorkout(@RequestBody WorkoutSession session) {
        session.setId(nextWorkoutId++);
        session.setExercises(new ArrayList<>());
        workoutMap.put(session.getId(), session);
        return session;
    }

    @PostMapping("/workouts/{workoutId}/exercises")
    public String logExercise(@PathVariable Long workoutId, @RequestBody ExerciseLog exercise) {
        WorkoutSession session = workoutMap.get(workoutId);
        if (session == null) {
            return "Workout session not found.";
        }
        session.getExercises().add(exercise);
        return "Exercise added to workout.";
    }

    @GetMapping("workouts/{workoutId}")
    public WorkoutSession getWorkoutSession(@PathVariable Long workoutId) {
        return workoutMap.get(workoutId);
    }


//    private final List<ExerciseLog> logs = new ArrayList<>();
//
//    @GetMapping
//    public List<ExerciseLog> getAllExercises() {
//        return logs;
//    }
//
//    // maps an exercise to the workout session
//    @PostMapping
//    public ExerciseLog logExercise(@RequestBody ExerciseLog log) {
//        logs.add(log);
//        System.out.println("Received exercise: " + log.getExerciseName() + ", " + log.getWeight() + " lbs");
//        return log;
//    }
}
