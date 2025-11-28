package org.example.bet.dto;

import java.time.Instant;
import java.util.List;

public record ShowEventInfoDto(
        Long id,
        String title,
        String status,
        Instant closesAt,
        List<OptionDto> topOptions
) {
}
