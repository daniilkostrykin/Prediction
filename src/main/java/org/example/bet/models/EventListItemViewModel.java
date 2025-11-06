package org.example.bet.models;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Модель представления для ОДНОГО события в общем списке.
 */
public record EventListItemViewModel(
    Long id,
    String title,
    String status,
    Instant closesAt,
    BigDecimal oddsYes,
    BigDecimal oddsNo
) {}
