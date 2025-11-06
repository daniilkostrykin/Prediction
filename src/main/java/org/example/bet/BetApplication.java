// package: org.example.bet
// файл: BetApplication.java

package org.example.bet;

// Импорты для CommandLineRunner и твоих классов
import org.example.bet.domain.Bet;
import org.example.bet.domain.Event;
import org.example.bet.domain.User;
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

    // --- ВОТ ЭТОТ КОД НУЖНО ДОБАВИТЬ ---
    @Bean
    public CommandLineRunner dataLoader(
            UserRepository userRepository,
            EventRepository eventRepository,
            BetRepository betRepository
    ) {
        return args -> {
            System.out.println("--- ЗАПУСК СОЗДАНИЯ ТЕСТОВЫХ ДАННЫХ ---");

            // 1. Создаем и сохраняем пользователя
            User testUser = new User();
            testUser.setUsername("testuser");
            testUser.setEmail("test@example.com");
            testUser.setPassword("hashed_password_123"); // В реальности здесь будет хэш
            testUser.setRole("USER");
            testUser.setBalance(new BigDecimal("1000.00"));

            // Супер-важный момент: метод save() возвращает сохраненную сущность с присвоенным ID!
            User savedUser = userRepository.save(testUser);
            System.out.println("Сохранен пользователь: " + savedUser.getUsername() + " с ID: " + savedUser.getId());

            // 2. Создаем и сохраняем событие
            Event testEvent = new Event();
            testEvent.setTitle("Произойдет ли событие X в 2025 году?");
            testEvent.setDescription("Полное описание события X...");
            testEvent.setStatus("ACTIVE");
            testEvent.setOutcome("UNDEFINED");
            testEvent.setClosesAt(Instant.now().plus(10, ChronoUnit.DAYS)); // Ставки закрываются через 10 дней
            testEvent.setOddsYes(new BigDecimal("1.90"));
            testEvent.setOddsNo(new BigDecimal("1.90"));

            Event savedEvent = eventRepository.save(testEvent);
            System.out.println("Сохранено событие: '" + savedEvent.getTitle() + "' с ID: " + savedEvent.getId());

            // 3. Создаем и сохраняем ставку, связывая пользователя и событие
            Bet testBet = new Bet();
            testBet.setAmount(new BigDecimal("50.00"));
            testBet.setPrediction("YES");
            testBet.setStatus("PLACED");
            testBet.setOddsAtPlacement(savedEvent.getOddsYes());
            testBet.setPotentialPayout(testBet.getAmount().multiply(testBet.getOddsAtPlacement()));

            // Устанавливаем связи!
            testBet.setUser(savedUser);
            testBet.setEvent(savedEvent);

            betRepository.save(testBet);
            System.out.println("Сохранена ставка от пользователя " + savedUser.getUsername() + " на событие '" + savedEvent.getTitle() + "'");

            System.out.println("--- ТЕСТОВЫЕ ДАННЫЕ УСПЕШНО СОЗДАНЫ ---");
        };
    }
}