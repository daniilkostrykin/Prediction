package org.example.bet.models;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Модель представления для ОДНОЙ ставки в истории пользователя.
 */
public record BetHistoryItemViewModel(
    String eventTitle,
    BigDecimal amount,
    String prediction,
    String status,
    BigDecimal potentialPayout,
    Instant createdAt
) {}
