package org.example.bet.controller;

import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.example.bet.domain.User;
import org.example.bet.models.form.LoginForm;
import org.example.bet.models.form.RegistrationForm;
import org.example.bet.service.AuthService;
import org.example.bet.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;

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
    @PostMapping("/login")
    public String processLogin(@ModelAttribute LoginForm form, HttpSession session, Model model) {
        Optional<User> userOpt = userRepository.findByUsername(form.username());

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(form.password())) {
            
            session.setAttribute("currentUser", userOpt.get());
            
            return "redirect:/";
        } else {
            model.addAttribute("error", "Неверный логин или пароль");
            return "auth/login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
