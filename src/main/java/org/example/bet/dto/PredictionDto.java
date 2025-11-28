package org.example.bet.dto;

import java.time.Instant;

public record PredictionDto(
    String eventTitle,
    String prediction,
    String status,
    Instant createdAt
) {}
