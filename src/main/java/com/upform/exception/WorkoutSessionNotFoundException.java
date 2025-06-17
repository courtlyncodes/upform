package com.upform.exception;

public class WorkoutSessionNotFoundException extends RuntimeException {
    public WorkoutSessionNotFoundException(Long id) {
        super("Workout session with ID " + id + " not found.");
    }
}
