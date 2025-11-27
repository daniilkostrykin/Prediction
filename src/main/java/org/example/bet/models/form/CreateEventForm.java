package org.example.bet.models.form;

import java.util.List;

import jakarta.validation.constraints.Size;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CreateEventForm(
        @NotBlank(message = "Название события не может быть пустым")
        @Size(min = 5, max = 100, message = "Название должно быть от 5 до 100 символов")
        String title,

        @Size(max = 500, message = "Описание должно быть до 500 символов")
        String description,

        @NotEmpty(message = "Варианты выбора должны быть")
        @Size(min = 2, message = "Не менее 2-х вариантов выбора")
        List<String> options
) {}