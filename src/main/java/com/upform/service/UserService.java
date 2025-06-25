package com.upform.service;

import com.upform.dto.ExerciseLogDto;
import com.upform.dto.UserDto;
import com.upform.dto.WorkoutSessionDto;
import com.upform.exception.UserNotFoundException;
import com.upform.model.ExerciseLog;
import com.upform.model.User;
import com.upform.model.WorkoutSession;
import com.upform.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;

    // Constructor injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //Get one user by ID
    public UserDto getUserById(Long id) {
        logger.info("Fetching user with ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        logger.info("Retrieved user with ID: {}", user.getId());

        return convertToDto(user);
    }

    //Get all users
    // TODO: Lock this endpoint behind admin privileges before going to prod
    public List<UserDto> getAllUsers() {
        logger.info("Fetching all users");
        List<User> users = userRepository.findAll();
        logger.info("Retrieved {} users", users.size());

        List<UserDto> dtoList = users.stream()
                .map(this::convertToDto)
                .toList();
        return dtoList;
    }

    // Create a user
    public UserDto createUser(UserDto dto) {
        logger.info("Creating user");
        User user = new User();

        mapUserDtoToEntity(dto, user);

        User newUser = userRepository.save(user);
        logger.info("Created user with id: {}", newUser.getId());

        return convertToDto(newUser);
    }

    // Update a user by id
    public UserDto updateUserById(Long userId, UserDto dto) {
        logger.info("Updating user with ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        mapUserDtoToEntity(dto, user);

        User updatedUser = userRepository.save(user);
        logger.info("Successfully updated user with ID: {}", updatedUser.getId());

        return convertToDto(updatedUser);
    }

    // Delete a user by ID
    public void deleteUser(Long userId) {
        logger.info("Attempting to delete user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        userRepository.delete(user);
        logger.info("Successfully deleted user with ID: {}", user.getId());
    }


    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        dto.setId(user.getId());

        if (user.getName() != null) {
            dto.setName(user.getName());
        }

        if (user.getEmail() != null) {
            dto.setEmail(user.getEmail());
        }

        if (user.getExperienceLevel() != null) {
            dto.setExperienceLevel(user.getExperienceLevel());
        }

        if (user.getGoal() != null) {
            dto.setGoal(user.getGoal());
        }

        if (user.getJoinedDate() != null) {
            dto.setJoinedDate(user.getJoinedDate());
        }

        if (user.getWorkoutSessionList() != null && !user.getWorkoutSessionList().isEmpty()) {
            List<WorkoutSessionDto> workoutSessionDtos = user.getWorkoutSessionList().stream().map(session -> {
                WorkoutSessionDto sessionDto = new WorkoutSessionDto();
                sessionDto.setId(session.getId());
                sessionDto.setDate(session.getDate());
                sessionDto.setDifficulty(session.getDifficulty());
                sessionDto.setDurationInMinutes(session.getDurationInMinutes());
                sessionDto.setNotes(session.getNotes());

                // Convert exercise log list to dto
                List<ExerciseLogDto> exerciseLogDtos = session.getExercises().stream().map(log -> {
                    ExerciseLogDto logDto = new ExerciseLogDto();
                    logDto.setId(log.getId());
                    logDto.setExerciseName(log.getExerciseName());
                    logDto.setSets(log.getSets());
                    logDto.setReps(log.getReps());
                    logDto.setWeight(log.getWeight());
                    logDto.setExertion(log.getExertion());
                    return logDto;
                }).toList();

                sessionDto.setExercises(exerciseLogDtos);

                return sessionDto;
            }).toList();

            dto.setWorkoutSessionList(workoutSessionDtos);
        }
            return dto;
    }

    // Maps a user DTO to a User
    private void mapUserDtoToEntity(UserDto dto, User user) {
        if (dto == null) {
            throw new IllegalArgumentException("UserDto cannot be null");
        }

        if (dto.getName() != null) {
            user.setName(dto.getName());
        }

        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getExperienceLevel() != null) {
            user.setExperienceLevel(dto.getExperienceLevel());
        }

        if (dto.getGoal() != null) {
            user.setGoal(dto.getGoal());
        }

        if (dto.getJoinedDate() != null) {
            user.setJoinedDate(dto.getJoinedDate());
        }

        if (dto.getWorkoutSessionList() != null && !dto.getWorkoutSessionList().isEmpty()) {
            List<WorkoutSession> workoutSessions = dto.getWorkoutSessionList().stream().map(sessionDto -> {
                WorkoutSession newWorkoutSession = new WorkoutSession();
                newWorkoutSession.setDate(sessionDto.getDate());
                newWorkoutSession.setDifficulty(sessionDto.getDifficulty());
                newWorkoutSession.setDurationInMinutes(sessionDto.getDurationInMinutes());
                newWorkoutSession.setNotes(sessionDto.getNotes());

                List<ExerciseLog> exerciseLogs = sessionDto.getExercises().stream().map(logDto -> {
                    ExerciseLog log = new ExerciseLog();
                    log.setId(logDto.getId());
                    log.setExerciseName(logDto.getExerciseName());
                    log.setSets(logDto.getSets());
                    log.setReps(logDto.getReps());
                    log.setWeight(logDto.getWeight());
                    log.setExertion(logDto.getExertion());
                    log.setWorkoutSession(newWorkoutSession);
                    return log;
                }).toList();

                newWorkoutSession.setUser(user);
                newWorkoutSession.setExercises(exerciseLogs);

                return newWorkoutSession;
            }).toList();

            user.setWorkoutSessionList(workoutSessions);
        }
    }
}
