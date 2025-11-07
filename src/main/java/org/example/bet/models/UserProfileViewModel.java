package org.example.bet.models;

import java.math.BigDecimal;

public record UserProfileViewModel(
        String username,
        BigDecimal balance,
        long totalBetsCount
) {}