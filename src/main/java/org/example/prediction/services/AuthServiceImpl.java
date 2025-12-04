package org.example.prediction.services;

import org.example.prediction.models.entities.User;
import org.example.prediction.dto.form.UserRegistrationDto;
import org.example.prediction.models.enums.UserRole;
import org.example.prediction.repositories.UserRepository;
import org.modelmapper.ModelMapper;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(UserRegistrationDto form){
        log.debug("Регистрация пользователя: {}", form.username());

        if (userRepository.findByUsername(form.username()).isPresent()) {
            log.warn("Попытка регистрации пользователя с уже существующим именем: {}", form.username());
            throw new RuntimeException("Username уже занят");
        }
        User user = new User();
        user.setUsername(form.username());
        user.setEmail(form.email());
        user.setPassword(passwordEncoder.encode(form.password()));
        user.setRole(UserRole.USER);

        userRepository.save(user);
        log.debug("Пользователь успешно зарегистрирован: {}", user.getUsername());
    }
}
