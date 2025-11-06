package org.example.bet.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "bets")
@Getter
@Setter
@NoArgsConstructor
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BetPrediction prediction; // YES, NO

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BetStatus status; // PLACED, WON, LOST

    @Column(name = "odds_at_placement", nullable = false)
    private BigDecimal oddsAtPlacement;

    @Column(name = "potential_payout", nullable = false)
    private BigDecimal potentialPayout;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();
}
