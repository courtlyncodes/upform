package com.upform.repository;

import com.upform.model.ExerciseLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseLogRepository extends JpaRepository<ExerciseLog, Long> {
}
