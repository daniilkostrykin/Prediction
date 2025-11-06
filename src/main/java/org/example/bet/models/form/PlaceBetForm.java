package org.example.bet.models.form;

import java.math.BigDecimal;

public record PlaceBetForm(
        Long eventId,
        BigDecimal amount,
        String prediction
) {}