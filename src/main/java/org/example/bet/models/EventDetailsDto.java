package org.example.bet.models;

import java.time.Instant;
import java.util.List;


public record EventDetailsDto(
    Long id,
    String title,
    String description,
    String status,
    Instant closesAt,
    List<OptionDto> optionsWithStats
) {}
