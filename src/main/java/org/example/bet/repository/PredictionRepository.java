package org.example.bet.repository;

import java.util.List;

import org.example.bet.domain.Event;
import org.example.bet.domain.Prediction;
import org.example.bet.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    boolean existsByUserAndEvent(User user, Event event);
    List<Prediction> findByUserOrderByCreatedAtDesc(User user);
    List<Prediction> findAllByEvent(Event event);
}