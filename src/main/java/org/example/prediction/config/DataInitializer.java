package org.example.prediction.config;

import org.example.prediction.models.entities.User; // Твой класс User
import org.example.prediction.repositories.UserRepository; // Твой репозиторий
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.example.prediction.models.enums.UserRole;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            System.out.println("Запуск инициализации данных");
            // Проверяем, есть ли админ, чтобы не создавать дубликаты
            // (хотя с ddl-auto=create это не обязательно, но полезно на будущее)
            if (userRepository.findByUsername("admin").isEmpty()) {
                System.out.println("Создание администратора...");
                User admin = new User();
                admin.setUsername("admin");
                // САМОЕ ВАЖНОЕ: Здесь пароль "12345" превращается в хеш
                String encodedPassword = passwordEncoder.encode("12345");
                admin.setPassword(encodedPassword);
                admin.setEmail("admin@example.com");
                admin.setRole(UserRole.ADMIN); // Используем правильный enum
                admin.setSuccessfulPredictions(0);

                userRepository.save(admin);
                System.out.println("Администратор создан: admin / 12345");
            } else {
                System.out.println("Администратор уже существует");
            }

            if (userRepository.findByUsername("user1").isEmpty()) {
                System.out.println("Создание пользователя user1...");
                User user = new User();
                user.setUsername("user1");
                user.setPassword(passwordEncoder.encode("12345"));
                user.setEmail("user1@example.com");
                user.setRole(UserRole.USER);
                user.setSuccessfulPredictions(0);

                userRepository.save(user);
                System.out.println("Пользователь создан: user1 / 12345");
            } else {
                System.out.println("Пользователь user1 уже существует");
            }
            System.out.println("Инициализация данных завершена");
        };
    }
}