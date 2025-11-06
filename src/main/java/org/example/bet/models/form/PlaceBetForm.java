package org.example.bet.models.form;

import java.math.BigDecimal;

/**
 * Модель, представляющая данные из HTML-формы для совершения ставки.
 */
public record PlaceBetForm(
    Long eventId,
    BigDecimal amount,
    String prediction
) {}
