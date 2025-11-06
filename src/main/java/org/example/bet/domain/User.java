package org.example.bet.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String password; // В Spring Security здесь будет хэш

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String role; // "USER", "ADMIN"

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    // Геттеры, сеттеры, конструкторы
}