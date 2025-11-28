package org.example.bet.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.bet.domain.Prediction;
import org.example.bet.domain.PredictionStatus;
import org.example.bet.domain.User;
import org.example.bet.repository.PredictionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserController {

    private final PredictionRepository predictionRepository;

    @GetMapping
    public String userProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }

        List<Prediction> history = predictionRepository.findByUserOrderByCreatedAtDesc(user);

        int totalBets = history.size();
        long wonBets = history.stream()
                .filter(p -> p.getStatus() == PredictionStatus.WON)
                .count();

        model.addAttribute("user", user);
        model.addAttribute("history", history);
        model.addAttribute("totalBets", totalBets);
        model.addAttribute("wonBets", wonBets);

        return "users/profile";
    }
}