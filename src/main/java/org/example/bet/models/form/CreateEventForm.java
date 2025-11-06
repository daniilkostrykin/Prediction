package org.example.bet.models.form;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Модель, представляющая данные из HTML-формы для создания нового события.
 */
public record CreateEventForm(
    String title,
    String description,
    BigDecimal oddsYes,
    BigDecimal oddsNo,
    Instant closesAt
) {}
