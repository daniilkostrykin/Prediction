package org.example.bet.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bet.domain.User;
import org.example.bet.models.form.PlacePredictionForm;
import org.example.bet.service.PredictionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/predictions")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;

    @PostMapping("/make")
    public String makePrediction(
            @ModelAttribute PlacePredictionForm form,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            predictionService.makePrediction(currentUser.getId(), form);
            redirectAttributes.addFlashAttribute("successMessage", "Предсказание успешно сделано!");

        } catch (Exception e) {
            log.error("Ошибка при прдесказании", e);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/events/details/" + form.eventId();
    }
}