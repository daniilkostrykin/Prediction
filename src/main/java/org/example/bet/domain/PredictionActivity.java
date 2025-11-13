package org.example.bet.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "prediction_activities")
public class PredictionActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prediction_id")
    private Prediction prediction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PredictionActivityType type; // PREDICTION_PLACED, PREDICTION_RESOLVED

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();
}