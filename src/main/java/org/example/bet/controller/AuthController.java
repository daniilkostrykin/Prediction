package org.example.bet.controller;

import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.bet.models.form.LoginForm;
import org.example.bet.models.form.RegistrationForm;
import org.example.bet.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {
    private final AuthService authService;

    @GetMapping("/register")
    public String registerPage(Model model) {
        RegistrationForm registrationForm = new RegistrationForm("","", "");

        model.addAttribute("registrationForm", registrationForm);

        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(RegistrationForm registrationForm){
        log.info("Попытка регистрации: {}", registrationForm);
        authService.register(registrationForm);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        LoginForm loginForm = new LoginForm("","");

        model.addAttribute("loginForm", loginForm);

        return "auth/login";
    }


}
