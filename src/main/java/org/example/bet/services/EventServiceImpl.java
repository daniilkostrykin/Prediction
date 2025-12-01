package org.example.bet.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bet.models.entities.Event;
import org.example.bet.models.entities.EventOption;
import org.example.bet.models.enums.EventStatus;
import org.example.bet.models.entities.Prediction;
import org.example.bet.models.entities.User;
import org.example.bet.dto.ShowDetailedEventInfoDto;
import org.example.bet.dto.ShowEventInfoDto;
import org.example.bet.dto.OptionDto;
import org.example.bet.models.exceptions.EventNotFoundException;
import org.example.bet.dto.form.AddEventDto;
import org.example.bet.models.enums.PredictionStatus;
import org.example.bet.repositories.EventOptionRepository;
import org.example.bet.repositories.EventRepository;
import org.example.bet.repositories.PredictionRepository;
import org.example.bet.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
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
                prediction.setStatus(PredictionStatus.WON);
                
                User winner = prediction.getUser();
                winner.setSuccessfulPredictions(winner.getSuccessfulPredictions() + 1);
                userRepository.save(winner);
                
            } else {
                prediction.setStatus(PredictionStatus.LOST);
            }
            predictionRepository.save(prediction);
        }
    }


    @Transactional
    public void createEvent(AddEventDto form) {
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

    @Transactional
    public List<ShowEventInfoDto> findAllEvents() {
        List<Event> events = eventRepository.findAll();
        for (Event event : events) {
            event.getOptions().size();
        }
        return events.stream()
                .map(this::mapToListItem)
                .collect(Collectors.toList());
    }

    @Transactional
    public ShowDetailedEventInfoDto findEventById(Long id) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new EventNotFoundException("Событие с ID " + id + " не найдено"));
        event.getOptions().size(); 
        return new ShowDetailedEventInfoDto(
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

    public Page<ShowEventInfoDto> searchEvents(String query, Pageable pageable) {
        Page<Event> page;
        if (query != null && !query.isEmpty()) {
            page = eventRepository.findByTitleContainingIgnoreCase(query, pageable);
        } else {
            page = eventRepository.findAll(pageable);
        }
        for (Event event : page.getContent()) {
            event.getOptions().size();
        }
        return page.map(this::mapToListItem);
    }

    private ShowEventInfoDto mapToListItem(Event event) {
        event.getOptions().size(); 
        return new ShowEventInfoDto(
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

    @Transactional
    public Event findEventWithStats(Long id){
        Event event = eventRepository.findById(id)
        .orElseThrow(() -> new EventNotFoundException("Событие с ID " + id + " не существует"));
        event.getOptions().size(); 
        return event;
    }
}
