package org.example.prediction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.debug("Создание PasswordEncoder");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthenticationFailureHandler customAuthenticationFailureHandler) throws Exception {
        log.debug("Настройка SecurityFilterChain");
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/register", "/login", "/events/all", "/css/**", "/js/**", "/").permitAll()
                .requestMatchers("/events/add", "/events/delete/**", "/events/*/finish").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login") // Показываем эту страницу при GET /login
                .loginProcessingUrl("/login") // <--- ВАЖНО: Перехватываем POST /login здесь
                .defaultSuccessUrl("/", true) // Куда кидать после успеха
                .failureHandler(customAuthenticationFailureHandler) // Используем кастомный обработчик ошибок
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll());

        return http.build();
    }
}