package org.example.bet.repository;

import org.example.bet.domain.Event;
import org.example.bet.domain.Prediction;
import org.example.bet.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    boolean existsByUserAndEvent(User user, Event event);
}