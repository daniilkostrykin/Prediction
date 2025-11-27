package org.example.bet.models;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;


public record EventDetailsViewModel(
    Long id,
    String title,
    String description,
    String status,
    Instant closesAt,
    List<EventOptionViewModel> optionsWithStats
) {}
