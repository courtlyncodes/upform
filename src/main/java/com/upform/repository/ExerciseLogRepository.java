package com.upform.repository;

import com.upform.model.ExerciseLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseLogRepository extends JpaRepository<ExerciseLog, Long> {
    List<ExerciseLog> findAllByUserId(Long userId);
    Optional<ExerciseLog> findByIdAndWorkoutSessionIdAndWorkoutSession_User_Id(Long logId, Long sessionId, Long userId);

}
