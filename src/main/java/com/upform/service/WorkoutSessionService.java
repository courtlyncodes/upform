package com.upform.service;

import com.upform.dto.ExerciseLogDto;
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
    public List<WorkoutSessionDto> getAllWorkoutSessions() {
        logger.info("Fetching all workout sessions");

        List<WorkoutSession> allWorkoutSessions = workoutSessionRepository.findAll();

        List<WorkoutSessionDto> dtoList = allWorkoutSessions.stream()
                        .map(this::convertToDto)
                        .toList();

        logger.info("Retrieved {} workout sessions", dtoList.size());
        return dtoList;
    }

    // Get one workout session by id
    // TODO: Lock this endpoint behind admin privileges before going to prod
    public WorkoutSessionDto getWorkoutSessionById(Long id) {
        logger.info("Fetching workout session with id {}", id);

        WorkoutSession existingWorkoutSession = workoutSessionRepository.findById(id)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(id));

        WorkoutSessionDto retrievedWorkoutSession = convertToDto(existingWorkoutSession);
        logger.info("Retrieved single workout session for session {}", retrievedWorkoutSession.getId());
        return retrievedWorkoutSession;
    }

    // Get all workout sessions by user
    public List<WorkoutSessionDto> getAllWorkoutSessionsByUserId(Long userId) {
        logger.info("Fetching all workout sessions for userId: {}", userId);
        List<WorkoutSession> existingWorkoutSessions = workoutSessionRepository.findAllByUserId(userId);

        List<WorkoutSessionDto> dtoList = existingWorkoutSessions.stream()
                        .map(this::convertToDto)
                        .toList();

        logger.info("Retrieved all {} workout sessions for userId: {}", dtoList.size(), userId);
        return dtoList;
    }

    // Get one session by user
    public WorkoutSessionDto getWorkoutSessionByIdAndUserId(Long sessionId, Long userId) {
        logger.info("Fetching workout session for sessionId: {}, userId: {}", sessionId, userId);
        WorkoutSession existingWorkoutSession = workoutSessionRepository.findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new WorkoutSessionNotFoundException(sessionId));

        WorkoutSessionDto retrievedWorkoutSession = convertToDto(existingWorkoutSession);
        logger.info("Retrieved workout session for sessionId: {}, user: {}", retrievedWorkoutSession.getId(), userId);
        return retrievedWorkoutSession;
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

    private WorkoutSessionDto convertToDto(WorkoutSession session) {
        WorkoutSessionDto dto = new WorkoutSessionDto();

        if (session == null) {
            throw new IllegalArgumentException("WorkoutSession cannot be null");
        }

        if (session.getDate() != null) {
            dto.setDate(session.getDate());
        }

        if (session.getDifficulty() != null) {
            dto.setDifficulty(session.getDifficulty());
        }

        if (session.getDurationInMinutes() != null) {
            dto.setDurationInMinutes(session.getDurationInMinutes());
        }

        if (session.getNotes() != null) {
            dto.setNotes(session.getNotes());
        }

        if (session.getExercises() != null && !session.getExercises().isEmpty()) {
            List<ExerciseLogDto> exerciseLogDtos = session.getExercises().stream().map(log-> {
                ExerciseLogDto logDto = new ExerciseLogDto();
                logDto.setExerciseName(log.getExerciseName());
                logDto.setSets(log.getSets());
                logDto.setReps(log.getReps());
                logDto.setWeight(log.getWeight());
                logDto.setExertion(log.getExertion());
                return logDto;
            }).toList();

            dto.setExercises(exerciseLogDtos);
        }
        return dto;
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
                log.setExertion(exerciseDto.getExertion());
                log.setWorkoutSession(workoutSession); // important for the FK relationship
                return log;
            }).toList();

            workoutSession.setExercises(exerciseLogs);
        }

        logger.debug("Mapped WorkoutSessionDto to WorkoutSession entity for userId={}", workoutSession.getUser().getId());
    }
}
