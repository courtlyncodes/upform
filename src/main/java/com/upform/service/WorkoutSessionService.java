package com.upform.service;

import com.upform.dto.WorkoutSessionDto;
import com.upform.exception.UserNotFoundException;
import com.upform.exception.WorkoutSessionNotFoundException;
import com.upform.model.ExerciseLog;
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

        List<WorkoutSession> allWorkoutSessions = workoutSessionRepository.findAll();
        logger.info("Retrieved all workout sessions");
        return allWorkoutSessions;
    }

    // Get one workout session by id
    // TODO: Lock this endpoint behind admin privileges before going to prod
    public WorkoutSession getWorkoutSessionById(Long id) {
        logger.info("Fetching workout session with id {}", id);

        WorkoutSession existingWorkoutSession = workoutSessionRepository.findById(id)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(id));

        logger.info("Retrieved single workout session for session {}", existingWorkoutSession.getId());
        return existingWorkoutSession;
    }

    // Get all workout sessions by user
    public List<WorkoutSession> getAllWorkoutSessionsByUserId(Long userId) {
        logger.info("Fetching all workout sessions for userId: {}", userId);
        List<WorkoutSession> existingWorkoutSessions = workoutSessionRepository.findAllByUserId(userId);

        logger.info("Retrieved all {} workout sessions for userId: {}", existingWorkoutSessions.size(), userId);
        return existingWorkoutSessions;
    }

    // Get one session by user
    public WorkoutSession getWorkoutSessionByIdAndUserId(Long sessionId, Long userId) {
        logger.info("Fetching workout session for sessionId: {}, userId: {}", sessionId, userId);
        WorkoutSession existingWorkoutSession = workoutSessionRepository.findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));

        logger.info("Retrieved workout session for sessionId: {}, user: {}", existingWorkoutSession.getId(), existingWorkoutSession.getUser());
        return existingWorkoutSession;
    }

    // Create a workout session
    public WorkoutSession createWorkoutSession(WorkoutSessionDto dto, Long userId) {
        logger.info("Creating workout session for userId: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        WorkoutSession workoutSession = new WorkoutSession();

        workoutSession.setUser(user);
        mapWorkoutSessionDtoToEntity(dto, workoutSession);


        WorkoutSession newWorkoutSession = workoutSessionRepository.save(workoutSession);
        logger.info("Created new workout session with ID: {}", newWorkoutSession.getId());
        return newWorkoutSession;
    }

    // Update a workout session
    public WorkoutSession updateWorkoutSession(WorkoutSessionDto dto, Long sessionId, Long userId) {
        WorkoutSession existingSession = workoutSessionRepository
                .findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));

        mapWorkoutSessionDtoToEntity(dto, existingSession);

        return workoutSessionRepository.save(existingSession);
    }

    private void mapWorkoutSessionDtoToEntity(WorkoutSessionDto dto, WorkoutSession workoutSession) {
        if (dto == null) {
            throw new IllegalArgumentException("WorkoutSessionDto cannot be null");
        }

        if (dto.getDate() != null) {
            workoutSession.setDate(dto.getDate());
        }

        if (dto.getDifficulty() != null) {
            workoutSession.setDifficulty(dto.getDifficulty());
        }

        if (dto.getDurationInMinutes() != null) {
            workoutSession.setDurationInMinutes(dto.getDurationInMinutes());
        }

        if (dto.getNotes() != null) {
            workoutSession.setNotes(dto.getNotes());
        }

        if (dto.getExercises() != null && !dto.getExercises().isEmpty()) {
            List<ExerciseLog> exerciseLogs = dto.getExercises().stream().map(exerciseDto -> {
                ExerciseLog log = new ExerciseLog();
                log.setExerciseName(exerciseDto.getExerciseName());
                log.setSets(exerciseDto.getSets());
                log.setReps(exerciseDto.getReps());
                log.setWeight(exerciseDto.getWeight());
                log.setWorkoutSession(workoutSession); // important for the FK relationship
                return log;
            }).toList();

            workoutSession.setExercises(exerciseLogs);
        }

        logger.debug("Mapped WorkoutSessionDto to WorkoutSession entity for userId={}", workoutSession.getUser().getId());
    }
    // Delete all workout sessions
    // TODO: Lock this endpoint behind admin privileges before going to prod, add deleted flag instead for sessions
    public void deleteAllWorkoutSessions() {
        logger.info("Attempting to delete all workout sessions");
        workoutSessionRepository.deleteAll();
        logger.info("Successfully deleted all workout sessions");
    }

    // Delete a workout session
    // TODO: Lock this endpoint behind admin privileges before going to prod
    public void deleteWorkoutSession(Long sessionId) {
        logger.info("Attempting to delete workout session with ID: {}", sessionId);
        WorkoutSession session = workoutSessionRepository.findById(sessionId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));

        workoutSessionRepository.delete(session);
        logger.info("Successfully deleted workout session with ID: {}", session.getId());
    }

    // Delete a workout session by user
    public void deleteWorkoutSessionByUserId(Long sessionId, Long userId) {
        logger.info("Attempting to delete workout session for sessionId: {}, userId: {}", sessionId, userId);
        WorkoutSession session = workoutSessionRepository
                .findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));

        workoutSessionRepository.delete(session);
        logger.info("Successfully deleted workout session with sessionId: {}, userId: {}", session.getId(), session.getUser());
    }
}
