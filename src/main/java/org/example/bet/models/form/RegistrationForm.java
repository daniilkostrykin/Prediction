package org.example.bet.models.form;

public record RegistrationForm(
        String username,
        String email,
        String password
) {}