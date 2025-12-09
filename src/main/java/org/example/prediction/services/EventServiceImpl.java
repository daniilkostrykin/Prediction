package org.example.prediction.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.prediction.models.entities.Event;
import org.example.prediction.models.entities.EventOption;
import org.example.prediction.models.enums.EventStatus;
import org.example.prediction.models.entities.Prediction;
import org.example.prediction.models.entities.User;
import org.example.prediction.dto.ShowDetailedEventInfoDto;
import org.example.prediction.dto.ShowEventInfoDto;
import org.example.prediction.models.exceptions.EventNotFoundException;
import org.example.prediction.dto.form.AddEventDto;
import org.example.prediction.models.enums.PredictionStatus;
import org.example.prediction.repositories.EventOptionRepository;
import org.example.prediction.repositories.EventRepository;
import org.example.prediction.repositories.PredictionRepository;
import org.example.prediction.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final PredictionRepository predictionRepository;
    private final EventOptionRepository eventOptionRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    /*@Transactional
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
    }*/
    @Override
    @Transactional
    @CacheEvict(cacheNames = "events", allEntries = true)
    public void createEvent(AddEventDto form) {

        Event event = mapper.map(form, Event.class);
        event.setStatus(EventStatus.ACTIVE);
        //event.setClosesAt(Instant.now().plusSeconds(86400));

        java.time.ZoneId zoneId = java.time.ZoneId.systemDefault();
        event.setClosesAt(form.getClosesAt().atZone(zoneId).toInstant());

        List<EventOption> options = form.getOptions().stream()
        .filter(text -> text != null && !text.trim().isEmpty()) 
        .map(text -> {
            EventOption option = new EventOption();
            option.setText(text);
            option.setEvent(event);
            option.setIsCorrectOutcome(false);
            return option;
        }).collect(Collectors.toList());

        if (options.size() < 2) {
             throw new IllegalArgumentException("Должно быть минимум 2 заполненных варианта");
        }

        event.setOptions(options);
        eventRepository.save(event);
    }

    /*@Transactional
    public List<ShowEventInfoDto> allEvents() {
        List<Event> events = eventRepository.findAll();
        for (Event event : events) {
            event.getOptions().size();
        }
        return events.stream()
                .map(this::mapToListItem)
                .collect(Collectors.toList());
    }*/
    @Override
    @Cacheable(value = "events", key = "'all'")
    public List<ShowEventInfoDto> allEvents() {
        log.debug("Получение всех событий из БД");
        return eventRepository.findAll()
                .stream()
                .map(event -> mapper.map(event, ShowEventInfoDto.class))
                .collect(Collectors.toList());

    }

    /*@Transactional
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
    }*/
    @Override
    @Cacheable(value = "event", key = "#id", unless = "#result == null")
    public ShowDetailedEventInfoDto findEventById(Long id) {
        Event event = eventRepository.findByIdWithOptions(id)
                .orElseThrow(() -> new EventNotFoundException("Событие с ID " + id + " не найдено"));
        return mapper.map(event, ShowDetailedEventInfoDto.class);
    }

    @Override
    public Page<ShowEventInfoDto> searchEvents(String query, Pageable pageable) {
        Page<Event> page;
        if (query != null && !query.isEmpty()) {
            page = eventRepository.findByTitleContainingIgnoreCase(query, pageable);
        } else {
            page = eventRepository.findAll(pageable);
        }
        /*for (Event event : page.getContent()) {
            event.getOptions().size();
        }*/
        return page.map(this::mapToListItem);
    }

    /*private ShowEventInfoDto mapToListItem(Event event) {
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
    }*/

    private org.example.prediction.dto.ShowEventInfoDto mapToListItem(Event event) {
        return mapper.map(event, org.example.prediction.dto.ShowEventInfoDto.class);
    }

    public Event findEventWithStats(Long id) {
        return eventRepository.findByIdWithOptions(id)
                .orElseThrow(() -> new EventNotFoundException("Событие с ID " + id + " не существует"));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "events", allEntries = true),
            @CacheEvict(value = "event", key = "#id")
    })
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException("Событие с ID " + id + " не найдено для удаления");
        }
        
        // Удаляем все прогнозы, связанные с этим событием, чтобы избежать нарушения целостности БД
        predictionRepository.deleteAllByEventId(id);
        
        eventRepository.deleteById(id);
    }
    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "events", allEntries = true),
            @CacheEvict(value = "event", key = "#eventId")
    })
    public void finishEvent(Long eventId, Long winningOptionId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Событие не найдено"));

        EventOption winningOption = eventOptionRepository.findById(winningOptionId)
                .orElseThrow(() -> new IllegalArgumentException("Опция не найдена"));

        if (winningOption.getEvent() == null || !winningOption.getEvent().getId().equals(event.getId())) {
            throw new IllegalArgumentException("Опция не принадлежит этому событию");
        }

        event.setStatus(EventStatus.FINISHED);
        winningOption.setIsCorrectOutcome(true);
        eventRepository.saveAndFlush(event);
        eventOptionRepository.saveAndFlush(winningOption);

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
            // Обновляем сущность, убедившись, что версия корректно обрабатывается
            predictionRepository.saveAndFlush(prediction);
        }
    }

    @Override
    public User getCurrentUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + username));
    }

    //@Scheduled(fixedRate = 60000)
        @CacheEvict(value = "events", allEntries = true)
        @Transactional
        public void closeExpiredEvents() {
            log.debug("Запуск проверки истекших событий...");
            
            List<Event> expiredEvents = eventRepository.findAllByStatusAndClosesAtBefore(EventStatus.ACTIVE, Instant.now());
            
            if (!expiredEvents.isEmpty()) {
                for (Event event : expiredEvents) {
                    log.info("Событие id={} истекло. Закрываем прием ставок.", event.getId());
                    event.setStatus(EventStatus.CLOSED); 
                }
                eventRepository.saveAll(expiredEvents);
            
            }
        }
}
