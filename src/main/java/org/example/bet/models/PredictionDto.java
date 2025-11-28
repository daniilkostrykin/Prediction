package org.example.bet.models;

import java.time.Instant;

public record PredictionDto(
    String eventTitle,
    String prediction,
    String status,
    Instant createdAt
) {}
