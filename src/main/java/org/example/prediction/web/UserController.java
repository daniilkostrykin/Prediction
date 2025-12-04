package org.example.prediction.web;

import lombok.RequiredArgsConstructor;
import org.example.prediction.models.entities.Prediction;
import org.example.prediction.models.enums.PredictionStatus;
import org.example.prediction.models.entities.User;
import org.example.prediction.repositories.PredictionRepository;
import org.example.prediction.repositories.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserController {

    private final PredictionRepository predictionRepository;
    private final UserRepository userRepository;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String userProfile(Principal principal, Model model) {

        String username = principal.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        List<Prediction> history = predictionRepository.findByUserOrderByCreatedAtDesc(user);

        int totalPredictions = history.size();
        long wonPredictions = history.stream()
                .filter(p -> p.getStatus() == PredictionStatus.WON)
                .count();

        model.addAttribute("user", user);
        model.addAttribute("history", history);
        model.addAttribute("totalPredictions", totalPredictions);
        model.addAttribute("wonPredictions", wonPredictions);

        return "users/profile";
    }
}