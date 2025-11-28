package org.example.bet.dto;

import java.time.Instant;
import java.util.List;


public record ShowDetailedEventInfoDto(
    Long id,
    String title,
    String description,
    String status,
    Instant closesAt,
    List<OptionDto> optionsWithStats
) {}
