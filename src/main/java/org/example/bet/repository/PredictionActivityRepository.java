package org.example.bet.repository;

import org.example.bet.domain.PredictionActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionActivityRepository extends JpaRepository<PredictionActivity, Long> {
}