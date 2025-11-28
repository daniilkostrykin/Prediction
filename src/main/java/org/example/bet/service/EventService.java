package org.example.bet.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bet.domain.Event;
import org.example.bet.domain.EventOption;
import org.example.bet.domain.EventStatus;
import org.example.bet.domain.Prediction;
import org.example.bet.domain.User;
import org.example.bet.models.EventDetailsDto;
import org.example.bet.models.EventSummaryDto;
import org.example.bet.models.OptionDto;
import org.example.bet.models.exceptions.EventNotFoundException;
import org.example.bet.models.form.CreateEventForm;
import org.example.bet.repository.EventOptionRepository;
import org.example.bet.repository.EventRepository;
import org.example.bet.repository.PredictionRepository;
import org.example.bet.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {
    private final PredictionRepository predictionRepository;
    private final EventOptionRepository eventOptionRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Transactional 
    public void finishEvent(Long eventId, Long winningOptionId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Событие не найдено"));

        EventOption winningOption = eventOptionRepository.findById(winningOptionId)
                .orElseThrow(() -> new IllegalArgumentException("Опция не найдена"));

        if (!winningOption.getEvent().getId().equals(event.getId())) {
            throw new IllegalArgumentException("Опция не принадлежит этому событию");
        }

        event.setStatus(EventStatus.FINISHED);
        winningOption.setIsCorrectOutcome(true);
        eventRepository.save(event);
        eventOptionRepository.save(winningOption);

        List<Prediction> allPredictions = predictionRepository.findAllByEvent(event);

        for (Prediction prediction : allPredictions) {
            if (prediction.getChosenOption().getId().equals(winningOptionId)) {
                prediction.setStatus(org.example.bet.domain.PredictionStatus.WON);
                
                User winner = prediction.getUser();
                winner.setSuccessfulPredictions(winner.getSuccessfulPredictions() + 1);
                userRepository.save(winner);
                
            } else {
                prediction.setStatus(org.example.bet.domain.PredictionStatus.LOST);
            }
            predictionRepository.save(prediction);
        }
    }


    @Transactional
    public void createEvent(CreateEventForm form) {
        Event event = new Event();
        event.setTitle(form.getTitle());
        event.setDescription(form.getDescription());
        event.setStatus(EventStatus.PENDING);
        event.setClosesAt(Instant.now().plusSeconds(86400));

        List<EventOption> options = form.getOptions().stream().map(text -> {
            EventOption option = new EventOption();
            option.setText(text);
            option.setEvent(event);
            option.setIsCorrectOutcome(false);
            return option;
        }).collect(Collectors.toList());

        event.setOptions(options);
        eventRepository.save(event);
    }

    public List<EventSummaryDto> findAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::mapToListItem)
                .collect(Collectors.toList());
    }

    public EventDetailsDto findEventById(Long id) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new EventNotFoundException("Событие с ID " + id + " не найдено"));
        return new EventDetailsDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getStatus().name(),
                event.getClosesAt(),
                event.getOptions().stream()
                        .map(option -> new OptionDto(option.getId(), option.getText(), 0))
                        .collect(Collectors.toList())
        );
    }

    public Page<EventSummaryDto> searchEvents(String query, Pageable pageable) {
        Page<Event> page;
        if (query != null && !query.isEmpty()) {
            page = eventRepository.findByTitleContainingIgnoreCase(query, pageable);
        } else {
            page = eventRepository.findAll(pageable);
        }
        return page.map(this::mapToListItem);
    }

    private EventSummaryDto mapToListItem(Event event) {
        return new EventSummaryDto(
                event.getId(),
                event.getTitle(),
                event.getStatus().name(),
                event.getClosesAt(),
                event.getOptions().stream()
                        .limit(3)
                        .map(option -> new OptionDto(option.getId(), option.getText(), 0))
                        .collect(Collectors.toList())
        );
    }

    public void deleteEvent(Long id){
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException("Событие с ID " + id + " не найдено для удаления");
        }
        eventRepository.deleteById(id);
    }

    public Event findEventWithStats(Long id){
        Event event = eventRepository.findById(id)
        .orElseThrow(() -> new EventNotFoundException("Событие с ID " + id + " не существует"));
        return event;
    }
}
