package org.example.bet.services;

import org.example.bet.models.entities.User;
import org.example.bet.models.enums.UserRole;
import org.example.bet.dto.form.UserRegistrationDto;
import org.example.bet.repositories.UserRepository;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;
    
    public void register(UserRegistrationDto form){
        if (userRepository.findByUsername(form.username()).isPresent()) {
            throw new RuntimeException("Username уже занят");
        }
        User user = new User();
        user.setUsername(form.username());
        user.setEmail(form.email());
        user.setPassword(form.password());
        user.setRole(UserRole.USER);

        userRepository.save(user);
    }
}
