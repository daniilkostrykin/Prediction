package org.example.bet.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.bet.models.entities.Event;
import org.example.bet.models.entities.User;
import org.example.bet.dto.ShowEventInfoDto;
import org.example.bet.dto.form.AddEventDto;
import org.example.bet.models.enums.UserRole;
import org.example.bet.repositories.PredictionRepository;
import org.example.bet.services.EventServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@Controller
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventServiceImpl eventServiceImpl;
    private final PredictionRepository predictionRepository; 


    @GetMapping("/all")
    public String showAllEvents(@RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "5") int size,
                                @RequestParam(value = "search", required = false) String search,
                                Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ShowEventInfoDto> eventPage = eventServiceImpl.searchEvents(search, pageable);

        model.addAttribute("events", eventPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", eventPage.getTotalPages());
        model.addAttribute("search", search);

        return "events/all";
    }

      @GetMapping("/details/{id}")
    public String eventDetails(@PathVariable("id") Long id, Model model, HttpSession session) {
        model.addAttribute("event", eventServiceImpl.findEventById(id));
        
        User currentUser = (User) session.getAttribute("currentUser");
        boolean hasVoted = false;
        
        if (currentUser != null) {
            Event eventEntity = eventServiceImpl.findEventWithStats(id);
            hasVoted = predictionRepository.existsByUserAndEvent(currentUser, eventEntity);
        }
        
        model.addAttribute("hasVoted", hasVoted);
        
        return "events/details";
    }

    @ModelAttribute("createEventForm")
    public AddEventDto initCreateForm(){
        return new AddEventDto("", "", java.util.Arrays.asList("", ""));
    }

    @GetMapping("/add")
    public String showCreateForm() {
        return "events/add";
    }

    @PostMapping("/add")
    public String createEvent(@Valid @ModelAttribute("createEventForm") AddEventDto form,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        log.debug("Обработка POST-запроса на добавление события");                        
        
        if (bindingResult.hasErrors()) {
            log.warn("Ошибки валидации: {}", bindingResult.getAllErrors());

            redirectAttributes.addFlashAttribute("createEventForm", form);

            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.createEventForm", 
                    bindingResult
            );

            return "redirect:/events/add";
        }

        eventServiceImpl.createEvent(form);
        redirectAttributes.addFlashAttribute("successMessage", "Событие успешно создано!");
        return "redirect:/events/all";
    }

    @GetMapping("delete/{id}")
    public String deleteEvent(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        log.debug("Удаление события: {}", id);
        eventServiceImpl.deleteEvent(id);
        redirectAttributes.addFlashAttribute("successMessage", "Событие успешно удалено");
        return "redirect:/events/all";

    }


    @PostMapping("/{id}/finish")
    public String finishEvent(
            @PathVariable("id") Long eventId,
            @RequestParam("winningOptionId") Long winningOptionId,
            jakarta.servlet.http.HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null || currentUser.getRole() != UserRole.ADMIN) {
            return "redirect:/login"; 
        }

        try {
            eventServiceImpl.finishEvent(eventId, winningOptionId);
            redirectAttributes.addFlashAttribute("successMessage", "Событие завершено! Результаты пересчитаны.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка: " + e.getMessage());
        }

        return "redirect:/events/details/" + eventId;
    }
}