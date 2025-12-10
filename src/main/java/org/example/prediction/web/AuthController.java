package org.example.prediction.web;

import jakarta.validation.Valid;
import org.example.prediction.dto.form.LoginDto;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.prediction.dto.form.UserRegistrationDto;
import org.example.prediction.services.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        log.debug("Отображение страницы входа");
        LoginDto loginDto = new LoginDto("", "");

        model.addAttribute("loginForm", loginDto);

        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute("userRegistrationDto", new UserRegistrationDto());

        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid UserRegistrationDto userRegistrationDto, BindingResult bindingResult) {
        log.info("Попытка регистрации: {}", userRegistrationDto);

        if (userRegistrationDto.getPassword() != null
                && userRegistrationDto.getConfirmPassword() != null
                && !userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmPassword())){
            bindingResult.rejectValue("confirmPassword", "error.userRegistrationDto", "Пароли не совпадают");

        }
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        authService.register(userRegistrationDto);
        return "redirect:/login";

    }

}
