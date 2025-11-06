package org.example.bet.models;

import java.math.BigDecimal;
import java.time.Instant;

public record EventListItemViewModel(
    Long id,
    String title,
    String status,
    Instant closesAt,
    BigDecimal oddsYes,
    BigDecimal oddsNo
) {}
