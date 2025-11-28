package org.example.bet.dto.form;

public record UserRegistrationDto(
        String username,
        String email,
        String password
) {}