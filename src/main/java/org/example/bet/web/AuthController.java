package org.example.bet.web;

import org.example.bet.dto.form.LoginDto;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.example.bet.models.entities.User;
import org.example.bet.dto.form.UserRegistrationDto;
import org.example.bet.services.AuthService;
import org.example.bet.repositories.*;
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
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("","", "");

        model.addAttribute("userRegistrationDto", userRegistrationDto);

        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(UserRegistrationDto userRegistrationDto){
        log.info("Попытка регистрации: {}", userRegistrationDto);
        
        authService.register(userRegistrationDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        LoginDto loginDto = new LoginDto("","");

        model.addAttribute("loginForm", loginDto);

        return "auth/login";
    }
    @PostMapping("/login")
    public String processLogin(@ModelAttribute LoginDto form, HttpSession session, Model model) {
        Optional<User> userOpt = userRepository.findByUsername(form.username());

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(form.password())) {
            
            session.setAttribute("currentUser", userOpt.get());
            
            return "redirect:/";
        } else {
            model.addAttribute("error", "Неверный логин или пароль");
            model.addAttribute("loginForm", form);
            return "auth/login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
