package com.upform.service;

import com.upform.exception.UserNotFoundException;
import com.upform.exception.WorkoutSessionNotFoundException;
import com.upform.model.User;
import com.upform.model.WorkoutSession;
import com.upform.repository.UserRepository;
import com.upform.repository.WorkoutSessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutSessionService {

    private static final Logger logger = LoggerFactory.getLogger(WorkoutSessionService.class);

    private final WorkoutSessionRepository workoutSessionRepository;
    private final UserRepository userRepository;

    // Constructor injection
    public WorkoutSessionService(WorkoutSessionRepository workoutSessionRepository, UserRepository userRepository) {
        this.workoutSessionRepository = workoutSessionRepository;
        this.userRepository = userRepository;
    }

    // Get all workout sessions
    // TODO: Lock this endpoint behind admin privileges before going to prod
    public List<WorkoutSession> getAllWorkoutSessions() {
        logger.info("Fetching all workout sessions");
        return workoutSessionRepository.findAll();
    }

    // Get one workout session by id
    // TODO: Lock this endpoint behind admin privileges before going to prod
    public WorkoutSession getWorkoutSessionById(Long id) {
        logger.info("Fetching workout session with id {}", id);
        return workoutSessionRepository.findById(id)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(id));
    }

    // Get all workout sessions by user
    public List<WorkoutSession> getAllWorkoutSessionsByUserId(Long userId) {
        return workoutSessionRepository.findAllByUserId(userId);
    }

    // Get one session by user
    public WorkoutSession getWorkoutSessionByIdAndUserId(Long sessionId, Long userId) {
        return workoutSessionRepository.findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));
    }

    // Create a workout session
    public WorkoutSession createWorkoutSession(WorkoutSession session, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (session.getDurationInMinutes() != null && session.getDurationInMinutes() < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }

        session.setUser(user);
        return workoutSessionRepository.save(session);
    }

    // Update a workout session
    public WorkoutSession updateWorkoutSession(Long sessionId, Long userId, WorkoutSession updatedSession) {
        WorkoutSession existingSession = workoutSessionRepository
                .findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));

        if (updatedSession.getDate() != null) {
            existingSession.setDate(updatedSession.getDate());
        }
        
        if (updatedSession.getDurationInMinutes() != null) {
            existingSession.setDurationInMinutes(updatedSession.getDurationInMinutes());
        }

        if (updatedSession.getNotes() != null) {
            existingSession.setNotes(updatedSession.getNotes());
        }

        if (updatedSession.getDifficulty() != null) {
            existingSession.setDifficulty(updatedSession.getDifficulty());
        }

        return workoutSessionRepository.save(existingSession);
    }

    // Delete all workout sessions
    // TODO: Lock this endpoint behind admin privileges before going to prod, add deleted flag instead for sessions
    public void deleteAllWorkoutSessions() {
       workoutSessionRepository.deleteAll();
    }

    // Delete a workout session
    // TODO: Lock this endpoint behind admin privileges before going to prod
    public void deleteWorkoutSession(Long sessionId) {
        WorkoutSession session = workoutSessionRepository.findById(sessionId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));

        workoutSessionRepository.delete(session);
    }

    // Delete a workout session by user
    public void deleteWorkoutSessionByUserId(Long sessionId, Long userId) {
        WorkoutSession session = workoutSessionRepository
                .findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));

        workoutSessionRepository.delete(session);
    }
}
