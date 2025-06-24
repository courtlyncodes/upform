package com.upform.exception;

public class ExerciseLogNotFoundException extends RuntimeException {
    public ExerciseLogNotFoundException(Long id) {
        super("ExerciseLog with ID " + id + " not found.");
    }
}
