package org.example.bet.models;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Модель представления для страницы с детальной информацией о событии.
 */
public record EventDetailsViewModel(
    Long id,
    String title,
    String description,
    String status,
    String outcome,
    Instant closesAt,
    BigDecimal oddsYes,
    BigDecimal oddsNo
) {}
