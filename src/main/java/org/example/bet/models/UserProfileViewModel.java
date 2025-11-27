package org.example.bet.models;

import java.math.BigDecimal;

public record UserProfileViewModel(
        String username,
        int successfulPredictions,
        long totalPredictionsCount,
        double successRate
) {}