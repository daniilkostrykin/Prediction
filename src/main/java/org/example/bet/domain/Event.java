package org.example.bet.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String status; // PENDING, ACTIVE, CLOSED, FINISHED

    @Column(nullable = false)
    private String outcome; // UNDEFINED, YES, NO

    @Column(name = "odds_yes", nullable = false)
    private BigDecimal oddsYes;

    @Column(name = "odds_no", nullable = false)
    private BigDecimal oddsNo;

    @Column(name = "closes_at", nullable = false)
    private Instant closesAt;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt = Instant.now();
}