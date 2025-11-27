package org.example.bet.models;

import java.time.Instant;
import java.util.List;

public record EventListItemViewModel(
        Long id,
        String title,
        String status,
        Instant closesAt,
        List<EventOptionViewModel> topOptions
) {
}
