package org.example.bet.services;

import org.example.bet.dto.form.UserRegistrationDto;

public interface AuthService {
    void register(UserRegistrationDto form);
}