package org.example.bet.models;

public record UserProfileViewModel(
        String username,
        int successfulPredictions,
        long totalPredictionsCount,
        double successRate
) {}