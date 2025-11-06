package org.example.bet.models;

import java.math.BigDecimal;
import java.time.Instant;


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
