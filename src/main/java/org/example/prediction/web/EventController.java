package org.example.prediction.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.prediction.dto.ShowEventInfoDto;
import org.example.prediction.dto.form.AddEventDto;
import org.example.prediction.services.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
 


    @GetMapping("/all")
    public String showAllEvents(@RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "4") int size,
                                @RequestParam(value = "search", required = false) String search,
                                @RequestParam(value = "filter", defaultValue = "active") String filter,
                                Model model) {
        Sort sort = Sort.by("status").ascending().and(Sort.by("closesAt").ascending());
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ShowEventInfoDto> eventPage = eventService.searchEvents(search, pageable);

        if (eventPage == null) {
            model.addAttribute("events", java.util.Collections.emptyList());
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", 0);
            model.addAttribute("search", search);
        } else {
            model.addAttribute("events", eventPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", eventPage.getTotalPages());
            model.addAttribute("search", search);
        }
    
        return "events/all";
    }

      @GetMapping("/details/{id}")
      public String eventDetails(@PathVariable("id") Long id, Model model, Principal principal) {
          model.addAttribute("event", eventService.findEventById(id));
  
          boolean hasVoted = false;
          
          if (principal != null) {
              String username = principal.getName();
              hasVoted = eventService.hasUserVoted(username, id);

              org.example.prediction.models.entities.User currentUser = eventService.getCurrentUserByUsername(username);
              model.addAttribute("currentUser", currentUser);
          }
          
          model.addAttribute("hasVoted", hasVoted);
          
          return "events/details";
      }

    @ModelAttribute("createEventForm")
    public AddEventDto initCreateForm(){
        return new AddEventDto("", "", new java.util.ArrayList<>(java.util.Arrays.asList("", "")), null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public String showCreateForm() {
        return "events/add";
    }

    @PreAuthorize("hasRole('ADMIN')")
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
    @DeleteMapping("delete/{id}")
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
        } catch (org.example.prediction.models.exceptions.EventNotFoundException | IllegalArgumentException e) {
            log.warn("Ошибка при завершении события {}: {}", eventId, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка завершения события: " + e.getMessage());
        } catch (Exception e) {
            log.error("Непредвиденная ошибка при завершении события {}", eventId, e);
            redirectAttributes.addFlashAttribute("errorMessage", "Произошла непредвиденная системная ошибка.");
        }

        return "redirect:/events/details/" + eventId;
    }

    @PostMapping(value = "/add", params = "addOption")
    public String addOption(@ModelAttribute("createEventForm") AddEventDto form) {
        if (form.getOptions() == null) {
            form.setOptions(new java.util.ArrayList<>());
        }
        form.getOptions().add("");
        
        return "events/add";
    }

     @PostMapping(value = "/add", params = "removeOption")
    public String removeOption(@ModelAttribute("createEventForm") AddEventDto form,
                               @RequestParam("removeOption") int index) {
        if (form.getOptions() != null && form.getOptions().size() > index) {
            form.getOptions().remove(index);
        }
        return "events/add";
    }
}