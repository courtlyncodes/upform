package com.upform.controller;

import com.upform.model.WorkoutLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workout")
public class WorkoutController {

    @GetMapping
    public WorkoutLog getSampleWorkout() {
        return new WorkoutLog("Hip Thrust", 4, 12, 185.0);
    }

    @PostMapping
    public String logWorkout(@RequestBody WorkoutLog log) {
        System.out.println("Received workout: " + log.getExerciseName() + ", " + log.getWeight() + " lbs");
        return "Workout received";
    }
}
