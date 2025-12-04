package org.example.prediction.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.prediction.models.entities.User;
import org.example.prediction.dto.form.AddPredictionDto;
import org.example.prediction.repositories.UserRepository;
import org.example.prediction.services.PredictionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/predictions")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;
    private final UserRepository userRepository;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/make")
    public String makePrediction(
            @ModelAttribute AddPredictionDto form,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {

        try {
            String username = principal.getName();

            User user = userRepository.findByUsername(username)
                            .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
            predictionService.makePrediction(user.getId(), form);
            redirectAttributes.addFlashAttribute("successMessage", "Предсказание успешно сделано!");

        } catch (Exception e) {
            log.error("Ошибка при прдесказании", e);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/events/details/" + form.getEventId();
    }
}