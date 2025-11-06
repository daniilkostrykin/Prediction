package org.example.bet.models.form;

import java.time.Instant;

public record CreateEventForm(
        String title,
        String description,
        Instant closesAt
) {}