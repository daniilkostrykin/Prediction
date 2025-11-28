package org.example.bet.dto.form;


public record AddPredictionDto(
        Long eventId,
        Long chosenOptionId
) {}