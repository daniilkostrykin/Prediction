package org.example.bet.dto;

public record UserStatsDto(
        String username,
        int successfulPredictions,
        long totalPredictionsCount,
        double successRate
) {}