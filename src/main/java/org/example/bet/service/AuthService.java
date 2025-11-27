package org.example.bet.service;

import org.example.bet.domain.User;
import org.example.bet.domain.UserRole;
import org.example.bet.models.form.RegistrationForm;
import org.example.bet.repository.UserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    
    public void register(RegistrationForm form){
        User user = new User();
        user.setUsername(form.username());
        user.setEmail(form.email());
        user.setPassword(form.password());
        user.setRole(UserRole.USER);

        userRepository.save(user);
    }
}
