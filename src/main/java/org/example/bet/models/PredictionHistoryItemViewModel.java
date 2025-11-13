package org.example.bet.models;

import java.time.Instant;

public record PredictionHistoryItemViewModel(
    String eventTitle,
    String prediction,
    String status,
    Instant createdAt
) {}
