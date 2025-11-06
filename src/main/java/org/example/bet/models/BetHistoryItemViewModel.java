package org.example.bet.models;

import java.math.BigDecimal;
import java.time.Instant;

public record BetHistoryItemViewModel(
    String eventTitle,
    BigDecimal amount,
    String prediction,
    String status,
    BigDecimal potentialPayout,
    Instant createdAt
) {}
