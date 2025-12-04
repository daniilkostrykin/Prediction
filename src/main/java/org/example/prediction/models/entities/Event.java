package org.example.prediction.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.prediction.models.enums.EventStatus;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
public class Event extends BaseEntity{

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status; // PENDING, ACTIVE, CLOSED, FINISHED

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventOption> options;

    @Column(name = "closes_at", nullable = false)
    private Instant closesAt;

}