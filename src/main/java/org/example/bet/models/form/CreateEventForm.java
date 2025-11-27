package org.example.bet.models.form;

import java.util.List;

public record CreateEventForm(
        String title,
        String description,
        List<String> options
) {}