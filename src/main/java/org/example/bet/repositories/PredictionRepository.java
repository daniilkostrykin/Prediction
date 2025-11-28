package org.example.bet.repositories;

import java.util.List;

import org.example.bet.models.entities.Event;
import org.example.bet.models.entities.Prediction;
import org.example.bet.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    boolean existsByUserAndEvent(User user, Event event);
    List<Prediction> findByUserOrderByCreatedAtDesc(User user);
    List<Prediction> findAllByEvent(Event event);
}