package org.example.bet.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bet.domain.Event;
import org.example.bet.domain.EventOption;
import org.example.bet.domain.EventStatus;
import org.example.bet.models.EventDetailsViewModel;
import org.example.bet.models.EventListItemViewModel;
import org.example.bet.models.EventOptionViewModel;
import org.example.bet.models.form.CreateEventForm;
import org.example.bet.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    @Transactional
    public void createEvent(CreateEventForm form) {
        Event event = new Event();
        event.setTitle(form.title());
        event.setDescription(form.description());
        event.setStatus(EventStatus.PENDING); // исправлено на PENDING
        event.setClosesAt(Instant.now().plusSeconds(86400)); // +1 день для примера

        // Создаем опции из списка строк
        List<EventOption> options = form.options().stream().map(text -> {
            EventOption option = new EventOption();
            option.setText(text);
            option.setEvent(event);
            option.setIsCorrectOutcome(false);
            return option;
        }).collect(Collectors.toList());

        event.setOptions(options);
        eventRepository.save(event);
    }

    public List<EventListItemViewModel> findAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::mapToListItem)
                .collect(Collectors.toList());
    }

    public EventDetailsViewModel findEventById(Long id) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) {
            return null;
        }
        return new EventDetailsViewModel(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getStatus().name(),
                event.getClosesAt(),
                event.getOptions().stream()
                        .map(option -> new EventOptionViewModel(option.getId(), option.getText(), 0))
                        .collect(Collectors.toList())
        );
    }

    public Page<EventListItemViewModel> searchEvents(String query, Pageable pageable) {
        Page<Event> page;
        if (query != null && !query.isEmpty()) {
            page = eventRepository.findByTitleContainingIgnoreCase(query, pageable);
        } else {
            page = eventRepository.findAll(pageable);
        }
        return page.map(this::mapToListItem);
    }

    private EventListItemViewModel mapToListItem(Event event) {
        return new EventListItemViewModel(
                event.getId(),
                event.getTitle(),
                event.getStatus().name(),
                event.getClosesAt(),
                event.getOptions().stream()
                        .limit(3) // только первые 3 опции для отображения
                        .map(option -> new EventOptionViewModel(option.getId(), option.getText(), 0))
                        .collect(Collectors.toList())
        );
    }
}
