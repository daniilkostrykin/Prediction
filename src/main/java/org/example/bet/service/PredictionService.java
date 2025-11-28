package org.example.bet.service;

import lombok.RequiredArgsConstructor;
import org.example.bet.domain.*;
import org.example.bet.models.form.PlacePredictionForm;
import org.example.bet.repository.EventOptionRepository;
import org.example.bet.repository.EventRepository;
import org.example.bet.repository.PredictionRepository;
import org.example.bet.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PredictionService {

    private final PredictionRepository predictionRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventOptionRepository eventOptionRepository;

    @Transactional
    public void makePrediction(Long userId, PlacePredictionForm form) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        Event event = eventRepository.findById(form.eventId())
                .orElseThrow(() -> new IllegalArgumentException("Событие не найдено"));
        
                if (predictionRepository.existsByUserAndEvent(user, event)) {
            throw new IllegalStateException("Вы уже сделали ставку на это событие! Повторная ставка запрещена.");
        }
        if (event.getStatus() == EventStatus.CLOSED || event.getStatus() == EventStatus.FINISHED) {
            throw new IllegalStateException("Событие уже закрыто");
        }

        if (event.getClosesAt().isBefore(Instant.now())) {
            throw new IllegalStateException("Время для ставок истекло");
        }

        if (predictionRepository.existsByUserAndEvent(user, event)) {
            throw new IllegalStateException("Вы уже сделали предсказание на это событие");
        }

        EventOption option = eventOptionRepository.findById(form.chosenOptionId())
                .orElseThrow(() -> new IllegalArgumentException("Опция не найдена"));

        if (!option.getEvent().getId().equals(event.getId())) {
            throw new IllegalArgumentException("Неверная опция для этого события");
        }

        Prediction prediction = new Prediction();
        prediction.setUser(user);
        prediction.setEvent(event);
        prediction.setChosenOption(option);
        prediction.setStatus(PredictionStatus.PLACED);

        predictionRepository.save(prediction);
    }
}