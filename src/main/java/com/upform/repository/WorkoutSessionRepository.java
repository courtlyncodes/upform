package com.upform.repository;

import com.upform.model.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long> {
    List<WorkoutSession> findAllByUserId(Long userId);

    Optional<WorkoutSession> findByIdAndUserId(Long sessionId, Long userId);
}
