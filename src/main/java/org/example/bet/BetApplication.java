
package org.example.bet;

import org.example.bet.domain.Bet;
import org.example.bet.domain.Event;
import org.example.bet.domain.User;
import org.example.bet.domain.BetPrediction;
import org.example.bet.domain.BetStatus;
import org.example.bet.domain.EventStatus;
import org.example.bet.domain.EventOutcome;
import org.example.bet.domain.UserRole;
import org.example.bet.repository.BetRepository;
import org.example.bet.repository.EventRepository;
import org.example.bet.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@SpringBootApplication
public class BetApplication {

    public static void main(String[] args) {
        SpringApplication.run(BetApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(
            UserRepository userRepository,
            EventRepository eventRepository,
            BetRepository betRepository) {
        return args -> {
            System.out.println("Создание тестовых данных");

            User testUser = new User();
            testUser.setUsername("testuser");
            testUser.setEmail("test@example.com");
            testUser.setPassword("hashed_password_123");
            testUser.setRole(UserRole.USER);
            testUser.setBalance(new BigDecimal("1000.00"));

            User savedUser = userRepository.save(testUser);
            System.out.println("Сохранен пользователь: " + savedUser.getUsername() + " с ID: " + savedUser.getId());

            Event testEvent = new Event();
            testEvent.setTitle("Произойдет ли событие X в 2025 году?");
            testEvent.setDescription("Полное описание события X...");
            testEvent.setStatus(EventStatus.ACTIVE);
            testEvent.setOutcome(EventOutcome.UNDEFINED);
            testEvent.setClosesAt(Instant.now().plus(10, ChronoUnit.DAYS));
            testEvent.setOddsYes(new BigDecimal("1.90"));
            testEvent.setOddsNo(new BigDecimal("1.90"));
            testEvent.setTotalAmountYes(BigDecimal.ZERO);
            testEvent.setTotalAmountNo(BigDecimal.ZERO);

            Event savedEvent = eventRepository.save(testEvent);
            System.out.println("Сохранено событие: '" + savedEvent.getTitle() + "' с ID: " + savedEvent.getId());

            Bet testBet = new Bet();
            testBet.setAmount(new BigDecimal("50.00"));
            testBet.setPrediction(BetPrediction.YES);
            testBet.setStatus(BetStatus.PLACED);
            testBet.setOddsAtPlacement(savedEvent.getOddsYes());
            testBet.setPotentialPayout(testBet.getAmount().multiply(testBet.getOddsAtPlacement()));

            testBet.setUser(savedUser);
            testBet.setEvent(savedEvent);

            betRepository.save(testBet);
            System.out.println("Сохранена ставка от пользователя " + savedUser.getUsername() + " на событие '"
                    + savedEvent.getTitle() + "'");

            System.out.println("Текстовые данные сохранены");
        };
    }
}