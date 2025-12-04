package org.example.prediction.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.prediction.models.entities.Event;
import org.example.prediction.dto.ShowEventInfoDto;
import org.example.prediction.dto.form.AddEventDto;
import org.example.prediction.repositories.PredictionRepository;
import org.example.prediction.services.EventService;
import org.example.prediction.services.EventServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Slf4j
@Controller
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final PredictionRepository predictionRepository; 


    @GetMapping("/all")
    public String showAllEvents(@RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "5") int size,
                                @RequestParam(value = "search", required = false) String search,
                                Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ShowEventInfoDto> eventPage = eventService.searchEvents(search, pageable);

        model.addAttribute("events", eventPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", eventPage.getTotalPages());
        model.addAttribute("search", search);

        return "events/all";
    }

      @GetMapping("/details/{id}")
      public String eventDetails(@PathVariable("id") Long id, Model model, Principal principal) {
          model.addAttribute("event", eventService.findEventById(id));
  
          boolean hasVoted = false;
          
          if (principal != null) {
              String username = principal.getName();
              //Event eventEntity = eventService.findEventWithStats(id);
              //hasVoted = predictionRepository.existsByUserUsernameAndEvent(username, eventEntity);
              hasVoted = predictionRepository.existsByUserUsernameAndEventId(username, id);

              // Получаем информацию о текущем пользователе для проверки прав администратора
              org.example.prediction.models.entities.User currentUser = eventService.getCurrentUserByUsername(username);
              model.addAttribute("currentUser", currentUser);
          }
          
          model.addAttribute("hasVoted", hasVoted);
          
          return "events/details";
      }

    @ModelAttribute("createEventForm")
    public AddEventDto initCreateForm(){
        return new AddEventDto("", "", java.util.Arrays.asList("", ""), null);
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

        eventService.createEvent(form);
        redirectAttributes.addFlashAttribute("successMessage", "Событие успешно создано!");
        return "redirect:/events/all";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("delete/{id}")
    public String deleteEvent(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        log.debug("Удаление события: {}", id);
        eventService.deleteEvent(id);
        redirectAttributes.addFlashAttribute("successMessage", "Событие успешно удалено");
        return "redirect:/events/all";

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/finish")
    public String finishEvent(
            @PathVariable("id") Long eventId,
            @RequestParam("winningOptionId") Long winningOptionId,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {
        try {
            eventService.finishEvent(eventId, winningOptionId);
            redirectAttributes.addFlashAttribute("successMessage", "Событие завершено! Результаты пересчитаны.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка: " + e.getMessage());
        }

        return "redirect:/events/details/" + eventId;
    }
}