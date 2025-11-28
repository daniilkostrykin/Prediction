package org.example.bet.models;

public record UserStatsDto(
        String username,
        int successfulPredictions,
        long totalPredictionsCount,
        double successRate
) {}