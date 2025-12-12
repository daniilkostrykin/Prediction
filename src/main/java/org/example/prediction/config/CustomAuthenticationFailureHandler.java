package org.example.prediction.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.warn("Ошибка аутентификации: {}", exception.getMessage());
        log.warn("Тип исключения: {}", exception.getClass().getSimpleName());
        
        String errorMessage = "Неправильный логин или пароль";
        
        if (exception instanceof BadCredentialsException) {
            log.warn("Неверные учетные данные");
            errorMessage = "Неправильный логин или пароль";
        } else if (exception instanceof DisabledException) {
            log.warn("Аккаунт отключен");
            errorMessage = "Аккаунт отключен";
        } else if (exception instanceof LockedException) {
            log.warn("Аккаунт заблокирован");
            errorMessage = "Аккаунт заблокирован";
        } else if (exception.getMessage() != null &&
                  (exception.getMessage().contains("User not found") ||
                   exception.getMessage().toLowerCase().contains("user not found"))) {
            log.warn("Пользователь не найден");
            errorMessage = "Пользователь не существует";
        }
        
        request.getSession().setAttribute("error", errorMessage);
        setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}