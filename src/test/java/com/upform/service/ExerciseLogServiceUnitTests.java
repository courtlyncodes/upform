package com.upform.service;

import com.upform.dto.ExerciseLogDto;
import com.upform.model.ExerciseLog;
import com.upform.model.User;
import com.upform.model.WorkoutSession;
import com.upform.repository.ExerciseLogRepository;
import com.upform.repository.UserRepository;
import com.upform.repository.WorkoutSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

// TODO: Add negative tests
@ExtendWith(MockitoExtension.class)
public class ExerciseLogServiceUnitTests {

    @Mock
    private ExerciseLogRepository exerciseLogRepository;

    @Mock
    private WorkoutSessionRepository workoutSessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ExerciseLogService exerciseLogService;

    @InjectMocks
    private WorkoutSessionService workoutSessionService;

    @InjectMocks
    private UserService userService;

    private ExerciseLog exerciseLog;
    private ExerciseLog exerciseLog1;
    private ExerciseLog exerciseLog2;
    private ExerciseLogDto exerciseLogDto;
    private WorkoutSession workoutSession;
    private User user;

    @BeforeEach
    public void setup() {
        exerciseLog = ExerciseLog.builder()
                .id(1L)
                .exerciseName("Romanian deadlifts")
                .sets(4)
                .reps(12)
                .weight(90.0)
                .exertion(10)
                .workoutSession(workoutSession)
                .build();

       exerciseLog1 = ExerciseLog.builder()
                .id(2L)
                .exerciseName("Squats")
                .sets(4)
                .reps(10)
                .weight(75.5)
                .exertion(7)
                .workoutSession(workoutSession)
                .build();

        exerciseLog2 = ExerciseLog.builder()
                .id(3L)
                .exerciseName("Leg press")
                .sets(4)
                .reps(8)
                .weight(100.0)
                .exertion(8)
                .workoutSession(workoutSession)
                .build();

        workoutSession = WorkoutSession.builder()
                .id(1L)
                .date(LocalDate.of(2025, 6, 25))
                .difficulty("Easy")
                .durationInMinutes(100)
                .notes("I did it")
                .build();

        user = User.builder()
                .id(1L)
                .authId("auth0")
                .name("Court")
                .email("court1@gmail.com")
                .experienceLevel("Intermediate")
                .goal("weight loss")
                .joinedDate(LocalDate.of(2025, 6, 24))
                .workoutSessionList(new ArrayList<>())
                .build();

        exerciseLogDto = ExerciseLogDto.builder()
                .id(1L)
                .exerciseName("Romanian deadlifts")
                .sets(4)
                .reps(12)
                .weight(90.0)
                .exertion(10)
                .build();
    }

    @Test
    @DisplayName("Should create a new exercise log given workout session id and user id")
    void createNewExerciseLogTest() {
        given(workoutSessionRepository.findByIdAndUserId(1L, 1L)).willReturn(Optional.of(workoutSession));
        given(exerciseLogRepository.save(any(ExerciseLog.class))).willReturn(exerciseLog);

        ExerciseLogDto result = exerciseLogService.createExerciseLogBySessionAndUser(exerciseLogDto, 1L, 1L);

        assertThat(result).isNotNull();
        assertThat(result.getExerciseName()).isEqualTo("Romanian deadlifts");
        assertThat(result.getSets()).isEqualTo(4);
        verify(exerciseLogRepository).save(any(ExerciseLog.class));
    }

    @Test
    @DisplayName("Should return all exercise logs given user id")
    void returnAllExerciseLogsByUserTest() {
        given(exerciseLogRepository.findAllByUserId(1L)).willReturn(List.of(exerciseLog, exerciseLog1));

        List<ExerciseLogDto> result = exerciseLogService.getAllExerciseLogsByUser(1L);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(1).getExerciseName()).isEqualTo("Squats");
    }

    @Test
    @DisplayName("Should return all exercise logs given workout session id and user id")
    void returnAllExerciseLogsBySessionAndUserTest() {
        workoutSession.setExercises(List.of(exerciseLog, exerciseLog1, exerciseLog2));
        workoutSession.setUser(user);

        given(workoutSessionRepository.findByIdAndUserId(1L, 1L)).willReturn(Optional.of(workoutSession));

        List<ExerciseLogDto> result = exerciseLogService.getAllExerciseLogsBySessionAndUser(1L, 1L);

        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(2).getExerciseName()).isEqualTo("Leg press");
    }

    @Test
    @DisplayName("Should return one exercise log given workout session id and user id")
    void returnExerciseLogBySessionAndUserTest() {
        given(exerciseLogRepository.findByIdAndWorkoutSessionIdAndWorkoutSession_User_Id(1L, 1L, 1L)).willReturn(Optional.of(exerciseLog));

        ExerciseLogDto result = exerciseLogService.getOneExerciseLogBySessionAndUser(1L, 1L, 1L);

        assertThat(result).isNotNull();
        assertThat(result.getReps()).isEqualTo(12);
    }

    @Test
    @DisplayName("Should update exercise log given session id and user id")
    void updateExerciseLogBySessionAndUserTest() {
        given(exerciseLogRepository.findByIdAndWorkoutSessionIdAndWorkoutSession_User_Id(1L, 1L, 1L)).willReturn(Optional.of(exerciseLog));
        given(exerciseLogRepository.save(any(ExerciseLog.class))).willReturn(exerciseLog);

        ExerciseLogDto updatedExerciseLogDto = ExerciseLogDto.builder()
                .exerciseName("Romanian deadlifts")
                .sets(4)
                .reps(12)
                .weight(96.0)
                .exertion(7)
                .build();

        ExerciseLogDto result = exerciseLogService.updateExerciseLogBySessionAndUser(updatedExerciseLogDto, 1L, 1L, 1L);

        assertThat(result.getWeight()).isEqualTo(96.0);
        assertThat(result.getExertion()).isEqualTo(7);
    }

    @Test
    @DisplayName("Should delete an exercise log given session id and user id")
    void deleteExerciseLogBySessionAndUserTest() {
        given(exerciseLogRepository.findByIdAndWorkoutSessionIdAndWorkoutSession_User_Id(1L, 1L, 1L)).willReturn(Optional.of(exerciseLog));

        exerciseLogService.deleteExerciseLogBySessionAndUser(1L, 1L, 1L);

        verify(exerciseLogRepository, times(1)).delete(exerciseLog);
    }
}
