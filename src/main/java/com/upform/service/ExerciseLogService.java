package com.upform.service;

import com.upform.dto.ExerciseLogDTO;
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
        return exerciseLogRepository.findAll();
    }

    // Get all exercise logs by workout session by user
    public List<ExerciseLog> getAllExerciseLogsBySessionAndUser(Long sessionId, Long userId) {
        logger.info("Fetching all exercise logs with sessionId {}", sessionId);
        WorkoutSession session = workoutSessionRepository.findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));

        return session.getExercises();
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
        return existingExerciseLog;
    }

    // Create exercise log by workout session by user
    public ExerciseLog createExerciseLogBySessionAndUser(ExerciseLogDTO dto, Long sessionId, Long userId) {
        logger.info("Creating exercise log for sessionId: {}, userId: {}", sessionId, userId);
        WorkoutSession existingSession = workoutSessionRepository.findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));

        ExerciseLog exerciseLog = mapDtoToExerciseLogCreate(dto, existingSession);

        ExerciseLog savedLog = exerciseLogRepository.save(exerciseLog);
        logger.info("Created exercise log with ID: {}", savedLog.getId());
        return savedLog;
    }

    private static ExerciseLog mapDtoToExerciseLogCreate(ExerciseLogDTO dto, WorkoutSession existingSession) {
        ExerciseLog exerciseLog = new ExerciseLog();

        exerciseLog.setExerciseName(dto.getExerciseName());
        exerciseLog.setWorkoutSession(existingSession);

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
        return exerciseLog;
    }

    // Update exercise log by workout session by user
    public ExerciseLog updateExerciseLogBySessionAndUser(ExerciseLogDTO dto, int exerciseLogIndex, Long sessionId, Long userId) {
        logger.info("Updating exercise log for sessionId: {}, userId: {}", sessionId, userId);
        WorkoutSession existingSession = workoutSessionRepository.findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));


        if (exerciseLogIndex < 0 || exerciseLogIndex >= existingSession.getExercises().size()) {
            logger.warn("Invalid exercise log index for update: {}", exerciseLogIndex);
            throw new IndexOutOfBoundsException("Invalid exercise log index");
        }

        ExerciseLog existingExerciseLog = mapDtoToExerciseLogUpdate(dto, exerciseLogIndex, existingSession);

        ExerciseLog savedLog = exerciseLogRepository.save(existingExerciseLog);
        logger.info("Updated exercise log with ID: {}", savedLog.getId());
        return savedLog;
    }

    private static ExerciseLog mapDtoToExerciseLogUpdate(ExerciseLogDTO dto, int exerciseLogIndex, WorkoutSession existingSession) {
        ExerciseLog existingExerciseLog = existingSession.getExercises().get(exerciseLogIndex);

        if (dto.getExerciseName() != null) {
            existingExerciseLog.setExerciseName(dto.getExerciseName());
        }

        if (dto.getSets() != null) {
            existingExerciseLog.setSets(dto.getSets());
        }

        if (dto.getReps() != null) {
            existingExerciseLog.setReps(dto.getReps());
        }

        if (dto.getWeight() != null) {
            existingExerciseLog.setWeight(dto.getWeight());
        }

        if (dto.getExertion() != null) {
            existingExerciseLog.setExertion(dto.getExertion());
        }
        return existingExerciseLog;
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
