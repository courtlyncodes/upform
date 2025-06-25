package com.upform.service;

import com.upform.dto.ExerciseLogDto;
import com.upform.exception.ExerciseLogNotFoundException;
import com.upform.exception.UserNotFoundException;
import com.upform.exception.WorkoutSessionNotFoundException;
import com.upform.model.ExerciseLog;
import com.upform.model.User;
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
    private final UserRepository userRepository;

    // Constructor injection
    public ExerciseLogService(ExerciseLogRepository exerciseLogRepository, WorkoutSessionRepository workoutSessionRepository, UserRepository userRepository) {
        this.exerciseLogRepository = exerciseLogRepository;
        this.workoutSessionRepository = workoutSessionRepository;
        this.userRepository = userRepository;
    }

    // Get all exercise logs by one user
    // TODO: Lock this endpoint behind admin privileges before going to prod
    public List<ExerciseLogDto> getAllExerciseLogsByUser(Long userId) {
        logger.info("Fetching all exercise logs by user with ID {}", userId);
        List<ExerciseLog> allExerciseLogs = exerciseLogRepository.findAllByUserId(userId);

        logger.info("Retrieved all {} exercise logs", allExerciseLogs.size());

        return allExerciseLogs.stream()
                .map(this::convertToDto)
                .toList();
    }

    // Get all exercise logs by workout session by user
    public List<ExerciseLogDto> getAllExerciseLogsBySessionAndUser(Long sessionId, Long userId) {
        logger.info("Fetching all exercise logs with sessionId {}", sessionId);
        WorkoutSession session = workoutSessionRepository.findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));

        List<ExerciseLog> allExerciseLogs = session.getExercises();
        logger.info("Retrieved all {} exercise logs for session: {}, user: {}", allExerciseLogs.size(), session.getId(), session.getUser().getId());

        return allExerciseLogs.stream()
                .map(this::convertToDto)
                .toList();
    }

    // Get one exercise log by workout session by user
    public ExerciseLogDto getOneExerciseLogBySessionAndUser(Long sessionId, Long userId, Long logId) {
        logger.info("Fetching single exercise log with Id {} for sessionId: {}, userId: {}", logId, sessionId, userId);
        ExerciseLog log = exerciseLogRepository.findByIdAndWorkoutSessionIdAndWorkoutSession_User_Id(logId, sessionId, userId)
                .orElseThrow(() -> new ExerciseLogNotFoundException(logId));

        logger.info("Retrieved single exercise log with Id {} for sessionId: {}, userId: {}", log.getId(), sessionId, userId);
        return convertToDto(log);
    }

    // Create exercise log by workout session by user
    public ExerciseLogDto createExerciseLogBySessionAndUser(ExerciseLogDto dto, Long sessionId, Long userId) {
        logger.info("Creating exercise log for sessionId: {}, userId: {}", sessionId, userId);
        WorkoutSession existingSession = workoutSessionRepository.findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));

        ExerciseLog exerciseLog = new ExerciseLog();

        exerciseLog.setUser(existingSession.getUser());
        exerciseLog.setWorkoutSession(existingSession);
        mapExerciseLogDtoToEntity(dto, exerciseLog);

        ExerciseLog savedLog = exerciseLogRepository.save(exerciseLog);

        logger.info("Created exercise log with ID: {}", savedLog.getId());
        return convertToDto(savedLog);
    }

    // Update exercise log by workout session by user
    public ExerciseLogDto updateExerciseLogBySessionAndUser(ExerciseLogDto dto, Long sessionId, Long userId, Long logId) {
        logger.info("Updating exercise log for sessionId: {}, userId: {}", sessionId, userId);

        ExerciseLog log = exerciseLogRepository.findByIdAndWorkoutSessionIdAndWorkoutSession_User_Id(logId, sessionId, userId)
                .orElseThrow(() -> new ExerciseLogNotFoundException(logId));

        mapExerciseLogDtoToEntity(dto, log);

        ExerciseLog savedLog = exerciseLogRepository.save(log);

        logger.info("Updated exercise log with ID: {}", savedLog.getId());
        return convertToDto(savedLog);
    }

    // Delete one exercise log by workout session by user
    public void deleteExerciseLogBySessionAndUser(Long sessionId, Long userId, Long logId) {
        logger.info("Attempting to delete exercise log with ID: {}", logId);

        ExerciseLog log = exerciseLogRepository.findByIdAndWorkoutSessionIdAndWorkoutSession_User_Id(logId, sessionId, userId)
                .orElseThrow(() -> new ExerciseLogNotFoundException(logId));

        exerciseLogRepository.delete(log);
        logger.info("Successfully deleted exercise log with ID: {}", log.getId());
    }

    // Converts an exercise log to a DTO
    private ExerciseLogDto convertToDto(ExerciseLog exerciseLog) {
        if (exerciseLog == null) {
            throw new IllegalArgumentException("ExerciseLog cannot be null");
        }
        ExerciseLogDto dto = new ExerciseLogDto();

        dto.setId(exerciseLog.getId());
        dto.setExerciseName(exerciseLog.getExerciseName());

        if (exerciseLog.getSets() != null) {
            dto.setSets(exerciseLog.getSets());
        }

        if (exerciseLog.getReps() != null) {
            dto.setReps(exerciseLog.getReps());
        }

        if (exerciseLog.getWeight() != null) {
            dto.setWeight(exerciseLog.getWeight());
        }

        if (exerciseLog.getExertion() != null) {
            dto.setExertion(exerciseLog.getExertion());
        }
        logger.debug("Successfully converted ExerciseLog with ID: {} to DTO", exerciseLog.getId());
        return dto;
    }

    private void mapExerciseLogDtoToEntity(ExerciseLogDto dto, ExerciseLog exerciseLog) {
        if (dto == null) {
            throw new IllegalArgumentException("ExerciseLogDto cannot be null");
        }

        exerciseLog.setExerciseName(dto.getExerciseName());

        if (dto.getSets() != null) {
            exerciseLog.setSets(dto.getSets());

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
    }
}
