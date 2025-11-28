package org.example.bet.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.bet.models.enums.PredictionStatus;

@Entity
@Table(name = "predictions")
@Getter
@Setter
@NoArgsConstructor
public class Prediction extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chosen_option_id", nullable = false)
    private EventOption chosenOption;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PredictionStatus status; // PLACED, WON, LOST

}
