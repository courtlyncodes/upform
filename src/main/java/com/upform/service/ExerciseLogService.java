package com.upform.service;

import com.upform.dto.ExerciseLogDto;
import com.upform.exception.WorkoutSessionNotFoundException;
import com.upform.model.ExerciseLog;
import com.upform.model.WorkoutSession;
import com.upform.repository.ExerciseLogRepository;
import com.upform.repository.UserRepository;
import com.upform.repository.WorkoutSessionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ExerciseLogService {
    private static final Logger logger = LoggerFactory.getLogger(ExerciseLogService.class);

    private final ExerciseLogRepository exerciseLogRepository;
    private final WorkoutSessionRepository workoutSessionRepository;

    // Constructor injection
    public ExerciseLogService(ExerciseLogRepository exerciseLogRepository,WorkoutSessionRepository workoutSessionRepository, UserRepository userRepository) {
        this.exerciseLogRepository = exerciseLogRepository;
        this.workoutSessionRepository = workoutSessionRepository;
    }

    // Get all exercise logs by one user
    // TODO: Lock this endpoint behind admin privileges before going to prod
    public List<ExerciseLog> getAllExerciseLogs() {
        logger.info("Fetching all exercise logs");

        List<ExerciseLog> allExerciseLogs =  exerciseLogRepository.findAll();
        logger.info("Retrieved all {} exercise logs", allExerciseLogs.size());
        return allExerciseLogs;
    }

    // Get all exercise logs by workout session by user
    public List<ExerciseLog> getAllExerciseLogsBySessionAndUser(Long sessionId, Long userId) {
        logger.info("Fetching all exercise logs with sessionId {}", sessionId);
        WorkoutSession session = workoutSessionRepository.findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));

        List<ExerciseLog> allExerciseLogs = session.getExercises();
        logger.info("Retrieved all {} exercise logs for session: {}, user: {}", allExerciseLogs.size(), session.getId(), session.getUser());
        return allExerciseLogs;
    }

    // Get one exercise log by workout session by user
    public ExerciseLog getOneExerciseLogBySessionAndUser(int exerciseLogIndex, Long sessionId, Long userId) {
        logger.info("Fetching single exercise log at index {} for sessionId: {}, userId: {}", exerciseLogIndex, sessionId, userId);
        WorkoutSession existingSession = workoutSessionRepository.findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));

        if (exerciseLogIndex < 0 || exerciseLogIndex >= existingSession.getExercises().size()) {
            logger.warn("Invalid exercise log index for get: {}", exerciseLogIndex);
            throw new IndexOutOfBoundsException("Invalid exercise log index");
        }

        ExerciseLog existingExerciseLog = existingSession.getExercises().get(exerciseLogIndex);
        logger.info("Retrieved single exercise log at index {} for sessionId: {}, userId: {}", exerciseLogIndex, sessionId, userId);
        return existingExerciseLog;
    }

    // Create exercise log by workout session by user
    public ExerciseLog createExerciseLogBySessionAndUser(ExerciseLogDto dto, Long sessionId, Long userId) {
        logger.info("Creating exercise log for sessionId: {}, userId: {}", sessionId, userId);
        WorkoutSession existingSession = workoutSessionRepository.findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));

        ExerciseLog exerciseLog = new ExerciseLog();

        exerciseLog.setWorkoutSession(existingSession);
        mapExerciseLogDtoToEntity(dto, exerciseLog);

        ExerciseLog savedLog = exerciseLogRepository.save(exerciseLog);
        logger.info("Created exercise log with ID: {}", savedLog.getId());
        return savedLog;
    }

    // Update exercise log by workout session by user
    public ExerciseLog updateExerciseLogBySessionAndUser(ExerciseLogDto dto, int exerciseLogIndex, Long sessionId, Long userId) {
        logger.info("Updating exercise log for sessionId: {}, userId: {}", sessionId, userId);
        WorkoutSession existingSession = workoutSessionRepository.findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));

        if (exerciseLogIndex < 0 || exerciseLogIndex >= existingSession.getExercises().size()) {
            logger.warn("Invalid exercise log index for update: {}", exerciseLogIndex);
            throw new IndexOutOfBoundsException("Invalid exercise log index");
        }

        ExerciseLog existingExerciseLog = existingSession.getExercises().get(exerciseLogIndex);

        mapExerciseLogDtoToEntity(dto, existingExerciseLog);

        ExerciseLog savedLog = exerciseLogRepository.save(existingExerciseLog);
        logger.info("Updated exercise log with ID: {}", savedLog.getId());
        return savedLog;
    }

    private void mapExerciseLogDtoToEntity(ExerciseLogDto dto, ExerciseLog exerciseLog) {
        if (dto == null) {
            throw new IllegalArgumentException("ExerciseLogDto cannot be null");
        }

        exerciseLog.setExerciseName(dto.getExerciseName());

        if (dto.getSets() != null) {
            exerciseLog.setSets(dto.getSets());
        }

        if (dto.getReps() != null) {
            exerciseLog.setReps(dto.getReps());
        }

        if (dto.getWeight() != null) {
            exerciseLog.setWeight(dto.getWeight());
        }

        if (dto.getExertion() != null) {
            exerciseLog.setExertion(dto.getExertion());
        }

        logger.debug("Applied DTO to ExerciseLog entity: {}", exerciseLog);
    }

    // Delete one workout log by workout session by user
    public void deleteWorkoutLogBySessionAndUser(int exerciseLogIndex, Long sessionId, Long userId) {
        logger.info("Attempting to delete exercise log for sessionId: {}, userId: {}", sessionId, userId);
        WorkoutSession existingSession = workoutSessionRepository.findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));

        if (exerciseLogIndex < 0 || exerciseLogIndex >= existingSession.getExercises().size()) {
            logger.warn("Invalid exercise log index for delete: {}", exerciseLogIndex);
            throw new IndexOutOfBoundsException("Invalid exercise log index");
        }

        ExerciseLog existingLog = existingSession.getExercises().get(exerciseLogIndex);

        exerciseLogRepository.delete(existingLog);
        logger.info("Successfully deleted exercise log with ID: {}", existingLog.getId());
        }
}
