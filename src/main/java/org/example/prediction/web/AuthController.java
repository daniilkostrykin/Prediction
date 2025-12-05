package org.example.prediction.web;

import org.example.prediction.dto.form.LoginDto;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.prediction.dto.form.UserRegistrationDto;
import org.example.prediction.services.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import jakarta.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {
    private final AuthService authService;
    @GetMapping("/login")
    public String loginPage(Model model){
        log.debug("Отображение страницы входа");
        LoginDto loginDto = new LoginDto("","");

        model.addAttribute("loginForm", loginDto);

        return "auth/login";
    }

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

    /*@PostMapping("/login")
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
    }*/
}
